import {ChangeDetectorRef, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {
  searchResults: any;

  constructor(private changeDetection: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.searchResults = [];
  }

  updateSearchResults(searchResults: any): void {
    console.log(searchResults);
    this.searchResults = searchResults;
    this.changeDetection.detectChanges();
  }


}
