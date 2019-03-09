var logger = require('tracer').console();
//function to get consortium
async function getConsortiumByName (req, res) {
    logger.info("inside getConsortium()")
    res.send({"message":"hello from getConsortium"})
}
// function to create consortium
async function createConsortium (req,res) {
    logger.info("inside createConsortium()")
    logger.info(JSON.stringify(req.body))
    res.send(req.body)
}

module.exports = {
    getConsortiumByName,
    createConsortium
 }