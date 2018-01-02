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
# Script for starting broker server with default GLASS parameters.
#
# author: Martin Karpisek (martin.karpisek@gmail.com)
#
rm -f *.log
./runBrokerServer.sh seaside '' DataCurator swordfish glass glass default.config >>brokerServer.log 2>&1 &