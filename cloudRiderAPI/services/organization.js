var logger = require('tracer').console();

//
function getOrg(req, res) {
    logger.info("inside getOrg()")
    res.send({"message":"hello from getOrg!!!"})
}

function createOrg(req, res) {
    logger.info("inside createOrg()")
    logger.info(JSON.stringify(req.body))
    res.send(req.body)
}

function delOrg (req, res) {
    logger.info("inside delOrg()")
    logger.info(req.body)
    res.send({"message":"Successfully deleted Org!"})
}

module.exports ={
   getOrg,
   createOrg,
   delOrg
}