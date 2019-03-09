import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-table-list',
  templateUrl: './table-list.component.html',
  styleUrls: ['./table-list.component.css']
})
export class TableListComponent implements OnInit {
  organisations = [ "Org 1", "Org 2", "Org 3"]
  constructor() { }

  ngOnInit() {
  }

}
