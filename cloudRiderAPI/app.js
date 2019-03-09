const express = require('express')
const bodyParser = require('body-parser');
const app = express()
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
var logger = require('tracer').console();
var org =require('./services/organization')

const port = 3000
logger.info("Hello world")
app.get('/', (req, res) => res.send({"message":"Hellooooooothere!!!"}))
//organization

app.get('/organization',org.getOrg)
app.post('/organization', org.createOrg)
app.delete('/organization',org.delOrg)
app.listen(port, () => console.log(`Example app listening on port ${port}!`))
