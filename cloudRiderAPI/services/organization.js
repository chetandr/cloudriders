var logger = require('tracer').console();
var mockData = require('../../mocks/peerDataforAGivenPeer.json')
var utils = require('../utils/utils.js')
const _ = require("lodash");
const shellExecuter =  require("../utils/shellExecutor")
//function to get organizations
async function getOrgs(req, res) {
    logger.info("inside getOrg()");
    let output = await shellExecuter.executeShell('KEY_STORE_MAP');
    logger.info(typeof output)
    let map = {}
    output = output.split('\n');

    output = _.compact(output);
    for(let entry of output){
        let arr = entry.split('/');
        console.log(entry, _.endsWith(entry,'peers'))
        if(!_.isEqual(arr[2], "ordererOrganizations") && !_.endsWith(entry,'peers')){
            
            let org = arr[3];
            let peer = arr[5].split('.')[0]
            if(map[org]) map[org].push(peer);
            else map[org] = [peer];
        }else{
            console.log('ORDERORG', arr[2])
        }

    }
    let finalResult = []
    for(let org in map){
        let obj ={
            "orgname":org,
            "peers": _.uniq(map[org])
        }
        finalResult.push(obj);
    }
    //let data = utils.mockDetails(mockData, res)
    res.send(finalResult)
    res.end()
}

//function to get organizations by name
async function getOrgByName(req, res) {
    logger.info("inside getOrg()")
    let orgName = req.params.name;
    logger.info("orgName is: ",orgName);
    let data = utils.mockDetails(mockData, res)
    let result = _.find(data, function(o) { return o.orgname === orgName; });
    res.send(result)
    res.end()
}

// function to create organizations
async function createOrg(req, res) {
    logger.info("inside createOrg()")
    logger.info(JSON.stringify(req.body))
    res.send(req.body)
}
// function to delete organizations
async function delOrgByName (req, res) {
    logger.info("inside delOrg()")
    logger.info(req.body)
    res.send({"message":"Successfully deleted Org!"})
}

module.exports = {
   getOrgs,
   getOrgByName,
   createOrg,
   delOrgByName
}
