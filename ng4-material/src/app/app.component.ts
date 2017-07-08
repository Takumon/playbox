import { Component } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  myData: Array<any>;

  constructor(private http: Http){
    this.http.get('https://jsonplaceholder.typicode.com/photos')
         .toPromise()
         .then(response => this.myData = response.json());
  }
}
