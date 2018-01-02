#!/bin/bash 
#*******************************************************************************
# Copyright (c) 2008, 2018 Martin Karpisek.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#     Martin Karpisek - initial API and implementation
#*******************************************************************************
#
# Script for running broker server capable to create new session servers.
#	@param CONFIG_FILE of broker server
#
# History:
#	13.08.2011 - removed dependency on HTTPServer (martin.karpisek@gmail.com)
#	07.11.2009 - initial version (martin.karpisek@gmail.com)
#

export CONFIG_FILE=$7

topaz -i -l <<EOF
set gemstone $1
set gemnetid $2
set username $3
set password $4
set hostusername $5
set hostpassword $6
login
ru
|configFile profiles portNumber firstSessionServerPortNumber lastSessionServerPortNumber log responseBlock server socket findFreePort getGsParameters checkGsParameters stopServer semaphore  startTime lastRequestTime lf serverStop|
lf := Character lf asString.
serverStop := false.
startTime := lastRequestTime := DateTime now.
log := [:string|
	GsFile stdout nextPutAll: DateTime now asString, ' - ', string asString; lf; flush.	
].

configFile := System clientEnvironmentVariable: 'CONFIG_FILE'.
configFile isEmpty ifTrue:[
	log value: 'usage: runBrokerServer.sh <configFile>'.
	^nil.
]
ifFalse:[
	|file|
	(file := GsFile openRead: configFile) == nil ifTrue:[
		^log value: 'Can not open file ', configFile asString.
	].
	
	[
		|config profilesArray|
		log value: 'Reading configuration from ', configFile asString.		
		config := IdentityDictionary withAll: (file contents evaluate).
		portNumber := config at: #portNumber ifAbsent: [^log value: 'Missing portNumber value in config file'].
		firstSessionServerPortNumber := config at: #firstSessionServerPortNumber ifAbsent: [^log value: 'Missing firstSessionServerPortNumber value in config file'].
		lastSessionServerPortNumber := config at: #lastSessionServerPortNumber ifAbsent: [^log value: 'Missing lastSessionServerPortNumber value in config file'].
		
		profilesArray := config at: #profiles ifAbsent: [^log value: 'Missing profiles value in config file'].
		profiles := IdentityDictionary withAll: profilesArray.
		
		profiles size <= 0 ifTrue:[
			^log value: 'No connection profiles found in config file'
		]
		ifFalse:[
			log value: 'Profiles loaded: ', profiles keys printString.
		].
	] ensure: [file close]
].

findFreePort := [
	|freePortNumber n|
	n := firstSessionServerPortNumber asInteger.
	[freePortNumber == nil and: [n<=lastSessionServerPortNumber asInteger]] whileTrue:[
		(System performOnServer: 'netstat -an | grep ',n asString,' | grep -i listen') isEmpty ifTrue:[
			freePortNumber := n.
		].
		n := n +1.
	].
	freePortNumber	
].

getGsParameters := [:array|		
	|result|
	array size ~= 3 ifTrue:[
		result := 'Wrong number of parameters (expected 3) got ', array printString.
	]
	ifFalse:[
		|profileName userName password gsParameters|		
		profileName := (array at: 1) asSymbol.
		userName := array at: 2.
		password := array at: 3.
		
		gsParameters := profiles at: profileName ifAbsent:[nil].
		gsParameters == nil ifTrue:[
			result := 'Unknown connection profile "', profileName asString, '".'.
		]
		ifFalse:[
			|expectedUserName expectedPassword|
			expectedUserName := (gsParameters at: 3) asString.
			expectedPassword := (gsParameters at: 4) md5sum asHexString.
		
			(userName asString ~= expectedUserName or: [password asString ~= expectedPassword]) ifTrue:[
				log value: 'Authorization error: userName="', userName asString,'" password="',password asString, '"'.
				log value: 'Expected: userName="', expectedUserName asString,'" password="',expectedPassword asString, '"'.
				result := 'Wrong user or password.'.
			]
			ifFalse:[
				result := gsParameters
			] 
		].	
	].	
	result
].

