var logger = require('tracer').console();
var mockData = require('../../mocks/peerDataforAGivenPeer.json')
var utils = require('../utils/utils.js')
const _ = require("lodash");
const {executeShell} =  require("../utils/shellExecutor")
//function to get organizations
async function getOrgs(req, res) {
    let nodes = await utils.getNodes();
    result = [];
    _.forEach(nodes.map, (map, con) => {
        _.forEach(map, (org, oname) => {
            console.log(org);
            result.push({orgName : oname, consortium: con, peers: org.length});
        })
    })
    res.send(result)
    res.end()
}
// ./addOrgnization.sh example.com Org3 mychannel Orderer orderer 5 byfn 11000
// function to create organizations
async function createOrg(req, res) {
    const {orgname,
    consortium,
    channelName,
    peers,
    ordererName,
    orderHostName,
    networkName,
    port} = req.body;
    logger.info("inside createOrg()")
    await executeShell("CREATE_ORG", [consortium, orgname, channelName, ordererName, orderHostName, peers, networkName, port]);
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
   createOrg,
   delOrgByName
}
