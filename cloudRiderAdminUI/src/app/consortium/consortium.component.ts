import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'consortium',
  templateUrl: './consortium.component.html',
  styleUrls: ['./consortium.component.css']
})
export class ConsortiumComponent implements OnInit {

  consordiumData = {};
  obj : any = {};
  headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });
  constructor(private http: HttpClient) { 
    this.http = http;
  }

  ngOnInit() {
    this.http.get("http://10.44.14.136:3000/hyperverse/consortium").subscribe((data: any) => {
          console.log(data.data); 
          this.consordiumData=data.data;
                            
        });
  }
  
   onClick(){
    
    console.log(this.obj);
    let data = this.http.post(
      "http://10.44.14.136:3000/hyperverse/consortium",
     JSON.stringify(this.obj),
      {headers: this.headers}
    ).subscribe((response:any)=>{
      console.log(response);
      
    },(error:any)=>{
      console.log(error);
      
    })
    return ''

  }

}



