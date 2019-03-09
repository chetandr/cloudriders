import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'chaincode-form',
  templateUrl: './chaincode.component.html',
  styleUrls: ['./chaincode.component.css']
})
export class ChaincodeComponent implements OnInit {

  consortiums = [ "Consortium 1", "Consortium 2", "Consortium 3"]

  constructor() { }

  ngOnInit() {

  }

}