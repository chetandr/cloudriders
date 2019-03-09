import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-table-list',
  templateUrl: './table-list.component.html',
  styleUrls: ['./table-list.component.css']
})
export class TableListComponent implements OnInit {

  organizations = [ "Organization 1", "Organization 2", "Organization 3"]

  constructor() { }

  ngOnInit() {
  }

}
