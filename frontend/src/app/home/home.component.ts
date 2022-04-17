import {Component, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {GridComponent} from '@syncfusion/ej2-angular-grids';
import {SearchResultsComponent} from './search-results/search-results.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  userIsSearching: boolean;

  @ViewChild('searchResultsComponent',{ static: false })
  public searchResultsComponent;



  constructor() {
    this.userIsSearching = false;
  }

  ngOnInit(): void {
  }

  fillSearchResultsComponent(searchResults: object): void {
    this.searchResultsComponent.updateSearchResults(searchResults);

  }
}



