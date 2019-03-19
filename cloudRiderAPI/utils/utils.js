var mockData = require('../mocks/peerDataforAGivenPeer.json')
var logger = require('tracer').console();
const {executeShell} = require('./shellExecutor');

const _ = require("lodash")

var getMockDetails = function () {
    let result = mockDetails(mockData);
    return result;
}

var getNetworkData = function () {
    let data = getMockDetails();

    let conName = "example.com"
    let result = [];

    let consortium = {
        "consortiumname": conName,
        "orgs": data
    }
    result.push(consortium);
    return { data: result };
}

var mockDetails = function (mockData, res) {
    let result = {};
    let orgs = [];
    for (let ele in mockData) {
        let str = mockData[ele].Endpoint.split(".");
        let orgName = str[1] + "." + str[2] + "." + str[3];
        let peerName = str[0];
        let chaincodecount = 0
        //let chaincodeArr = mockData[ele].Chaincodes;
        if (mockData[ele].Chaincodes && mockData[ele].Chaincodes.length > 0) {
            chaincodecount = mockData[ele].Chaincodes.length
        }
        let peer = {
            "name": peerName,
            "ledger": "",
            "chaincodecount": chaincodecount,
            "chaincodes": mockData[ele].Chaincodes

        }
        if (result[orgName]) {
            result[orgName].push(peer);
        } else {
            result[orgName] = [peer]
        }
    }
    let finalResult = []
    for (let r in result) {
        let result1 = {
            "orgname": r,
            "peers": result[r]
        }

        finalResult.push(result1)
    }
    return finalResult;
}

var listOfChaincodes = function (peerName, orgName) {

    let data = mockDetails(mockData);
    let ele = _.find(data, ['orgname', orgName]);

    let result = _.find(ele.peers, ["name", peerName])
    return result;
}

var getNodes = async function () {
    
    const str = await executeShell('CONTAINER_LIST');//"peer2.org11.hyper.com\npeer1.org11.hyper.com\npeer0.org11.hyper.com\npeer0.org2.hyper.com\npeer1.org2.hyper.com\npeer1.org11.hyper1.com\npeer0.org11.hyper1.com\npeer0.org2.hyper1.com\npeer1.org2.hyper1.com"
    console.log("OUTPUT")
    
    const files = str.split("\n");
    console.log(files);
    const _ = require('lodash');

    const map = {};

    const orgs = {};

    const data = [];

    const edges = [];

    files.forEach(file => {
        if(file != "") {
        const f = file.split(".");

            if (orgs[`${f[1]}.${f[2]}.${f[3]}`]) orgs[`${f[1]}.${f[2]}.${f[3]}`].push(f[0]);

            else orgs[`${f[1]}.${f[2]}.${f[3]}`] = [f[0]];
        }
    });

    

    for (k in orgs) {

        if (orgs.hasOwnProperty(k)) {

            let kp = k.split(".");

            if (map[`${kp[1]}.${kp[2]}`]) map[`${kp[1]}.${kp[2]}`][kp[0]] = orgs[k];

            else map[`${kp[1]}.${kp[2]}`] = { [kp[0]]: orgs[k] }

        }

    }

    let cnt = 0;

    _.forEach(map, (orgs, key) => {

        cnt++;

        data.push({ id: cnt, label: key, group: "consortium" });

        let consrtId = cnt

        _.forEach(orgs, (peers, org) => {

            cnt++;

            data.push({ id: cnt, label: org, group: "org" });

            edges.push({ from: cnt, to: consrtId });

            let orgId = cnt;

            _.forEach(peers, peer => {

                cnt++;

                data.push({ id: cnt, label: peer, group: "peer" });

                edges.push({ from: cnt, to: orgId });

                let peerId = cnt;

            })

        })

    })
    finalResult = { map: map, data: data, edges: edges }

    return finalResult;
}


module.exports = {
    mockDetails,
    getMockDetails,
    getNetworkData,
    listOfChaincodes,
    getNodes
}
