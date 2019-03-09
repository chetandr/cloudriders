var mockData = require('../../mocks/peerDataforAGivenPeer.json')
var logger = require('tracer').console();

var mockDetails = function(mockData, res) {
    let result = {};
    let orgs=[];
    for(let ele in mockData) {
        let str = mockData[ele].Endpoint.split(".");
        let orgName = str[1] +"."+ str[2] +"."+ str[3];
        let peerName = str[0];
        //let chaincodeArr = mockData[ele].Chaincodes;
        let peer = {
            "name":peerName,
            "ledger":"",
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
    // res.send(finalResult)
    // res.end()
}

module.exports = {
    mockDetails
}