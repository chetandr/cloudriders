var logger = require('tracer').console();
var utils = require('../utils/utils.js')
const _ = require("lodash")
async function home(req, res) {
    let finalResult = [];
    let cons = utils.getNetworkData();    
    let result = {
        "consortiumCount": 2,
        "orgCount":4,
        "peerCount": 8,
        "channelCount": 2,
        "chainCode": 8
    }
    

    res.send(result);
    res.end();

}

module.exports = {
    home
}