var logger = require('tracer').console();
var utils = require('../utils/utils.js')

async function getNetworkGraph(req, res) {
    let finalResult = [];
    let data = utils.getMockDetails();
    let result = {}
    finalResult.push(result)
    res.send(finalResult)
    res.end();

}

//get all nodes {name:<name>, type:<type>}
async function nodes(req, res) {
    let finalResult = [];
    let org = utils.getMockDetails();
    let cons = utils.getNetworkData();
    let count = 0;
    for (let ele of cons["data"]) {
        logger.info(JSON.stringify(ele))
        let obj = {};
        count++;
        obj["id"] = count,
            obj["name"] = ele.consortiumname;
        obj["type"] = "consortium"
        finalResult.push(obj)
    }

    for (let ele of org) {
        logger.info(JSON.stringify(ele))
        let obj = {};
        count++;
        obj["id"] = count,
        obj["name"] = ele.orgname;
        obj["type"] = "organization"
        finalResult.push(obj)

        for (let peer of ele["peers"]) {
            logger.info(JSON.stringify(peer))
            let obj = {};
            count++;
            obj["id"] = count,
            obj["name"] = peer.name;
            obj["type"] = "peer"
            finalResult.push(obj)
        }
    }

    res.send(finalResult)
    res.end();
}

module.exports = {
    getNetworkGraph,
    nodes
}