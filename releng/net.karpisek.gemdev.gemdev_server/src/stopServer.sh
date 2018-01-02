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
# Stops running broker or session server.
# @param portNumber of server to be stopped
#
# author: Martin Karpisek (martin.karpisek@gmail.com)
#

export PORT_NUMBER=$1

topaz <<EOF
set gems seaside u DataCurator p swordfish
login
run
| portNumber log stopServer|
portNumber := System clientEnvironmentVariable: 'PORT_NUMBER'.

log := [:string|
	GsFile stdout nextPutAll: string; lf.
	GsFile stdout flush.
].

portNumber isEmpty ifTrue:[
	log value: 'Error: Missing required parameter portNumber.'.
	log value: 'usage: stopServer.sh <portNumber>'.
	^nil.
].

stopServer := [:sessionServerPortNumber|
	|socket|
	socket := GsSocket new.
	[
		(socket connectTo: sessionServerPortNumber asInteger) ifTrue:[
			|hostHeader stream| 
			hostHeader := 'Host: localhost:', sessionServerPortNumber asString.
			stream := WriteStream on: String new.
			#[
				'POST / HTTP/1.1',
				hostHeader,
				'Content-Type: text/plain',
				'Content-Length: 17',
				'',
				'#('''' '''' #die #())'
			] do:[:e|
				stream nextPutAll: e; nextPutAll: String crlf.
			].	
			socket write: stream contents.
		].
	] ensure: [socket close]
].

stopServer value: portNumber.
%
exit
EOF