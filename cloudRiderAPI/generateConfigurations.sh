#!/bin/bash

#Common 
FULL_NAME_ORDERER=""
FULL_NAME_PEER=""
orgShortName=""
FULL_NAME_ORDERER=""
FULL_NAME_ORDERER_MAIN=""


#Input Parameters
domain=""
ordererName=""
ordererHostname=""
orgName=""
noOfOrgs=""
network=""
noOfPeers=""
startPort=""
ordererPort=""
startPortPeer=""
channelName=""


DIR_OUTPUT=Output
DIR_CHANNEL_ARTIFACTS=channel-artifacts
DIR_YAML=YAML
DIR_CRYPTO_CONFIG=${DIR_YAML}/crypto-config
DIR_DOCKER_COMPOSE=${DIR_YAML}/docker-compose
DIR_BASE=${DIR_YAML}/base
DIR_CONFIGTX=${DIR_YAML}/configtx
DIR_SCRIPTS=scripts

#Script Files
SCRIPT_BASIC=script_basic.sh
SCRIPT_UTILS=utils_org.sh

#Files - Crypto-Config.yaml
FILE_CRYPTO_CONFIG=crypto-config.yaml
FILE_PEERORGS=PeerOrgs.yaml

#Files - Docker Compose
FILE_DOCKER_COMPOSE=docker-compose-e2e.yaml
FILE_DOCKER_COMPOSE_CLI=docker-compose-cli.yaml
FILE_CA=ca.yaml
FILE_SERVICE=service.yaml
FILE_CI=cli.yaml

#Files - base
FILE_ORDERER=orderer.yaml
FILE_PEER=peer.yaml
FILE_DOCKER_COMPOSE_BASE=docker-compose-base.yaml

#Files - configtx
FILE_CONFIGTX=configtx.yaml
FILE_CONFIGTX_ORG=org.yaml
FILE_CONFIGTX_CAPABILITIES=capabilities.yaml

showUsage(){

    echo -e "./generateConfigurations.sh <domain> <ordererName> <ordererHostname> <Org Name> <No Of Orgs> <Network> <No Of Peers> <Start Port> <docker repo> <catalog image name> <catalog image tag> <adapter image name> <adapter image tag>"

 }

generateCryptoConfig(){

    sed "s#PARAM_ORDERER_NAME#${ordererName}#g; s#PARAM_DOMAIN#${domain}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostname}#g;" ${DIR_CRYPTO_CONFIG}/${FILE_CRYPTO_CONFIG} > ${DIR_OUTPUT}/${FILE_CRYPTO_CONFIG}

    for number in $(seq 1 ${noOfOrgs})
    do
        sed "s#PARAM_NAME#${orgName}$number#g; s#PARAM_DOMAIN#${orgShortName}$number.${domain}#g; s#PARAM_PEER_NOS#${noOfPeers}#g;" ${DIR_CRYPTO_CONFIG}/${FILE_PEERORGS} >> ${DIR_OUTPUT}/${FILE_CRYPTO_CONFIG}
    done
    
}

