
const execFile = require('child_process').execFile;
const fs = require("fs")
const shellMap = {
    "DISCOVER_LIST":{
        "cmd": "./cli/discover.sh",
        "args": ["peer0.org1.example.com", "org1.example.com", "User1", "Org1MSP" ,"mychannel"]
    },
    "KEY_STORE_MAP":{
        "cmd": "./cli/keystoreList.sh",
        "args": ["cli.cloudrider.com"]
    }
}
function executeShell(command, args){
  console.log(shellMap[command]['cmd'], args)
  console.log(fs.readdirSync('./'))
  return new Promise(function(resolve, reject){
    return execFile(shellMap[command]['cmd'], args,function(err, out, code) {
      if (err instanceof Error){
        process.stderr.write(err);
        return reject(err);
      }
      process.stdout.write(out);
      return resolve(out);
    });
  })
}
// executeShell("../cli/discover.sh", ["peer0.org1.example.com", "org1.example.com", "User1", "Org1MSP" ,"mychannel"]).then((data)=>{
//    console.log(data)
// })
// executeShell("KEY_STORE_MAP", ["cli.cloudrider.com"]).then((data)=>{
//      console.log(data)
//   })
module.exports={
  executeShell
}

