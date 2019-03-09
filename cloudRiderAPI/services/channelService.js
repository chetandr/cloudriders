var logger = require('tracer').console();
var utils = require('../utils/utils.js')

async function createChannel(req, res) {
    console.log("Got the request");
    if (req.body) {
        res.send("Request served for create" + req.body);
    }
}

async function deployChaincode(req, res) {
    console.log("Got the request");
    if (req.body) {
        res.send(req.body);
    }
}

async function readChannel(req, res) {
    if (req.params.name) {
        let nameOfChannel = req.params.name
        var data = {
            "channelName": nameOfChannel,
            "org1": {
                "peers": [
                    {
                        "name": "peer1",
                        "ledger": "",
                        "chaincodes": null
                    }
                ]
            }
        }
        res.send(data);
    }
}

async function readChannels(req, res) {
    logger.info("Got the request for read all channels")
        let nameOfChannel = "sample-channel"
        var data = [{
            "channelName": nameOfChannel+'1',
            "org1": {
                "peers": [
                    {
                        "name": "peer1",
                        "ledger": "",
                        "chaincodes": null
                    }
                ]
            }
        },
        {
            "channelName": nameOfChannel+'2',
            "org1": {
                "peers": [
                    {
                        "name": "peer1",
                        "ledger": "",
                        "chaincodes": null
                    }
                ]
            }
        },
        {
            "channelName": nameOfChannel+'3',
            "org1": {
                "peers": [
                    {
                        "name": "peer1",
                        "ledger": "",
                        "chaincodes": null
                    }
                ]
            }
        },
        ]
        res.send(data);    
}

async function invokeChaincode(req, res) {
    console.log("Got the request");
    if (req.params.chaincodeId) {
        let chaincodeId = req.params.chaincodeId
        res.send("Request served for invokde chaincode ID " + chaincodeId);
    }
}

async function listChannels(req, res) {
    console.log("Got the request");
    res.send("Request served for list channels");

}

async function listLedger(req, res) {
    console.log("Got the request");
    res.send("Request served for list ledger");

}

async function listChaincode(req, res) {
    console.log("Got the request");
    let peerName = req.query.peername;
    let orgName = req.query.orgname;
    let data = utils.listOfChaincodes(peerName, orgName)
    res.send(data)
    res.end()
}

async function updateChannel(req, res) {
    console.log("Got the request");
    if (req.params.name) {
        let nameOfChannel = req.params.name;
        console.log("Name of channel for update" + nameOfChannel);
        if (req.body) {
            let body = req.body;
            console.log("Got the data to update", body)
            res.send("Check data" + body)
        }
    }
}

async function subscribeChannel(req, res) {
    console.log("Got the request");
    res.send("Request served for subscribe channel");
}

async function deleteChannel(req, res) {
    console.log("Got the request");
    if (req.params.name) {
        let name = req.params.name;
        res.send("Request served for delete" + name);
    }
}

async function listTransaction(req, res) {
    console.log("Got the request");    
    res.send("Request served for list transaction");    
}

module.exports = {
    createChannel: createChannel,
    readChannel: readChannel,
    updateChannel: updateChannel,
    deleteChannel: deleteChannel,
    listChannels: listChannels,
    listLedger: listLedger,
    listChaincode: listChaincode,
    deployChaincode:deployChaincode,
    invokeChaincode:invokeChaincode,
    subscribeChannel:subscribeChannel,
    listTransaction:listTransaction,
    readChannels:readChannels
}