generateDockerComposeConfig(){

    #FULL_NAME_ORDERER=${ordererHostname}.${domain}:
    #FULL_NAME_ORDERER_MAIN=${ordererHostname}.${domain}:
    
    for number in $(seq 1 ${noOfOrgs})
    do
        for number1 in $(seq 0 ${noOfPeers})
        do
            FULL_NAME_ORDERER=${FULL_NAME_ORDERER}"\n  "peer${number1}.${orgShortName}${number}.${domain}:
        done    
    done


    sed "s#PARAM_VOLUMNS#${FULL_NAME_ORDERER}#g; s#PARAM_NETWORK#${network}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostname}#g;" ${DIR_DOCKER_COMPOSE}/${FILE_DOCKER_COMPOSE} > ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE}

    tmpStartPort=$startPort

    for number in $(seq 1 ${noOfOrgs})
    do

        if [ ${number} == "1" ]; then # Master Peer
            sed "s#PARAM_ORG#${orgShortName}$number#g; s#PARAM_DOMAIN#${domain}#g;  s#NUMBER#$number#g; s#PARAM_NETWORK#${network}#g;  s#PARAM_PORT_1#${startPort}#g; s#PARAM_PORT_2#${startPort}#g" ${DIR_DOCKER_COMPOSE}/${FILE_CA} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE}
        else
            sed "s#PARAM_ORG#${orgShortName}$number#g; s#PARAM_DOMAIN#${domain}#g;  s#NUMBER#$number#g; s#PARAM_NETWORK#${network}#g;  s#PARAM_PORT_1#${tmpStartPort}#g; s#PARAM_PORT_2#${startPort}#g" ${DIR_DOCKER_COMPOSE}/${FILE_CA} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE}
        fi     

        tmpStartPort=`expr $tmpStartPort + 1`

    done


    sed "s#PARAM_SERVICE_NAME#${FULL_NAME_ORDERER_MAIN}#g; " ${DIR_DOCKER_COMPOSE}/${FILE_SERVICE} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE}

    for number in $(seq 1 ${noOfOrgs})
    do
        for number1 in $(seq 0 ${noOfPeers})
        do
            sed "s#PARAM_SERVICE_NAME#peer${number1}.${orgShortName}${number}.${domain}#g; " ${DIR_DOCKER_COMPOSE}/${FILE_SERVICE} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE}
        done    
    done    

}

generateDockerComposeCliConfig(){

    FULL_NAME_ORDERER=${ordererHostname}.${domain}:
   
    for number in $(seq 1 ${noOfOrgs})
    do
        for number1 in $(seq 0 ${noOfPeers})
        do
            FULL_NAME_ORDERER=${FULL_NAME_ORDERER}"\n  "peer${number1}.${orgShortName}${number}.${domain}:
        done    
    done


    sed "s#PARAM_VOLUMNS#${FULL_NAME_ORDERER}#g; s#PARAM_NETWORK#${network}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostname}#g;" ${DIR_DOCKER_COMPOSE}/${FILE_DOCKER_COMPOSE_CLI} > ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE_CLI}

    FULL_NAME_ORDERER_MAIN_NAME=${ordererHostname}.${domain}

    sed "s#PARAM_SERVICE_NAME#${FULL_NAME_ORDERER_MAIN_NAME}#g; " ${DIR_DOCKER_COMPOSE}/${FILE_SERVICE} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE_CLI}

    for number in $(seq 1 ${noOfOrgs})
    do
        for number1 in $(seq 0 ${noOfPeers})
        do
            sed "s#PARAM_SERVICE_NAME#peer${number1}.${orgShortName}${number}.${domain}#g; " ${DIR_DOCKER_COMPOSE}/${FILE_SERVICE} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE_CLI}
        done    
    done    

    FULL_NAME_ORDERER_CLI="- "${ordererHostname}.${domain}
   
    for number in $(seq 1 ${noOfOrgs})
    do
        for number1 in $(seq 0 ${noOfPeers})
        do
            FULL_NAME_ORDERER_CLI=${FULL_NAME_ORDERER_CLI}"\n      - "peer${number1}.${orgShortName}${number}.${domain}
        done    
    done

    PEER_NAME="peer0.${orgShortName}1.${domain}"
    ORG_NAME="${orgShortName}1.${domain}"

    sed "s#PARAM_VOLUMNS#${FULL_NAME_ORDERER_CLI}#g; s#PARAM_NETWORK#${network}#g; s#PARAM_SERVICE_NAME#cli.${domain}#g; s#PARAM_PEER#${PEER_NAME}#g; s#PARAMORG#${orgName}1#g; s#PARAM_ORG#${ORG_NAME}#g; s#PARAM_PORT#${startPortPeer}#g;" ${DIR_DOCKER_COMPOSE}/${FILE_CI} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE_CLI}



}

