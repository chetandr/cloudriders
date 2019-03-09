const express = require('express')
const bodyParser = require('body-parser');
const app = express()
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
var logger = require('tracer').console();
var org = require('./services/organization')
var consortium = require('./services/consortium')

const port = 3000
logger.info("Hello world")
app.get('/', (req, res) => res.send({"message":"Hellooooooothere!!!"}))

//API for organization
app.get('/hyperverse/organization',org.getOrg)
app.get('/hyperverse/organization:name',org.getOrgByName)
app.post('/hyperverse/organization', org.createOrg)
app.delete('/hyperverse/organization:name',org.delOrgByName)

//API for consortium
app.get('/hyperverse/consortium:name',  consortium.getConsortiumByName)
app.post('/hyperverse/consortium', consortium.createConsortium)

app.listen(port, () => console.log(`Example app listening on port ${port}!`))
