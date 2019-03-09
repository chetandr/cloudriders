var logger = require('tracer').console();
var utils = require('../utils/utils.js')
let fs =  require("fs");
let shellExec = require('../utils/shellExecutor')
//function to get consortium
async function getConsortiumByName (req, res) {
    logger.info("inside getConsortium()")
    let consortiumname = req.params.name;
    let data = utils.getMockDetails()
    let result =
    {
        "consortiumname" : consortiumname,
        "orgs":data
    }
    res.send(result)
    res.end()
}
//list of consortiums
async function getConsortiums (req, res) {
    logger.info("inside getConsortium()")
    //console.log(fs.readdirSync('../'))
    //let consortiums = fs.readdirSync('./../Scripts/Output/crypto-config/ordererOrganizations/')
    //console.log('###',consortiums);
    let result = utils.getNetworkData();    
    res.send(result)
    res.end()
}
// function to create consortium
async function createConsortium (req,res) {
    logger.info("inside createConsortium()")
    logger.info(JSON.stringify(req.body))
    res.send(req.body)
}

module.exports = {
    getConsortiumByName,
    getConsortiums,
    createConsortium
 }