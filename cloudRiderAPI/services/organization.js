var logger = require('tracer').console();
var mockData = require('../../mocks/peerDataforAGivenPeer.json')
var utils = require('../utils/utils.js')
const _ = require("lodash")
//function to get organizations
async function getOrgs(req, res) {
    logger.info("inside getOrg()")
    let data = utils.mockDetails(mockData, res)
    res.send(data)
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