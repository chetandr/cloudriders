var logger = require('tracer').console();
var utils = require('../utils/utils.js')

// network edges. from to.
async function getNetworkGraph(req, res) {
    logger.info("inside getNetworkGraph()")
    let finalResult = [];
    let org = utils.getMockDetails();
    let cons = utils.getNetworkData();
    let count = 0;
    for (let ele of cons["data"]) {
        for (let org of ele["orgs"]) {
            let obj = {};
            obj["from"] = ele.consortiumname;
            logger.info(org.orgname);
            obj["To"] = org.orgname;
            finalResult.push(obj)
        }
    }
    for (let ele of org) {
        for (let peer of ele["peers"]) {
            let obj = {};
            obj["from"] = ele.orgname;
            obj["To"] = peer.name;
            finalResult.push(obj)
        }
    }
    res.send(finalResult)
    res.end();
}

//get all nodes {name:<name>, type:<type>}
async function nodes(req, res) {
    logger.info("inside nodes()")
    let finalResult = utils.getNodes();
    res.send(finalResult)
    res.end();
}

module.exports = {
    getNetworkGraph,
    nodes
}
