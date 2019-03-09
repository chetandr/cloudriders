
var logger = require('tracer').console();
var mockData = require('../../mocks/peerDataforAGivenPeer.json')
var utils = require('../utils/utils.js')
let _ = require('lodash')
async function getPeers(request, response){
    try{
        let data = utils.mockDetails(mockData);
        let result;
        let orgName = request.params.name;
        if(orgName){
            result = _.find(data, ['orgname',orgName]).peers;
            logger.info(result);
        }
        response.send(result)
    } catch(err){
        logger.info("Got error",err)
    }
}

async function createPeers(request, response){
    return response.json(request.body);
    
}

async function updatePeers(request, response){
    return response.json({"message": "Updated Peers Successfully"})
}

async function deletePeers(request, response){
    return response.json({"message":"Deleted Peers Successfully!"});
}

async function listPeers(request, response){
    return response.json({"message":"Listed Peers Successfully!"});
}

//deployPeers
async function deployPeers(request, response) {

}

module.exports={
    getPeers,
    createPeers,
    updatePeers,
    deletePeers,
    listPeers,
    deployPeers
}