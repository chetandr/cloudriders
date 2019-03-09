const express = require('express')
const bodyParser = require('body-parser');
const channelService = require('./services/channelService.js');
const app = express()
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
var logger = require('tracer').console();
var org = require('./services/organization')
var consortium = require('./services/consortium')

const port = 3000
logger.info("Hello world")
app.get('/', (req, res) => res.send({"message":"Hellooooooothere!!!"}))

app.post('/channels',channelService.createChannel);

app.get('/channels/:name',channelService.readChannel);

app.put('/channels/:name',channelService.updateChannel);

app.delete('/channels/:name',channelService.deleteChannel);

app.get('/listChannels',channelService.listChannels);

app.get('/listLedger',channelService.listLedger);

app.get('/listChaincode',channelService.listChaincode);

app.get('/listChannels',channelService.listChannels);

app.post('/deployChaincode',channelService.deployChaincode);

app.get('/invokeChaincode/:chaincodeId',channelService.invokeChaincode);

//API for organization
app.get('/hyperverse/organization',org.getOrg)
app.get('/hyperverse/organization:name',org.getOrgByName)
app.post('/hyperverse/organization', org.createOrg)
app.delete('/hyperverse/organization:name',org.delOrgByName)

//API for consortium
app.get('/hyperverse/consortium:name',  consortium.getConsortiumByName)
app.post('/hyperverse/consortium', consortium.createConsortium)

app.listen(port, () => console.log(`Example app listening on port ${port}!`))