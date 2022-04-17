import {Component, Inject, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {ItemComponent} from '../../item/item.component';
import {WorkAreaComponent} from '../../work-area.component';
import {RoomService} from '../services/room.service';
import {EventRoom} from '../../../../../model/EventRoom';

@Component({
  selector: 'app-room-creator',
  templateUrl: './room-creator.component.html',
  styleUrls: ['./room-creator.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RoomCreatorComponent implements OnInit, ItemComponent {
  @Input() type: string;
  @Input() info: EventRoom;
  public name: string;
  public rows = 1;
  public columns = 1;
  public rowLetters: string[] = ['A'];
  public columnNumber: number[]  = [1];
  public standard: string[] = [];
  public premium: string[] = [];
  public vip: string[] = [];
  public actual = 'standard';
  public loaded = true;
  public roomId: number;

  @ViewChild('invalidResponseToastAlert') invalidResponseAlert;
  @ViewChild('correctResponseToastAlert') correctResponseAlert;
  public position = { X: 'Left'};

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


  constructor(@Inject(WorkAreaComponent) private parent: WorkAreaComponent, private service: RoomService) { }

  ngOnInit(): void {
    if (this.info != null) {
      this.roomId = this.info.id;
      this.rows = this.info.nRows;
      this.columns = this.info.nColumns;
      this.name = this.info.name;
      for (const seat of this.info.seats) {
        if (seat.seatType === 'STANDARD') {
          this.standard.push(seat.row + seat.column);
        }
        else if (seat.seatType === 'PREMIUM') {
          this.premium.push(seat.row + seat.column);
        }
        else if (seat.seatType === 'VIP') {
          this.vip.push(seat.row + seat.column);
        }
      }
      this.renderPlant();
    }
  }

  public renderPlant(): void{
    this.rowLetters = new Array(this.rows);
    this.columnNumber = Array.from({length: this.columns}, (_, i) => i + 1);
    for (let i = 0; i < this.rows; i++) {
      this.rowLetters[i] = this.rowName(i);
    }
  }

  public getStatus( seatPos: string ): string {
    if (this.vip.indexOf(seatPos) !== -1) {
      return 'vip';
    } else if (this.standard.indexOf(seatPos) !== -1) {
      return 'standard';
    }
    else if (this.premium.indexOf(seatPos) !== -1) {
      return 'premium';
    }
  }

  public clearSelected(): void {
    this.vip = [];
    this.premium = [];
    this.standard = [];
  }

  public seatClicked( seatPos: string ): void {
    let index;
    switch ( this.actual ) {
      case 'standard':
        index = this.standard.indexOf(seatPos);
        if (index !== -1) {
          this.standard.splice(index, 1);
        }
        else {
          if ( this.vip.indexOf(seatPos) === -1 && this.premium.indexOf(seatPos) === -1) {
            this.standard.push(seatPos);
          }
        }
        break;
      case 'premium':
        index = this.premium.indexOf(seatPos);
        if (index !== -1) {
          this.premium.splice(index, 1);
        }
        else {
          if ( this.vip.indexOf(seatPos) === -1 && this.standard.indexOf(seatPos) === -1) {
            this.premium.push(seatPos);
          }
        }
        break;
      case 'vip':
        index = this.vip.indexOf(seatPos);
        if (index !== -1) {
          this.vip.splice(index, 1);
        }
        else {
          if ( this.standard.indexOf(seatPos) === -1 && this.premium.indexOf(seatPos) === -1) {
            this.vip.push(seatPos);
          }
        }
        break;
    }
  }

  public setActual(actual: string): void {
    this.actual = actual;
  }

  public saveRoom(): void {
    if (this.info !== null) {
      this.loaded = false;
      const room = this.parseModifiedRoom();
      console.log(room);
      this.service.modifyRoom(room).subscribe(() => {}, error => {
        this.invalidResponseAlert.show();
        this.loaded = true;
      }, () => {
        this.correctResponseAlert.show();
        this.loaded = true;
      });
      console.log(room);
    }
    else {
      this.loaded = false;
      const room = this.parseNewRoom();
      this.service.saveRoom(room).subscribe(() => {}, error => {
        this.invalidResponseAlert.show();
        this.loaded = true;
      }, () => {
        this.correctResponseAlert.show();
        this.loaded = true;
      });
    }
  }

  private parseModifiedRoom(): object {
    const room = {
      id: this.roomId,
      name: this.name,
      nRows: this.rows,
      nColumns: this.columns,
      seats: []
    };
    // row * col standard seats
    if (this.standard.length === 0 && this.premium.length === 0 && this.vip.length === 0) {
      for (let i = 0; i < this.rows; i++) {
        for (let j = 1; j <= this.columns; j++ ) {
          let pushed = false;
          for (const seat of this.info.seats) {
            // if seat already exist
            if (seat.row === this.rowName(i) && +seat.column === +j) {
              room.seats.push({
                id: seat.id,
                row: this.rowName(i),
                column: j,
                seatType: 'STANDARD'
              });
              pushed = true;
              break;
            }
          }
          if (!pushed) {
            room.seats.push({
              row: this.rowName(i),
              column: j,
              seatType: 'STANDARD'
            });
          }
        }
      }
    }
    else {
      const re = new RegExp(/([A-Z]+)(\d+)/, 'i');
      // all standard seats
      for (const e of this.standard) {
        const x = re.exec(e);
        let pushed = false;
        for (const seat of this.info.seats) {
          // if seat already exist
          if (seat.row === x[1] && +seat.column === +x[2]) {
            room.seats.push({
              id: seat.id,
              row: x[1],
              column: x[2],
              seatType: 'STANDARD'
            });
            pushed = true;
            break;
          }
        }
        if (!pushed) {
          room.seats.push({
            row: x[1],
            column: x[2],
            seatType: 'STANDARD'
          });
        }
      }

      // all premium seats
      for (const e of this.premium) {
        const x = re.exec(e);
        let pushed = false;
        for (const seat of this.info.seats) {
          // if seat already exist
          if (seat.row === x[1] && +seat.column === +x[2]) {
            room.seats.push({
              id: seat.id,
              row: x[1],
              column: x[2],
              seatType: 'PREMIUM'
            });
            pushed = true;
            break;
          }
        }
        if (!pushed) {
          room.seats.push({
            row: x[1],
            column: x[2],
            seatType: 'PREMIUM'
          });
        }
      }

      // all vip seats
      for (const e of this.vip) {
        const x = re.exec(e);
        let pushed = false;
        for (const seat of this.info.seats) {
          // if seat already exist
          if (seat.row === x[1] && +seat.column === +x[2]) {
            room.seats.push({
              id: seat.id,
              row: x[1],
              column: x[2],
              seatType: 'VIP'
            });
            pushed = true;
            break;
          }
        }
        if (!pushed) {
          room.seats.push({
            row: x[1],
            column: x[2],
            seatType: 'VIP'
          });
        }
      }
    }
    return room;
  }

  private parseNewRoom(): object {
    const room = {
      name: this.name,
      nRows: this.rows,
      nColumns: this.columns,
      seats: []
    };
    // row * col standard seats
    if (this.standard.length === 0 && this.premium.length === 0 && this.vip.length === 0) {

      for (let i = 0; i < this.rows; i++) {
        for (let j = 1; j <= this.columns; j++ ) {
          room.seats.push({
            row: this.rowName(i),
            column: j,
            seatType: 'STANDARD'
          });
        }
      }

    }
    else {
      const re = new RegExp(/([A-Z]+)(\d+)/, 'i');
      for (const e of this.standard) {
        const x = re.exec(e);
        room.seats.push({
          row: x[1],
          column: x[2],
          seatType: 'STANDARD'
        });
      }
      for (const e of this.premium) {
        const x = re.exec(e);
        room.seats.push({
          row: x[1],
          column: x[2],
          seatType: 'PREMIUM'
        });
      }
      for (const e of this.vip) {
        const x = re.exec(e);
        room.seats.push({
          row: x[1],
          column: x[2],
          seatType: 'VIP'
        });
      }
    }
    return room;
  }


}
