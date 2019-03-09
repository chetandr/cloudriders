#!/bin/bash
set -x

#$1 peer name
#$2 org name
#$3 User name
#$4 MSP name
#$5 channel name


skval=`docker exec cli ls ./crypto/peerOrganizations/"${2}"/users/"${3}"@"${2}"/msp/keystore/`
echo "${skval}"
docker exec cli discover --configFile conf.yaml --peerTLSCA ./crypto/peerOrganizations/"${2}"/users/"${3}"@"${2}"/tls/ca.crt --userKey "./crypto/peerOrganizations/${2}/users/${3}@${2}/msp/keystore/${skval}" --userCert ./crypto/peerOrganizations/"${2}"/users/"${3}"\@"${2}"/msp/signcerts/"${3}"\@"${2}-cert.pem" --MSP "${4}" saveConfig
docker exec cli discover --configFile conf.yaml peers --channel "${5}" --server "${1}":7051
