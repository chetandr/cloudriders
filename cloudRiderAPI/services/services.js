var logger = require('tracer').console();
var utils = require('../utils/utils.js')

async function getNetworkGraph(req, res) {
    let finalResult = [];
    let data = utils.getNodes();
    //data.map(value => value.key);
    let result = {}
    finalResult.push(result)
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