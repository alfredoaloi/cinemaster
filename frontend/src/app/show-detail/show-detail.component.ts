import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ShowDetailService} from './services/show-detail.service';
import {ActivatedRoute} from '@angular/router';
import {SeatReservationComponent} from './seat-reservation/seat-reservation.component';
import {Show} from '../model/Show';
import {HomeComponent} from '../home/home.component';
import {EventRoom} from '../model/EventRoom';
import {PaypalPaymentExecutorComponent} from '../paypal-payment-executor/paypal-payment-executor.component';

@Component({
  selector: 'app-show-detail',
  templateUrl: './show-detail.component.html',
  styleUrls: ['./show-detail.component.css'],
  providers: [HomeComponent]
})
export class ShowDetailComponent implements OnInit {

  @ViewChild('seatReservationComponent')
  public seatReservation: SeatReservationComponent;
  @ViewChild('paypalPaymentExecutorComponent')
  public paypalPaymentExecutorComponent: PaypalPaymentExecutorComponent;
  public show: Show;
  public totalAmount: number;
  public seatReservationCompleted: boolean;
  public showLoaded = false;
  public eventsLoaded = false;
  public events: Map<string, any>;
  public uniqueHours: Map<string, string[]>;
  public wantsToBuy = false;
  constructor(private service: ShowDetailService, private route: ActivatedRoute) {
    // tslint:disable-next-line:new-parens
    this.show = new class implements Show {
      actors: any[] | string;
      categories: any[] | string;
      comingSoon: boolean;
      description: string;
      directors: any[] | string;
      highlighted: boolean;
      id: number;
      language: string;
      length: number | string;
      name: string;
      photoUrl: string;
      productionLocation: string;
      releaseDate: string;
      urlHighlighted: string;
      urlTrailer: string;
      rating: string;
    };
    type myEvent = {
      hour: string,
      room: EventRoom,
      id: string,
      price: {}
    };
    this.events = new Map<string, myEvent[]>();
    this.uniqueHours = new Map<string, string[]>();
  }

  ngOnInit(): void {
    this.seatReservationCompleted = false;
    this.totalAmount = 0;
    this.loadDetail();
    this.loadEvents();
  }

  private setSeatReservationCompleted(status: boolean): void {
    this.seatReservationCompleted = status;
  }

  private priceToPayChanged(newPrice: number): void {
    this.totalAmount = newPrice;
  }

  private loadDetail(): void {
    this.service.getShowDetail(this.route.snapshot.params.id).subscribe(response => {
      this.show.name = response.name;
      this.show.description = response.description;
      this.show.length = '' + Math.floor(response.length / 60) + ' h ' + response.length % 60 + ' min';
      this.show.releaseDate = response.releaseDate;
      this.show.language = response.language;
      this.show.productionLocation = response.productionLocation;
      this.show.photoUrl = response.photoUrl;
      let gen = '';
      for (const a of response.categories) {
        gen += a.name + ' ';
      }
      this.show.categories = gen;
      let act = '';
      for (const a of response.actors) {
        act += a.name + ' ';
      }
      this.show.actors = act;
      let dir = '';
      for (const a of response.directors) {
        dir += a.name + ' ';
      }
      this.show.directors = dir;
      this.showLoaded = true;
    }, error => { this.error(); }
    );
  }

  private loadEvents(): void {
    this.service.getshowEvents(this.route.snapshot.params.id).subscribe(
      response => {
          for (const e of response) {
            const date = new Date(e.date).toLocaleDateString('it-IT', {weekday: 'long', year: 'numeric', month: 'long', day: '2-digit'});
            const hour = new Date();
            const [hours, minutes, seconds] = e.startTime.split(':');
            hour.setHours(hours);
            hour.setMinutes(minutes);
            hour.setSeconds(seconds);
            const h = hour.toLocaleString('it-IT', {hour: '2-digit', minute: '2-digit'});
            if (this.events.has(date)) {
              this.events.get(date).push({hour: h, room: e.room, id: e.id, price: e.price});
              if (this.uniqueHours.get(date).indexOf(h) === -1) {
                this.uniqueHours.get(date).push(h);
              }
            } else {
              this.events.set(date, [{hour: h, room: e.room, id: e.id, price: e.price}]);
              this.uniqueHours.set(date, [h]);
            }
          }
          this.eventsLoaded = true;
        },
      error => { this.error(); }
    );
  }

  public renderSeatPlan(date: string, hour: string): void{
    const events = this.events.get(date);
    const eventsRoom = [];
    for (const e of events) {
      if (e.hour === hour) {
        eventsRoom.push({id: e.id, room: e.room, price: e.price});
      }
    }
    this.seatReservation.loadData(date, hour, eventsRoom);
  }

  private error(): void {
    window.location.href = 'http://localhost:4200/error404';
  }

  public getKeys(map): any{
    return Array.from(map.keys());
  }


}
