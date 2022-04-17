import {Component, Inject, OnInit} from '@angular/core';
import {ShowDetailComponent} from '../show-detail.component';
import {ShowDetailService} from '../services/show-detail.service';
import {EventRoom} from '../../model/EventRoom';
import {newArray} from '@angular/compiler/src/util';
import {CashierComponent} from '../../cashier/cashier.component';

@Component({
  selector: 'app-seat-reservation',
  templateUrl: './seat-reservation.component.html',
  styleUrls: ['./seat-reservation.component.css']
})
export class SeatReservationComponent implements OnInit {

  public movieTitle: string;
  public date: string;
  public hour: string;
  public plants: any[];

  public loaded = false;
  public firstLoad = true;

  private rowName(n): string {
    const ordA = 'A'.charCodeAt(0);
    const ordZ = 'Z'.charCodeAt(0);
    const len = ordZ - ordA + 1;

    let s = '';
    while (n >= 0) {
      s = String.fromCharCode(n % len + ordA) + s;
      n = Math.floor(n / len) - 1;
    }
    return s;
  }

  constructor(@Inject(ShowDetailComponent) private showDetail: ShowDetailComponent, private service: ShowDetailService) {
    this.movieTitle = this.showDetail.show.name;
    this.plants = [];
  }

  public loadData(date, hour, rooms): void {
    this.loaded = false;
    this.date = date;
    this.hour = hour;
    this.plants = [];
    for (const room of rooms) {
      const obj = {
        name: room.room.name,
        rowLetters: new Array(room.room.nRows),
        columnNumber: Array.from({length: room.room.nColumns}, (_, i) => i + 1),
        rows: room.room.nRows,
        columns: room.room.nColumns,
        reserved: [],
        selected: [],
        standard: [],
        premium: [],
        vip: [],
        ids: new Map(),
        price: {
          standardPrice: room.price.standardPrice,
          premiumPrice: room.price.premiumPrice,
          vipPrice: room.price.vipPrice,
        },
        eventId: room.id
      };

      for (let i = 0; i < obj.rows; i++) {
        obj.rowLetters[i] = this.rowName(i);
      }
      for (const seat of room.room.seats) {
        if (seat.seatType === 'VIP') {
          obj.vip.push(seat.row + seat.column);
        }
        if (seat.seatType === 'PREMIUM') {
          obj.premium.push(seat.row + seat.column);
        }
        if (seat.seatType === 'STANDARD') {
          obj.standard.push(seat.row + seat.column);
        }
        obj.ids.set(seat.row + seat.column, seat.id);
      }
      this.service.getBookedSeats(obj.eventId).subscribe(response => {
        for (const seats of response) {
          obj.reserved.push(seats.row.concat(seats.column));
        }
        this.loaded = true;
        this.firstLoad = false;
      }, error => {
      });
      this.plants.push(obj);
    }
  }

  ngOnInit(): void {
  }

  public getStatus( seatPos, index ): string {

    if (this.plants[index].reserved.indexOf(seatPos) !== -1) {
      return 'reserved';
    }
    else {
      if (this.plants[index].vip.indexOf(seatPos) !== -1) {
              if (this.plants[index].selected.indexOf(seatPos) !== -1) {
                return 'selected-vip';
              }
              return 'vip';
      }
      else if (this.plants[index].standard.indexOf(seatPos) !== -1) {
        if (this.plants[index].selected.indexOf(seatPos) !== -1) {
          return 'selected-standard';
        }
        return 'standard';
      }
      else if (this.plants[index].premium.indexOf(seatPos) !== -1) {
        if (this.plants[index].selected.indexOf(seatPos) !== -1) {
          return 'selected-premium';
        }
        return 'premium';
      }
      return 'white';
    }
  }

  public seatClicked( seatPos: string, index ): void {
    const index1 = this.plants[index].selected.indexOf(seatPos);

    if (index1 !== -1) {
      // seat already selected, remove
      this.plants[index].selected.splice(index1, 1);
    } else {
      // push to selected array only if it is not reserved
      if ( this.plants[index].reserved.indexOf(seatPos) === -1 && (this.plants[index].premium.indexOf(seatPos) !== -1 ||
        this.plants[index].vip.indexOf(seatPos) !== -1 || this.plants[index].standard.indexOf(seatPos) !== -1)) {
        this.plants[index].selected.push(seatPos);
      }
    }
    for (const e of this.plants) {
      if (e.selected.length > 0) {
        this.showDetail.wantsToBuy = true;
        this.showDetail.totalAmount = this.getAmount();
        return;
      }
    }
    this.showDetail.wantsToBuy = false;
  }

  public seatSelected(): string[] {
    const retVal = [];
    for (const plant of this.plants) {
      let s = plant.name + ': ';
      for ( const e of plant.selected ) {
         s += e + ' ';
      }
      retVal.push(s);
    }
    return retVal;
  }

  public getAmount(): number {
    let amount = 0;
    for (const element of this.plants){
      for (const e of element.selected) {
        if (element.vip.indexOf(e) !== -1){
          amount += element.price.vipPrice;
        }
        else if (element.standard.indexOf(e) !== -1){
          amount += element.price.standardPrice;
        }else if (element.premium.indexOf(e) !== -1){
          amount += element.price.premiumPrice;
        }
      }
    }
    return amount;
  }

  public getReservedSeatByEvent(): object[] {
    const retval = [];
    for (const plant of this.plants) {
      const obj = {
        event: { id: plant.eventId} ,
        seats: []
      };
      for ( const e of plant.selected ) {

        let price = 0;

        if (plant.vip.indexOf(e) !== -1){
          price = plant.price.vipPrice;
        }
        else if (plant.standard.indexOf(e) !== -1){
          price = plant.price.standardPrice;
        }else if (plant.premium.indexOf(e) !== -1){
          price = plant.price.premiumPrice;
        }

        obj.seats.push({id: plant.ids.get(e), price: price});
      }
      if ( obj.seats.length > 0) {
        retval.push(obj);
      }
    }
    return retval;
  }

}
