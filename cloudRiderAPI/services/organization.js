var logger = require('tracer').console();

//function to get organizations
async function getOrg(req, res) {
    logger.info("inside getOrg()")
    res.send({"message":"hello from getOrg!!!"})
}
//function to get organizations by name
async function getOrgByName(req, res) {
    logger.info("inside getOrg()")
    res.send({"message":"hello from getOrg!!!"})
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
   getOrg,
   getOrgByName,
   createOrg,
   delOrgByName
}