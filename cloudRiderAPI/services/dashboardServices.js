var logger = require('tracer').console();
var utils = require('../utils/utils.js')
const _ = require("lodash")
async function home(req, res) {
    let result = await utils.getNodes();
    result.counts = {
        "consortiumCount": 0,
        "orgCount":0,
        "peerCount": 0,
        "channelCount": 0,
        "chainCode": 0
    };
    _.forEach(result.map, map => {
        console.log(map);
        result.counts.consortiumCount++;
        _.each(map, org => {
            result.counts.orgCount++;
            _.each(org, peer => {
                result.counts.peerCount++;
            })
        })
    })
    let finalResult = [];
    // let cons = utils.getNetworkData();    
    // let result = {
    //     "consortiumCount": 2,
    //     "orgCount":4,
    //     "peerCount": 8,
    //     "channelCount": 2,
    //     "chainCode": 8
    // }
    

    res.send(result.counts);
    res.end();

}

module.exports = {
    home
}