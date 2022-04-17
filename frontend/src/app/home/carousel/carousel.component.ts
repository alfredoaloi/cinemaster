import {AfterViewInit, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SearchService} from '../../services/search.service';


@Component({selector: 'app-carousel',
  encapsulation: ViewEncapsulation.None
  , styleUrls: ['./carousel.component.css'],
  templateUrl: './carousel.component.html'})
export class CarouselComponent implements OnInit, AfterViewInit {
  // images = [944, 1011, 984].map((n) => `https://picsum.photos/id/${n}/900/500`);

  searchResults: any;
  public loaded = false;



  constructor(private searchService: SearchService) {
    this.searchService = searchService;
  }

  ngOnInit(): void {



  }

  ngAfterViewInit(): void {

    this.searchService.getHighlightedShows().subscribe(response => {
      this.searchResults = response;
      console.log(response);
      this.loaded = true;
    }, error => {});

  }


}
