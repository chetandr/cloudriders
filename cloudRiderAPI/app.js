const express = require('express')
const bodyParser = require('body-parser');
const app = express()
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const port = 3000

app.get('/', (req, res) => res.send({"message":"Hellooooooothere!!!"}))

app.listen(port, () => console.log(`Example app listening on port ${port}!`))
