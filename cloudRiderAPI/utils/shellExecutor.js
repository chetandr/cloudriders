
const execFile = require('child_process').execFile;
const fs = require("fs")
const shellMap = {
    "DISCOVER_LIST":{
        "cmd": "./cli/discover.sh",
        "args": ["peer0.org1.example.com", "org1.example.com", "User1", "Org1MSP" ,"mychannel"]
    },
    "KEY_STORE_MAP":{
        "cmd": "./cli/keystoreList.sh",
        "args":[""]
    }
}
function executeShell(command){
  return new Promise(function(resolve, reject){
    return execFile(shellMap[command]['cmd'], shellMap[command]['args'],function(err, out, code) {
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
// executeShell("KEY_STORE_MAP").then((data)=>{
//     console.log(data)
//  })
module.exports={
  executeShell
}

