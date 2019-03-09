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

export IMAGE_TAG=latest
fileOrgCryptio=""
orgShortName=""
noOfPeers=""
network=""
startPortPeer=""

DIR_SCRIPTS=scripts
SCRIPT_STEP1=step1.sh
SCRIPT_UTILS=utils_add_org.sh

DIR_YAML=YAML
DIR_CRYPTO_CONFIG=${DIR_YAML}/crypto-config
DIR_DOCKER_COMPOSE=${DIR_YAML}/docker-compose

FILE_DOCKER_COMPOSE_CLI=docker-compose-cli.yaml
FILE_CA=ca.yaml
FILE_SERVICE=service.yaml
FILE_PEER=peer.yaml
FILE_CI=cli_new.yaml

showUsage(){
    echo -e "./addOrgnization.sh <domain> <Org Name> <Channel Name> <Orderer Name> <Orderer Host Name>"
 }


generateCryptoConfig(){

   sed "s#PARAM_PEER_NOS#${noOfPeers}#g; s#PARAM_NAME#${orgName}#g; s#PARAM_DOMAIN#${orgShortName}.${domain}#g; " ${DIR_ADD_ORG}/${FILE_NEW_ORG_CRYPTO} >> ${DIR_OUTPUT}/${orgShortName}-crypto.yaml


    CURRENT_DIR=$PWD
    
    cd ${DIR_OUTPUT}
    cryptogen generate --config=${orgShortName}-crypto.yaml

    

    cd $CURRENT_DIR

    cp -R ${DIR_OUTPUT}/crypto-config/peerOrganizations/${orgShortName}.${domain} ${DIR_OUTPUT_MAIN}/crypto-config/peerOrganizations/

}

generateChannelArtifacts(){

    HOST_PEER="peer0.${orgShortName}.${domain}"
    HOST_PEER_PORT=7051

    sed "s#PARAM_ORG#${orgName}#g; s#PARAMORG#${orgShortName}.${domain}#g; s#PARAM_PEER#${HOST_PEER}#g;s#PARAM_PORT_1#${HOST_PEER_PORT}#g; " ${DIR_ADD_ORG}/${FILE_CONFIGTX} > ${DIR_OUTPUT}/${FILE_CONFIGTX}

    CURRENT_DIR=$PWD
    
    cd ${DIR_OUTPUT}

    export FABRIC_CFG_PATH=$PWD
    configtxgen -printOrg ${orgName}MSP > ${orgShortName}.json

    cd $CURRENT_DIR

    cp ${DIR_OUTPUT}/${orgShortName}.json ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/

    cp -r ${DIR_OUTPUT_MAIN}/crypto-config/ordererOrganizations ${DIR_OUTPUT}/crypto-config/


    #Copy Old org details


    

}

generateConfigTX(){

   #Generate Step 1 Shell Script


   echo "Name : "$ordererHostName

   sed "s#PARAM_ORG#${orgName}#g; s#PARAM_SHORT_ORG#${orgShortName}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostName}.${domain}#g;s#PARAM_PORT_1#${HOST_PEER_PORT}#g; " ${DIR_SCRIPTS}/${SCRIPT_STEP1} > ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_STEP1}

   #sed "s#PARAM_DOMAIN#${domain}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostName}#g; s#PARAM_PEER#${HOST_PEER}#g;s#PARAM_PORT_1#${HOST_PEER_PORT}#g; " ${DIR_SCRIPTS}/${SCRIPT_UTILS} > ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_UTILS}   

   sed "s#PARAM_ORDERER_NAME#${ordererName}#g; s#PARAM_SHORT_ORG#${orgShortName}#g; s#PARAM_DOMAIN#${domain}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostName}.${domain}#g; s#PARAM_PEER#${HOST_PEER}#g; s#PARAM_ORG#${orgName}#g; s#PARAMORG_HOSTNAME#${orgShortName}.${domain}#g; s#PARAM_PORT_1#${startPortPeer}#g; s#PARAMORG_DOMAIN#${CURRENT_ORG}#g; s#PARAMORGDOMAIN#${neworgShortName}.${domain}#g;" ${DIR_SCRIPTS}/${SCRIPT_UTILS} > ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_UTILS} 

   chmod a+x ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_STEP1} ${DIR_OUTPUT_MAIN}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_UTILS}   

   echo ${DIR_SCRIPTS}/${orgShortName}${SCRIPT_STEP1}
   docker exec cli.${domain} ${DIR_SCRIPTS}/${orgShortName}${SCRIPT_STEP1} $channel $CLI_DELAY $LANGUAGE $CLI_TIMEOUT $VERBOSE $domain $ordererHostName

  docker exec cli.${domain} peer channel update -f org3_update_in_envelope.pb -c ${channel} -o ${ordererHostName}.${domain}:7050 --tls --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/${domain}/orderers/${ordererHostName}.${domain}/msp/tlscacerts/tlsca.${domain}-cert.pem



}


