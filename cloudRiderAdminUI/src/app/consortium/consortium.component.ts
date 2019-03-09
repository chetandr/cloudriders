import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'consortium',
  templateUrl: './consortium.component.html',
  styleUrls: ['./consortium.component.css']
})
export class ConsortiumComponent implements OnInit {

  consordiumData = {};
  constructor(private http: HttpClient) { 
    this.http = http;
  }

  ngOnInit() {
    this.http.get("http://10.44.14.143:3000/hyperverse/consortium").subscribe((data: any) => {
          console.log(data.data); 
          this.consordiumData=data.data;
                            
        });
  }

}
