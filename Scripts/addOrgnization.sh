#!/bin/bash
#set -x

DIR_OUTPUT_MAIN=Output
DIR_OUTPUT=Output_Org
DIR_CHANNEL_ARTIFACTS=channel-artifacts
DIR_YAML=New/YAML
DIR_ADD_ORG=${DIR_YAML}/ADD_ORG

#Files - New Org
FILE_NEW_ORG_CRYPTO=new-crypto.yaml

#Files - configtx
FILE_CONFIGTX=configtx.yaml


fileOrgCryptio=""
orgShortName=""

DIR_SCRIPTS=scripts
SCRIPT_STEP1=step1.sh
SCRIPT_UTILS=utils_org.sh

showUsage(){
    echo -e "./addOrgnization.sh <domain> <Org Name> <Channel Name> <Orderer Name> <Orderer Host Name>"
 }


generateCryptoConfig(){

   sed "s#PARAM_NAME#${orgName}#g; s#PARAM_DOMAIN#${orgShortName}.${domain}#g; " ${DIR_ADD_ORG}/${FILE_NEW_ORG_CRYPTO} >> ${DIR_OUTPUT}/${orgShortName}-crypto.yaml

}

generateChannelArtifacts(){

    HOST_PEER="peer0.${orgShortName}.${domain}"
    HOST_PEER_PORT=7051

    sed "s#PARAM_ORG#${orgName}#g; s#PARAMORG#${orgShortName}.${domain}#g; s#PARAM_PEER#${HOST_PEER}#g;s#PARAM_PORT_1#${HOST_PEER_PORT}#g; " ${DIR_ADD_ORG}/${FILE_CONFIGTX} > ${DIR_OUTPUT}/${FILE_CONFIGTX}

}

generateConfigTX(){

   #Generate Step 1 Shell Script

   sed "s#PARAM_ORG#${orgName}#g; s#PARAM_SHORT_ORG#${orgShortName}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostName}#g;s#PARAM_PORT_1#${HOST_PEER_PORT}#g; " ${DIR_SCRIPTS}/${SCRIPT_STEP1} > ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_STEP1}

   sed "s#PARAM_DOMAIN#${domain}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostName}#g; s#PARAM_PEER#${HOST_PEER}#g;s#PARAM_PORT_1#${HOST_PEER_PORT}#g; " ${DIR_SCRIPTS}/${SCRIPT_UTILS} > ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_UTILS}   

   chmod a+x ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_STEP1} ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_UTILS}   

   docker exec ${orgName}-cli ${DIR_SCRIPTS}/${orgShortName}${SCRIPT_STEP1} $channel $CLI_DELAY $LANGUAGE $CLI_TIMEOUT $VERBOSE $domain $ordererHostName

}

signConfigtxAsPeerOrg() {
  PEERORG=$1
  TX=$2
  setGlobals 0 $PEERORG

  peer channel signconfigtx -f "${TX}"

}


createConfigUpdate() {
  CHANNEL=$1
  ORIGINAL=$2
  MODIFIED=$3
  OUTPUT=$4

  configtxlator proto_encode --input "${ORIGINAL}" --type common.Config >original_config.pb
  configtxlator proto_encode --input "${MODIFIED}" --type common.Config >modified_config.pb
  configtxlator compute_update --channel_id "${CHANNEL}" --original original_config.pb --updated modified_config.pb >config_update.pb
  configtxlator proto_decode --input config_update.pb --type common.ConfigUpdate >config_update.json
  echo '{"payload":{"header":{"channel_header":{"channel_id":"'$CHANNEL'", "type":2}},"data":{"config_update":'$(cat config_update.json)'}}}' | jq . >config_update_in_envelope.json
  configtxlator proto_encode --input config_update_in_envelope.json --type common.Envelope >"${OUTPUT}"

}



setOrdererGlobals() {
  CORE_PEER_LOCALMSPID="${ordererName}MSP"
  CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/${domain}/orderers/${ordererHostName}/msp/tlscacerts/tlsca.${domain}-cert.pem
  CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/${domain}/users/Admin@${domain}/msp
}






addNewOrgnization(){

    cd ${DIR_OUTPUT}

    cryptogen generate --config=${orgShortName}-crypto.yaml

    export FABRIC_CFG_PATH=$PWD
    configtxgen -printOrg ${orgName}MSP > ${DIR_CHANNEL_ARTIFACTS}/${orgName}.json
    mkdir -p ${orgName}-artifacts
    cp -r ../Output/crypto-config/ordererOrganizations ${orgName}-artifacts/crypto-config/


}

 #Main 

if [ "$#" -ne 5 ]; then
  showUsage
  exit 1
fi

if [ -d "${DIR_OUTPUT}" ]; then
    echo -e "Deleting Old Output files"
    rm -rf ${DIR_OUTPUT}
fi

mkdir -p ${DIR_OUTPUT}
mkdir -p ${DIR_OUTPUT}/${DIR_CHANNEL_ARTIFACTS}

domain=$1
orgName=$2
channel=$3
ordererName=$4
ordererHostName=$5

fileOrgCryptio=${orgName}-crypto.yaml
orgShortName=`echo "${orgName,,}"`


# noOfOrgs=$5
# network=$6
# noOfPeers=$7
# startPort=$8
# ordererPort=$9
# startPortPeer=${10}

generateCryptoConfig
generateChannelArtifacts
generateConfigTX

#addNewOrgnization
