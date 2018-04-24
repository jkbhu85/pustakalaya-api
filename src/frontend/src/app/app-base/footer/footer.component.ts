import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styles: [
    `
    .full-width{
      width: 100%;
    }
    `
  ]
})
export class FooterComponent implements OnInit {
  loggedIn = false;

  constructor() { }

  ngOnInit() {
  }

}
