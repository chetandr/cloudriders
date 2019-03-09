const express = require('express')
const bodyParser = require('body-parser');
const channelService = require('./services/channelService.js');
const app = express()
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const port = 3000

app.get('/', (req, res) => res.send({"message":"Hellooooooothere!!!"}))

app.post('/channels',channelService.createChannel);

app.get('/channels/:name',channelService.readChannel);

app.put('/channels/:name',channelService.updateChannel);

app.delete('/channels/:name',channelService.deleteChannel);

app.get('/listChannels',channelService.listChannels);

app.listen(port, () => console.log(`Example app listening on port ${port}!`))

/*
list channels
deploy invoke chaincode
list ledger and list chaincode
*/