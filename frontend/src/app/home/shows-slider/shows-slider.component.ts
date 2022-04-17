import { Component, OnInit } from '@angular/core';
import {Show} from '../../model/Show';
import {AddEvent} from '../../model/AddEvent';
import {ListService} from '../../admin/dashboard/work-area/default/services/list.service';
import {SearchService} from '../../services/search.service';

@Component({
  selector: 'app-shows-slider',
  templateUrl: './shows-slider.component.html',
  styleUrls: ['./shows-slider.component.css']
})
export class ShowsSliderComponent implements OnInit {

  loaded: boolean;

  availableSliderShows: Show[] = [];

  comingSoonSliderShows: Show[] = [];

  actionSliderConfig = {
    slidesToShow: 5,
    slidesToScroll: 5,
    autoplay: false,
    autoplaySpeed: 3000,
    arrows: true,
    infinite: true,
    centerMode: true,
    /*responsive: [
      {breakpoint: 480,
        settings: {slidesToShow: 1, slidesToScroll: 1}},
      {breakpoint: 979, settings: {slidesToShow: 3, slidesToScroll: 3}},
      {breakpoint: 1199, settings: {slidesToShow: 5, slidesToScroll: 5}},
      {breakpoint: 1999, settings: {slidesToShow: 7, slidesToScroll: 7}},
      {breakpoint: 4999, settings: {slidesToShow: 7, slidesToScroll: 7}}],*/
    dots: false
  };

  ngOnInit(): void {
    this.loaded = false;
    this.searchService.getShowsWithEvents().subscribe(
      (data) => {
        this.availableSliderShows = data;
        console.log(data);
      },
      error => {},
      () => {
        this.loaded = true;
      }
    );

    this.searchService.getComgingSoonShows().subscribe(
      (data) => {
        this.comingSoonSliderShows = data;
      },
      error => {},
      () => {
        this.loaded = true;
      }
    );
  }

  constructor(private searchService: SearchService) {
  }

}