generateDockerComposeCliConfig(){

    FULL_NAME_ORDERER=""
   
    for number1 in $(seq 0 ${noOfPeers})
    do
        FULL_NAME_ORDERER=${FULL_NAME_ORDERER}"\n  "peer${number1}.${orgShortName}${number}.${domain}:
    done    


    sed "s#PARAM_VOLUMNS#${FULL_NAME_ORDERER}#g; s#PARAM_NETWORK#${network}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostname}#g;" ${DIR_DOCKER_COMPOSE}/${FILE_DOCKER_COMPOSE_CLI} > ${DIR_OUTPUT_MAIN}/${orgShortName}${FILE_DOCKER_COMPOSE_CLI}

    FULL_NAME_ORDERER_MAIN_NAME=${ordererHostname}.${domain}

    # sed "s#PARAM_SERVICE_NAME#${FULL_NAME_ORDERER_MAIN_NAME}#g; " ${DIR_DOCKER_COMPOSE}/${FILE_SERVICE} >> ${DIR_OUTPUT}/${orgShortName}${FILE_DOCKER_COMPOSE_CLI}


    # for number1 in $(seq 0 ${noOfPeers})
    # do
    #     sed "s#PARAM_SERVICE_NAME#peer${number1}.${orgShortName}.${domain}#g; " ${DIR_DOCKER_COMPOSE}/${FILE_SERVICE} >> ${DIR_OUTPUT}/${orgShortName}${FILE_DOCKER_COMPOSE_CLI}
    # done    

    mainPeerPort1=$startPortPeer
    mainPeerPort2=`expr $startPortPeer + 2`
    tmpPortPeer=`expr $mainPeerPort2 + 2`

    for number1 in $(seq 0 ${noOfPeers})
    do

        tmpPortPeer1=`expr $tmpPortPeer + 2`

        sed "s#PARAM_PORT_1#${tmpPortPeer}#g; s#PARAM_PORT_2#7051#g; s#PARAM_PORT_3#${tmpPortPeer1}#g; s#PARAM_PORT_4#7053#g; s#PARAMORGNUMBER#${orgName}#g; s#PARAM_PORT_0#7051#g; s#PARAM_ORG#${orgShortName}.${domain}#g; s#PARAM_NETWORK#${network}#g; s#PARAM_SERVICE_NAME#peer${number1}.${orgShortName}.${domain}#g;" ${DIR_DOCKER_COMPOSE}/${FILE_PEER} >> ${DIR_OUTPUT_MAIN}/${orgShortName}${FILE_DOCKER_COMPOSE_CLI}

        tmpPortPeer=`expr $tmpPortPeer1 + 2`

    done     
   
    FULL_NAME_ORDERER_CLI=""
   
    for number1 in $(seq 0 ${noOfPeers})
    do
        FULL_NAME_ORDERER_CLI=${FULL_NAME_ORDERER_CLI}"\n      - "peer${number1}.${orgShortName}${number}.${domain}
    done    
  
    #sed "s#PARAM_PORT_1#${tmpPortPeer}#g; s#PARAM_PORT_2#7051#g; s#PARAM_PORT_3#${tmpPortPeer1}#g; s#PARAM_PORT_4#7053#g; s#PARAMORGNUMBER#${orgName}#g; s#PARAM_PORT_0#7051#g; s#PARAM_ORG#${orgShortName}.${domain}#g; s#PARAM_NETWORK#${network}#g; s#PARAM_SERVICE_NAME#peer${number1}.${orgShortName}.${domain}#g;" ${DIR_DOCKER_COMPOSE}/${FILE_CI} >> ${DIR_OUTPUT_MAIN}/${orgShortName}${FILE_DOCKER_COMPOSE_CLI}

    PEER_NAME="peer0.${orgShortName}.${domain}"
    ORG_NAME="${orgShortName}.${domain}"

    sed "s#PARAM_VOLUMNS#${FULL_NAME_ORDERER_CLI}#g; s#PARAM_NETWORK#${network}#g; s#PARAM_SERVICE_NAME#cli.${orgShortName}.${domain}#g; s#PARAM_PEER#${PEER_NAME}#g; s#PARAMORG#${orgName}#g; s#PARAM_ORG#${ORG_NAME}#g; s#PARAM_PORT#${startPortPeer}#g;" ${DIR_DOCKER_COMPOSE}/${FILE_CI} >> ${DIR_OUTPUT_MAIN}/${orgShortName}${FILE_DOCKER_COMPOSE_CLI}

    # PEER_NAME="peer0.${orgShortName}1.${domain}"
    # ORG_NAME="${orgShortName}1.${domain}"

    # sed "s#PARAM_VOLUMNS#${FULL_NAME_ORDERER_CLI}#g; s#PARAM_NETWORK#${network}#g; s#PARAM_SERVICE_NAME#cli.${domain}#g; s#PARAM_PEER#${PEER_NAME}#g; s#PARAMORG#${orgName}1#g; s#PARAM_ORG#${ORG_NAME}#g; s#PARAM_PORT#${startPortPeer}#g;" ${DIR_DOCKER_COMPOSE}/${FILE_CI} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE_CLI}

    CURRENT_DIR=$PWD
    cd $CURRENT_DIR

    cd ${DIR_OUTPUT_MAIN}
    docker-compose -f ${orgShortName}${FILE_DOCKER_COMPOSE_CLI} up -d

    cd $CURRENT_DIR

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

if [ "$#" -ne 8 ]; then
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
noOfPeers=$6
network=$7
startPortPeer=$8

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
generateDockerComposeCliConfig

#addNewOrgnization
