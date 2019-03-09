import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-typography',
  templateUrl: './typography.component.html',
  styleUrls: ['./typography.component.css']
})
export class TypographyComponent implements OnInit {

  organizations = [ "Organization 1", "Organization 2", "Organization 3"]

  constructor() { }

  ngOnInit() {
  }

}
