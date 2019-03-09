var mockData = require('../mocks/peerDataforAGivenPeer.json')
var logger = require('tracer').console();
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
    return {data: result };
}

var mockDetails = function(mockData, res) {
    let result = {};
    let orgs=[];
    for(let ele in mockData) {
        let str = mockData[ele].Endpoint.split(".");
        let orgName = str[1] +"."+ str[2] +"."+ str[3];
        let peerName = str[0];
        let chaincodecount=0
        //let chaincodeArr = mockData[ele].Chaincodes;
        if(mockData[ele].Chaincodes && mockData[ele].Chaincodes.length > 0) {
            chaincodecount = mockData[ele].Chaincodes.length
        }
        let peer = {
            "name":peerName,
            "ledger":"",
            "chaincodecount": chaincodecount,
            "chaincodes": mockData[ele].Chaincodes

        }
        if(result[orgName]) {
            result[orgName].push(peer);
        }else{
            result[orgName] = [peer]
        }
    }
    let finalResult=[]
    for(let r in result){
        let result1 = {
            "orgname": r,
            "peers":result[r]
        }

        finalResult.push(result1)
    }
    return finalResult;
}

var listOfChaincodes = function(peerName, orgName) {
    
    let data = mockDetails(mockData);
    let ele = _.find(data, ['orgname', orgName]);

    let result = _.find(ele.peers,["name",peerName])
    return result;
}

var getNodes = function () {
    logger.info("inside getNodes()")
    let finalResult = [];
    let org = getMockDetails();
    let cons = getNetworkData();
    let count = 0;
    for (let ele of cons["data"]) {
        let obj = {};
        count++;
        obj["id"] = count,
        obj["name"] = ele.consortiumname;
        obj["group"] = "consortium"
        finalResult.push(obj)
    }

    for (let ele of org) {
        let obj = {};
        count++;
        obj["id"] = count,
        obj["name"] = ele.orgname;
        obj["group"] = "organization"
        finalResult.push(obj)

        for (let peer of ele["peers"]) {
            let obj = {};
            count++;
            obj["id"] = count,
            obj["name"] = peer.name;
            obj["group"] = "peer"
            finalResult.push(obj)
        }
    }
    let nameOfChannel = "sample-channel"
    for(let i =1; i<=3; i++) {
        let obj = {};
        count++;
        obj["id"] = count;
        obj["name"] = nameOfChannel+i;
        obj["group"] = "channel"
        finalResult.push(obj)
    }
    return finalResult;
}


module.exports = {
    mockDetails,
    getMockDetails,
    getNetworkData,
    listOfChaincodes,
    getNodes
}
