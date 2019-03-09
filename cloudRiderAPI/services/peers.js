
var logger = require('tracer').console();

async function getPeers(request, response){
        return response.json({"message":"returning getPeers"});
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

module.exports={
    getPeers,
    createPeers,
    updatePeers,
    deletePeers,
    listPeers
}