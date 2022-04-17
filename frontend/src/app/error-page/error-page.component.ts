import { Component, OnInit, HostListener } from '@angular/core';
import {style} from '@angular/animations';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent implements OnInit {

  private screenHeight;
  private screenWidth;
  constructor() { }

  ngOnInit(): void {

  }

  @HostListener('window:resize', ['$event'])
  public onResize(event?): void {
    const resolution = 1440;
    const font = 10;
    const width = window.innerWidth;
    const newFont = font * (width / resolution);
    const element = document.getElementsByClassName('errorPageRap') as HTMLCollectionOf<HTMLElement>;
    if (element.length !== 0) {
      element[0].setAttribute('style', 'font-size: ' + newFont);
    }
  }

}
