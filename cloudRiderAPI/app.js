const express = require('express')
const bodyParser = require('body-parser');
const app = express()
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
var logger = require('tracer').console();
var org =require('./services/organization')
var peers =require('./services/peers')


const port = 3000
logger.info("Hello world")
app.get('/', (req, res) => res.send({"message":"This is Cloud Riders Project!"}))

//organization

app.get('/organization',org.getOrg)
app.post('/organization', org.createOrg)
app.delete('/organization',org.delOrg)

// peers

app.get('/hyperverse/peers/',peers.listPeers);
app.get('/hyperverse/peers/:name',peers.getPeers);
app.post('/hyperverse/peers', peers.createPeers);
app.delete('/hyperverse/peers/:name',peers.deletePeers);
app.update('/hyperverse/peers/:name',peers.updatePeers);


app.listen(port, () => console.log(`Example app listening on port ${port}!`))
