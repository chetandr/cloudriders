const express = require('express')
const bodyParser = require('body-parser');
const channelService = require('./services/channelService.js');
const app = express()
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
var logger = require('tracer').console();
var org =require('./services/organization')
var peers =require('./services/peers')
var consortium = require('./services/consortium')

const port = 3000
logger.info("Hello world")
app.get('/', (req, res) => res.send({"message":"This is Cloud Riders Project!"}))

//channels apis
app.post('/hyperverse/channels',channelService.createChannel);

app.get('/hyperverse/channels/:name',channelService.readChannel);

app.put('/hyperverse/channels/:name',channelService.updateChannel);

app.delete('/hyperverse/channels/:name',channelService.deleteChannel);

app.get('/hyperverse/listChannels',channelService.listChannels);

app.post('/hyperverse/subscribeChannel',channelService.subscribeChannel);


//chaincode apis
app.get('/hyperverse/listChaincode',channelService.listChaincode);

app.post('/hyperverse/deployChaincode',channelService.deployChaincode);
app.post('/hyperverse/invokeChaincode',channelService.invokeChaincode);

app.get('/hyperverse/invokeChaincode/:chaincodeId',channelService.invokeChaincodeById);

//API for organization
app.post('/hyperverse/organization', org.createOrg);
app.delete('/hyperverse/organization/:name',org.delOrgByName);
app.get('/hyperverse/organization',org.getOrgs);
app.get('/hyperverse/organization/:name',org.getOrgByName);

//API for consortium
app.get('/hyperverse/consortium/:name',  consortium.getConsortiumByName)
app.get('/hyperverse/consortium',  consortium.getConsortiums)
app.post('/hyperverse/consortium', consortium.createConsortium)

// peers

app.get('/hyperverse/peers/',peers.listPeers);
app.get('/hyperverse/peers/:name',peers.getPeers);
app.post('/hyperverse/peers', peers.createPeers);
app.post('/hyperverse/deploypeers', peers.deployPeers);
app.delete('/hyperverse/peers/:name',peers.deletePeers);
app.put('/hyperverse/peers/:name',peers.updatePeers)

//new apis
app.get('/hyperverse/listTransaction',channelService.listTransaction);

app.listen(port, () => console.log(`Example app listening on port ${port}!`))