generateBaseConfig(){

    FULL_NAME_ORDERER_MAIN_NAME=${ordererHostname}.${domain}

    sed "s#PARAM_SERVICE_NAME#${FULL_NAME_ORDERER_MAIN_NAME}#g; s#PARAM_DOMAIN#${domain}#g;  s#NUMBER#$number#g; s#PARAM_NETWORK#${network}#g;  s#PARAM_PORT_1#${ordererPort}#g; s#PARAM_ORDERER_NAME#${ordererName}#g; " ${DIR_BASE}/${FILE_ORDERER} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE_BASE}

    mainPeerPort1=$startPortPeer
    mainPeerPort2=`expr $startPortPeer + 2`
    tmpPortPeer=`expr $mainPeerPort2 + 2`

    for number in $(seq 1 ${noOfOrgs})
    do
        for number1 in $(seq 0 ${noOfPeers})
        do

            if [ ${number} == "1" ] && [ ${number1} == "0" ]; then # Master Peer

                sed "s#PARAM_SERVICE_NAME#peer${number1}.${orgShortName}${number}.${domain}#g;  s#PARAM_ORG#${orgShortName}$number.${domain}#g; s#NUMBER#${number}#g;  s#PARAMORG#${orgName}#g; s#PARAM_PORT_0#${startPortPeer}#g; s#PARAM_PORT_1#${mainPeerPort1}#g; s#PARAM_PORT_2#${mainPeerPort1}#g; s#PARAM_PORT_3#${mainPeerPort2}#g; s#PARAM_PORT_4#${mainPeerPort2}#g; s#PARAMSHORTORG#${orgShortName}#g  " ${DIR_BASE}/${FILE_PEER} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE_BASE}

            else 
                
                tmpPortPeer1=`expr $tmpPortPeer + 2`

                sed "s#PARAM_SERVICE_NAME#peer${number1}.${orgShortName}${number}.${domain}#g;  s#PARAM_ORG#${orgShortName}$number.${domain}#g; s#NUMBER#${number}#g;  s#PARAMORG#${orgName}#g; s#PARAM_PORT_0#${startPortPeer}#g;  s#PARAM_PORT_1#${tmpPortPeer}#g; s#PARAM_PORT_2#${mainPeerPort1}#g; s#PARAM_PORT_3#${tmpPortPeer1}#g; s#PARAM_PORT_4#${mainPeerPort2}#g; s#PARAMSHORTORG#${orgShortName}#g  " ${DIR_BASE}/${FILE_PEER} >> ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE_BASE}

                tmpPortPeer=`expr $tmpPortPeer1 + 2`

            fi

        done    
    done    


    mkdir -p ${DIR_OUTPUT}/base
    mv ${DIR_OUTPUT}/${FILE_DOCKER_COMPOSE_BASE} ${DIR_OUTPUT}/base/
    cp ${DIR_BASE}/peer-base.yaml ${DIR_OUTPUT}/base/


}


generateConfigTX(){

    sed "s#PARAM_DOMAIN#${domain}#g; s#PARAM_ORDERER_NAME#${ordererName}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostname}#g;" ${DIR_CONFIGTX}/${FILE_CONFIGTX} > ${DIR_OUTPUT}/${FILE_CONFIGTX}

    #ORG_NAMES="- *${orgName}$noOfOrgs"

    for number in $(seq 1 ${noOfOrgs})
    do

        sed "s#PARAM_ORG#${orgShortName}${number}.${domain}#g;  s#PARAM_SHORT_ORG#${orgName}$number#g; s#PARAM_PEER#peer0.${orgShortName}${number}.${domain}#g;  s#PARAM_PORT_1#${startPortPeer}#g; " ${DIR_CONFIGTX}/${FILE_CONFIGTX_ORG} >> ${DIR_OUTPUT}/${FILE_CONFIGTX}

        ORG_NAMES=${ORG_NAMES}"\n                    - *"${orgName}$number
        ORGNAMES=${ORGNAMES}"\n                - *"${orgName}$number

    done        

    sed "s#PARAM_ORDERER_NAME#${ordererName}#g; s#PARAM_PORT_1#${ordererName}#g; s#PARAM_ORG#${ORG_NAMES}#g; s#PARAMORG#${ORGNAMES}#g;" ${DIR_CONFIGTX}/${FILE_CONFIGTX_CAPABILITIES} >> ${DIR_OUTPUT}/${FILE_CONFIGTX}


}

