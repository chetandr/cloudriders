var logger = require('tracer').console();
var utils = require('../utils/utils.js')
let fs =  require("fs");
let {executeShell} = require('../utils/shellExecutor')
let _ = require('lodash');
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
    let nodes = await utils.getNodes();
    console.log("NODES");
    console.log(nodes);
    result = [];
    let count = 1;
    _.forEach(nodes.map, (map, con) => {
        result.push({id: count++, name: con})
    })
    res.send(result)
    res.end()
}
// function to create consortium
// ./generateConfigurations.sh example.com Orderer orderer Org 1 byfn 3 10000 7050 7051 mychannel

// ./addOrgnization.sh example.com Org3 mychannel Orderer orderer 5 byfn 11000
async function createConsortium (req,res) {
    const { domain,
        ordererName,
        ordererHostName,
        orgname,
        numberOfOrgs,
        networkname,
        numberOfPeers,
        channelName,
        orderport,
        peerportstart,
        peerportSequence} = req.body;
    await executeShell('CREATE_NETWORK', [domain, ordererName, ordererHostName, orgname, numberOfOrgs, networkname, numberOfPeers, orderport, peerportstart, peerportSequence, channelName]);
    //logger.info("inside createConsortium()")
    logger.info(domain,
        ordererName,
        ordererHostName,
        orgname,
        numberOfOrgs,
        networkname,
        numberOfPeers,
        channelName,
        orderport,
        peerportstart,
        peerportSequence)
    res.send(req.body)
}

module.exports = {
    getConsortiumByName,
    getConsortiums,
    createConsortium
 }