import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import homeConfigVar from '../../config/home-config.json';

@Component({
  selector: 'app-home',
  imports: [DatePipe,RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  readonly today = new Date();
  title : string = homeConfigVar.conf.label.title;
  subTitle : string = homeConfigVar.conf.label.subTitle;
  description : string = homeConfigVar.conf.label.description;

}
