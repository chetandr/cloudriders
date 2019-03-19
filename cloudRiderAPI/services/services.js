var logger = require('tracer').console();
var utils = require('../utils/utils.js')

// network edges. from to.
async function getNetworkGraph(req, res) {
    
    res.send(utils.getNodes())
    res.end();
}

//get all nodes {name:<name>, type:<type>}
async function nodes(req, res) {
    logger.info("inside nodes()")
    let finalResult = await utils.getNodes();
    res.send(finalResult)
    res.end();
}

module.exports = {
    getNetworkGraph,
    nodes
}
