#!/bin/bash


DIR_OUTPUT_MAIN=Output
DIR_OUTPUT=Output_Peer

peerName=""



 #Main 

if [ "$#" -ne 1 ]; then
  showUsage
  exit 1
fi

peerName=$1
