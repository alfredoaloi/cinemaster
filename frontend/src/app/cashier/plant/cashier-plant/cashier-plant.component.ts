import {Component, Inject, OnInit} from '@angular/core';
import {CashierComponent} from '../../cashier.component';
import {CashierService} from '../../services/cashier.service';

@Component({
  selector: 'app-cashier-plant',
  templateUrl: './cashier-plant.component.html',
  styleUrls: ['./cashier-plant.component.css']
})
export class CashierPlantComponent implements OnInit {

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

  constructor(@Inject(CashierComponent) private cashier: CashierComponent, private service: CashierService) {}

  public loadData(event): void {
    this.movieTitle = event.show.name;
    this.loaded = false;
    this.date = event.date;
    this.hour = event.startTime;
    this.plants = [];
    const obj = {
      name: event.room.name,
      rowLetters: new Array(event.room.nRows),
      columnNumber: Array.from({length: event.room.nColumns}, (_, i) => i + 1),
      rows: event.room.nRows,
      columns: event.room.nColumns,
      reserved: [],
      selected: [],
      standard: [],
      premium: [],
      vip: [],
      ids: new Map(),
      price: {
        standardPrice: event.price.standardPrice,
        premiumPrice: event.price.premiumPrice,
        vipPrice: event.price.vipPrice,
      },
      eventId: event.id
    };

    for (let i = 0; i < obj.rows; i++) {
      obj.rowLetters[i] = this.rowName(i);
    }
    for (const seat of event.room.seats) {
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
        this.cashier.wantsToBuy = true;
        return;
      }
    }
    this.cashier.wantsToBuy = false;
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