checkGsParameters := [:gsParameters|
"
	param gsParameters is array with gs connection profile as passed to #newSession operation (see runSessionServer.sh input parameters)
	return string with error message or nil on success
"
	|gci result params|
	params := GemStoneParameters new.
	params gemStoneName: (gsParameters at: 1).
	params gemService: (gsParameters at: 2).
	params username: (gsParameters at: 3).
	params password: (gsParameters at: 4) copy.
	params hostUsername: (gsParameters at: 5).
	params hostPassword: (gsParameters at: 6) copy.
	
	params gemService isEmpty ifTrue:[params gemService: 'gemnetobject'].
		
	gci := GciInterface new.			
	(gci login: params andExecute: '1+2' ifFailure: [nil]) == nil ifTrue:[
		result := 'Failed to connect to GemStone server.'.
		(gci lastError ~~ nil and: [gci lastError message ~~ nil]) ifTrue:[
			result := gci lastError message asString.
		].
	].
	result
].

stopServer := [:sessionServerPortNumber|
	System performOnServer: './stopServer.sh ', sessionServerPortNumber asString.
].

responseBlock := [:request|
	|requestArray operation operationArgs response|	
	[
	requestArray := request evaluate.
	] on: ExceptionA do: [:e| response := 'ServerError',lf, 'Can not evaluate request body.'].

	"request must be always array with #(versionString clientIdString operationSymbol operationArgumentsArray)"	
	(requestArray class == Array and: [requestArray size = 4]) ifFalse:[
		response := 'ServerError', lf, 'Request body is not Array of size 4, it is ', requestArray printString, '.'
	].
	
	response isNil ifTrue:[
		operation := requestArray at: 3.
		operationArgs := requestArray at: 4.
	].

	#info ~= operation ifTrue:[
		lastRequestTime := DateTime now.
	].
	
	#echo == operation ifTrue: [		
		response := operationArgs first asString
	].				
	
	#newSession == operation ifTrue: [		
		|clientId parameters result gsParameters sessionServerPortNumber responseMessage|
		clientId := operationArgs at: 1.
		parameters := operationArgs at: 2.
		
		result := getGsParameters value: parameters.
		(result isKindOf: String) ifTrue:[
			responseMessage := 'NewSessionError',lf,result asString.
		]
		ifFalse:[
			gsParameters := result.
		].		
		
		responseMessage == nil ifTrue:[
			sessionServerPortNumber := findFreePort value.
			sessionServerPortNumber == nil ifTrue:[
				responseMessage := 'NewSessionError',lf,'No free ports.'.
			]
		].
		
		responseMessage == nil ifTrue:[
			|shellScript|
			shellScript := WriteStream on: String new.
			shellScript nextPutAll: './runSessionServer.sh'; space.
			shellScript nextPutAll: sessionServerPortNumber asString; space.
			shellScript nextPutAll: '''',clientId asString,''''; space.
			gsParameters do: [:e| shellScript nextPutAll: '''',e asString,''''; space].
			shellScript nextPutAll: ' >> sessionServer_',sessionServerPortNumber asString,'.log'.
			shellScript nextPutAll: ' 2>&1 '.
			shellScript nextPutAll: ' &'.
												
			log value: DateTime now asString, ' - New session for clienId "',clientId asString, '" on port ', sessionServerPortNumber asString.
			
			System performOnServer: shellScript contents.
			(Delay forSeconds: 5) wait.	
			
			responseMessage := sessionServerPortNumber asString.	 		
		].
		response := responseMessage.			
	].
	
	#checkConnection == operation ifTrue:[
		"operationArgs is array with same parameters as is used by newSession"
		
		|result|
		result := getGsParameters value: operationArgs.
		(result isKindOf: String) ifFalse:[
			result := nil
		].	
		response := result asString
	].
	
	#info == operation ifTrue:[
		|stream|
		stream := WriteStream on: String new.
		stream 
			nextPutAll: portNumber asString; lf;
			nextPutAll: startTime asString; lf;
			nextPutAll: lastRequestTime asString; lf;
			nextPutAll: firstSessionServerPortNumber asString; lf;
			nextPutAll: lastSessionServerPortNumber asString; lf. 		
			
		profiles keys do:[:e| stream nextPutAll: e asString; lf].
		response := stream contents	
	].	
	#die == operation ifTrue:[
		log value: 'Received die command - stopping broker server on port', portNumber asString.
		
		firstSessionServerPortNumber asInteger to: lastSessionServerPortNumber asInteger do:[:n|
			stopServer value: n.
		].
		
		serverStop := true.
		response := 'ok'
	].	
	response isNil ifTrue:[
		response := 'ServerError',lf, 'Unknown operation ''', operation asString, '''.'.
	].	
	response
].

"broker server should never do anything where it needs transactions, so we use transactionless mode"
System transactionMode: #transactionless.

"start server"
log value: 'Starting new broker server on port ', portNumber asString.
[
	| readLine readContent errStr chunk header|
	[
		|headers contentLength requestBody response responseBody|
		readLine := [:socket|
			|line result buffer|
			line := WriteStream on: String new.
			result := nil.
			buffer := String new.
			[(result := socket read: 1 into: buffer startingAt: 1) ~~ nil and: [(buffer first) ~= Character lf]] whileTrue:[
				line nextPutAll: buffer.
			].	
			result == nil ifTrue:[
				Object error: 'Error reading line'
			].
			line contents trimWhiteSpace	
		].
		readContent := [:socket :size|
			|remains stream|
			
			remains := size.
			stream := WriteStream on: String new.
			
			[remains >0 and: [(chunk := socket readString: (10 min: remains)) ~~ nil]] whileTrue:[
				stream nextPutAll: chunk.
				remains := remains - chunk size.
			].
			stream contents trimWhiteSpace	
		].
			
		server := GsSocket new.
		(server makeServerAtPort: portNumber asInteger) == nil ifTrue: [
 	 		errStr := server lastErrorString.
  			server close.
  			Object error: errStr.
		].
		server port == portNumber asInteger ifFalse:[ Object error: 'bad port number' ] .
		[serverStop not] whileTrue: [	
			|crlf|
			crlf := Character cr asString, Character lf asString.
			socket := server accept.
			lastRequestTime := DateTime now.
			socket == nil ifTrue: [
				errStr := server lastErrorString.
				server close.
				Object error: errStr.
			].
			socket linger: true length: 10.  "wait after closing until data is processed"

			"read initial line"
			headers := OrderedCollection new.
			[(header := readLine value: socket) ~= ''] whileTrue:[
				headers add: header subStrings.
			].
			headers size = 0 ifTrue:[
				Object error: 'No headers in request.'
			].
			(headers first isEmpty or: [(headers first at: 1) ~= 'POST']) ifTrue:[
				Object error: 'Unsupported http method ', headers first	printString.
			].
			contentLength := (headers detect: [:e| e size > 0 and: [e first = 'Content-Length:']] ifNone: [Object error: 'No content length header found']) at: 2.			
			requestBody := readContent value: socket value: contentLength asInteger.
			responseBody := responseBlock value: requestBody.
			
			response := WriteStream on: String new.
			response nextPutAll: 'HTTP/1.0 200 OK';nextPutAll: crlf.
			response nextPutAll: 'Content-Type: text/html';nextPutAll: crlf.
			response nextPutAll: 'Content-Length: ', responseBody size asString; nextPutAll: crlf.
			response nextPutAll: crlf.
			response nextPutAll: responseBody.
			
			socket write: response contents.
			socket close.
		].
	] on: ExceptionA do:[:ex|
		|exReport|
		exReport := WriteStream on: String new.
		exReport nextPutAll: 'Fatal error';lf.
		exReport nextPutAll: ex description;lf.
		exReport nextPutAll: (GsProcess stackReportToLevel: 500);lf.
		log value: exReport contents.
	].
] ensure: [
	socket ~~ nil ifTrue: [socket close].
	server ~~ nil ifTrue: [server close].
	log value: 'end'.
].

%
exit
EOF