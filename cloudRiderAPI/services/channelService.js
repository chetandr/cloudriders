function createChannel(req, res) {
    console.log("Got the request");
    if (req.body) {
        res.send("Request served for create" + req.body);
    }
}

function readChannel(req, res) {
    console.log("Got the request");
    if (req.params.name) {
        let nameOfChannel = req.params.name
        res.send("Request served for read" + nameOfChannel);
    }
}

function listChannels(req, res) {
    console.log("Got the request");
    res.send("Request served for list channels");

}

function updateChannel(req, res) {
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

function deleteChannel(req, res) {
    console.log("Got the request");
    if (req.params.name) {
        let name = req.params.name;
        res.send("Request served for delete" + name);
    }
}
module.exports = {
    createChannel: createChannel,
    readChannel: readChannel,
    updateChannel: updateChannel,
    deleteChannel: deleteChannel,
    listChannels: listChannels
}