changeScriptBasic(){

   CURRENT_ORG=$1

   neworgShortName=`echo "${CURRENT_ORG,,}"`

   echo "ordererHostname : $ordererHostname"

   sed "s#PARAM_SHORT_ORG#${orgShortName}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostname}.${domain}#g; s#PARAM_PORT_1#${HOST_PEER_PORT}#g; s#PARAM_ORG_NOS#${noOfOrgs}#g; s#PARAM_PEER_NOS#${noOfPeers}#g; s#PARAMORG#${orgName}#g" ${DIR_SCRIPTS}/${SCRIPT_BASIC} > ${DIR_OUTPUT}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_BASIC}   

   sed "s#PARAM_SHORT_ORG#${orgShortName}#g; s#PARAM_DOMAIN#${domain}#g; s#PARAM_ORDERER_HOSTNAME#${ordererHostname}.${domain}#g; s#PARAM_PEER#${HOST_PEER}#g; s#PARAM_ORG#${orgName}#g; s#PARAMORG_HOSTNAME#${orgShortName}1.${domain}#g; s#PARAM_PORT_1#${startPortPeer}#g; s#PARAMORG_DOMAIN#${CURRENT_ORG}#g; s#PARAMORGDOMAIN#${neworgShortName}.${domain}#g;" ${DIR_SCRIPTS}/${SCRIPT_UTILS} > ${DIR_OUTPUT}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_UTILS}    

   chmod a+x ${DIR_OUTPUT}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_BASIC} ${DIR_OUTPUT}/${DIR_SCRIPTS}/${orgShortName}${SCRIPT_UTILS}   

}


 #Main 

if [ "$#" -ne 11 ]; then
  showUsage
  exit 1
fi

domain=$1
ordererName=$2
ordererHostname=$3
orgName=$4
noOfOrgs=$5
network=$6
noOfPeers=$7
startPort=$8
ordererPort=$9
startPortPeer=${10}
channelName=${11}



if [ -d "${DIR_OUTPUT}" ]; then
    echo -e "Deleting Old Output files"
    rm -rf ${DIR_OUTPUT}
fi

mkdir -p ${DIR_OUTPUT}
mkdir -p ${DIR_OUTPUT}/${DIR_CHANNEL_ARTIFACTS}

orgShortName=`echo "${orgName,,}"`

FULL_NAME_ORDERER=${ordererHostname}.${domain}:
FULL_NAME_ORDERER_MAIN=${ordererHostname}.${domain}:

generateCryptoConfig

noOfPeers=`expr $noOfPeers - 1`

generateDockerComposeConfig
generateDockerComposeCliConfig
generateBaseConfig
generateConfigTX

#exit 0

#Added for testing

CURRENT_DIR=$PWD

export CHANNEL_NAME=$channelName
export IMAGE_TAG=latest
cd ${DIR_OUTPUT}
cryptogen generate --config=./crypto-config.yaml

configtxgen -profile ThreeOrgsOrdererGenesis -outputBlock ${DIR_CHANNEL_ARTIFACTS}/genesis.block

configtxgen -profile ThreeOrgsChannel -outputCreateChannelTx ${DIR_CHANNEL_ARTIFACTS}/channel.tx -channelID $CHANNEL_NAME

for number in $(seq 1 ${noOfOrgs})
do

    ORG=${orgName}$number"MSP"
    ORG_TX=${orgName}$number"MSPanchors"
    configtxgen -profile ThreeOrgsChannel -outputAnchorPeersUpdate ${DIR_CHANNEL_ARTIFACTS}/$ORG_TX.tx -channelID $CHANNEL_NAME -asOrg $ORG

done
#exit 0

cd $CURRENT_DIR

cp -R scripts ${DIR_OUTPUT}/

cd ${DIR_OUTPUT}
docker-compose -f docker-compose-cli.yaml up -d

cd $CURRENT_DIR
#Change Scripts
changeScriptBasic ${orgName}1

#exit 0

#docker exec ${orgShortName}-cli ls -ltr ${DIR_SCRIPTS}
docker exec cli.${domain} ${DIR_SCRIPTS}/${orgShortName}${SCRIPT_BASIC} $CHANNEL_NAME $CLI_DELAY $LANGUAGE $CLI_TIMEOUT $VERBOSE

