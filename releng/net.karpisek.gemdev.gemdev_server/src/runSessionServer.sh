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
# Script for starting new http based session server for communication with GS
# This is invoked by runBrokerServer.sh script and should not be invoked in other way.
#
#	@param portNumber of port on which will be server listening
# 	@param gemstone
# 	@param gemnetid
# 	@param username
# 	@param password
# 	@param hostusername
# 	@param hostpassword
#
# History:
#	13.08.2011 - removed dependency on HTTPServer (martin.karpisek@gmail.com)
#	07.11.2009 - initial version (martin.karpisek@gmail.com)
#
export PORT_NUMBER=$1
export CLIENT_ID=$2

topaz -l <<EOF
set gemstone $3
set gemnetid $4
set username $5
set password $6
set hostusername $7
set hostpassword $8
login
run
|portNumber clientId log debug responseBlock serverStop startTime lastRequestTime server socket|
portNumber := System clientEnvironmentVariable: 'PORT_NUMBER'.
clientId := System clientEnvironmentVariable: 'CLIENT_ID'.
serverStop := false.
startTime := lastRequestTime := DateTime now.

log := [:string|
	GsFile stdout nextPutAll: DateTime now asString, ' - ', string asString; lf; flush.	
].

debug := [:string|
	true ifTrue:[
		log value: string
	]
].

responseBlock := [:request|
	|requestArray operation operationArgs response lf|
	debug value: 'responseBlock #start'.
	lf := Character lf asString.
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
	#execute == operation ifTrue: [			
		response := (
			"evaluate will create block with request action, execute it with our environment"
			[operationArgs first evaluate asString] on: ExceptionA do: [:e| 
				|stream messageString gsStackReportString requestString|
				stream := WriteStream on: String new.
				messageString := e description.
				gsStackReportString := GsProcess stackReportToLevel: 50. 
				requestString := request.
				
				stream 
					nextPutAll: 'UnhandledError';
					lf;
					nextPutAll: messageString size asString; space; 
					nextPutAll: requestString size asString; space;
					nextPutAll: gsStackReportString size asString;  					
					lf; 
					nextPutAll: messageString; lf; 
					nextPutAll: requestString; lf;
					nextPutAll: gsStackReportString; 					
					lf.
				stream contents
			]
		)
	].	
	#info == operation ifTrue:[
		|stream|
		stream := WriteStream on: String new.
		stream 
			nextPutAll: portNumber asString;
			lf;
			nextPutAll: clientId asString;
			lf;
			nextPutAll: startTime asString;
			lf;
			nextPutAll: lastRequestTime asString;
			lf;
			nextPutAll: System myUserProfile userId asString;
			lf;
			nextPutAll: System stoneName asString;		
			lf. 		
		response := stream contents	
	].
	#die == operation ifTrue:[
		log value: 'Received die command - stopping session server on port ', portNumber asString.
		serverStop := true.
		response := 'ok'
	].	
	response isNil ifTrue:[
		response := 'ServerError',lf, 'Unknown operation ''', operation asString, '''.'.
	].	
	
	debug value: 'responseBlock #end'.
	response
].

portNumber isEmpty ifTrue:[
	log value: 'Error: Missing required parameter portNumber.'.
	log value: 'usage: runSessionServer.sh <portNumber>'.
	^nil.
].

log value: 'Starting new session server on port ', portNumber asString, ' for client ', clientId asString.
[
	| readLine readContent errStr chunk header|
	[
		|headers contentLength requestBody response responseBody|
		readLine := [:socket|
			|line result buffer|
			debug value: 'readLine#start'.
			line := WriteStream on: String new.
			result := nil.
			buffer := String new.
			[(result := socket read: 1 into: buffer startingAt: 1) ~~ nil and: [buffer isEmpty not and: [(buffer first) ~= Character lf]]] whileTrue:[
				line nextPutAll: buffer.
			].	
			result == nil ifTrue:[
				Object error: 'Error reading line'
			].
			debug value: 'readLine#end'.
			line contents trimWhiteSpace	
		].
		readContent := [:socket :size|
			|remains stream|
			debug value: 'readContent#start'.
			
			remains := size.
			stream := WriteStream on: String new.
			
			[remains >0 and: [(chunk := socket readString: (10 min: remains)) ~~ nil]] whileTrue:[
				stream nextPutAll: chunk.
				remains := remains - chunk size.
			].
			debug value: 'readContent#end'.
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
			debug value: 'socketAccept'.
			socket := server accept.
			lastRequestTime := DateTime now.
			socket == nil ifTrue: [
				debug value: 'socketNil'.
				errStr := server lastErrorString.
				server close.
				Object error: errStr.
			].
			debug value: 'socketLinger'.
			socket linger: true length: 10.  "wait after closing until data is processed"

			"read initial line"
			debug value: 'readInitialLine'.
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
			
			debug value: 'readRequestBody'.
			requestBody := readContent value: socket value: contentLength asInteger.			
			
			debug value: 'createResponseBody'.
			responseBody := responseBlock value: requestBody.
			
			response := WriteStream on: String new.
			response nextPutAll: 'HTTP/1.0 200 OK';nextPutAll: crlf.
			response nextPutAll: 'Content-Type: text/html';nextPutAll: crlf.
			response nextPutAll: 'Content-Length: ', responseBody size asString; nextPutAll: crlf.
			response nextPutAll: crlf.
			response nextPutAll: responseBody.
			
			debug value: 'writeResponseBody'.
			socket write: response contents.
			debug value: 'socketClose'.
			socket close.
		].
	] on: ExceptionA do:[:ex|
		|exReport|
		debug value: 'exceptionOccurred', ex description.
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