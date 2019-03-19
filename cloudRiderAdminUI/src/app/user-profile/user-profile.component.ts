import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  orgData = {};
  obj : any = {};
  headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });
  constructor(private http: HttpClient) { 
    this.http = http;
  }

  ngOnInit() {
    this.http.get("http://localhost:3030/hyperverse/organization").subscribe((data: any) => {
          console.log(data); 
          this.orgData=data;
                            
        });
  }
  
   onClick(){
    
    console.log(this.obj);
    let data = this.http.post(
      "http://localhost:3030/hyperverse/organization",
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
