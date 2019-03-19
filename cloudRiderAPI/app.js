const express = require('express')
const bodyParser = require('body-parser');
const channelService = require('./services/channelService.js');
const app = express()
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
  });
var logger = require('tracer').console();
var org =require('./services/organization')
var peers =require('./services/peers')
var consortium = require('./services/consortium')
var services = require('./services/services')
var dashboard = require('./services/dashboardServices')
const port = 3030
logger.info("Hello world")
app.get('/',dashboard.home)



//channels apis
app.post('/hyperverse/channels',channelService.createChannel);

app.get('/hyperverse/channels/:name',channelService.readChannel);

app.get('/hyperverse/allChannels/:org',channelService.readChannels);
app.get('/hyperverse/myChannels',channelService.readChannels);

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
app.delete('/hyperverse/organization',org.delOrgByName);
app.get('/hyperverse/organization',org.getOrgs);

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
app.get('/hyperverse/getNodes',services.nodes);
app.get('/hyperverse/getNetworkGraph', services.getNetworkGraph);
app.listen(port, () => console.log(`Example app listening on port ${port}!`))


