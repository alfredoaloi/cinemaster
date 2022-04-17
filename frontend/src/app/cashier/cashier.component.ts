import {Component, OnInit, ViewChild} from '@angular/core';
import {CashierService} from './services/cashier.service';
import { NormalEvent } from '../model/NormalEvent';
import {SeatReservationComponent} from '../show-detail/seat-reservation/seat-reservation.component';
import {AuthenticationService} from '../services/authentication.service';
import {Router} from '@angular/router';
import {CashierPlantComponent} from './plant/cashier-plant/cashier-plant.component';

@Component({
  selector: 'app-cashier',
  templateUrl: './cashier.component.html',
  styleUrls: ['./cashier.component.css']
})
export class CashierComponent implements OnInit {

  @ViewChild('invalidResponseToastAlert') invalidResponseAlert;
  @ViewChild('correctResponseToastAlert') correctResponseAlert;
  @ViewChild('correctDeleteToastAlert') correctDeleteToastAlert;
  @ViewChild('bookingCompletedToastAlert') bookingCompletedToastAlert;
  @ViewChild('bookingFailedToastAlert') bookingFailedToastAlert;
  @ViewChild('noSeatsAlert') noSeatsAlert;
  @ViewChild('seatReservationComponent') seatReservation: CashierPlantComponent;
  public position = { X: 'Left'};
  public events: any[];
  public purchases: object[];
  public loaded = false;
  public loading = false;
  public bookingIdentifier: object[];
  public commands = [{ type: 'Delete', buttonOption: { iconCss: 'e-icons e-delete', cssClass: 'e-flat' }  }];

  public headerText: object = [{ text: 'Lista Eventi'}, { text: 'Storico Acquisti'}];
  public editSettings = { allowEditing: true, allowDeleting: true, mode: 'Dialog', allowEditOnDblClick: false,
    showDeleteConfirmDialog: true };
  public wantsToBuy = false;
  constructor(private service: CashierService, private authService: AuthenticationService, private router: Router) { }

  public loadEvents(): void {
    this.service.getEvents().subscribe(response => {
      this.events = response;
      this.loaded = true;
    }, error => {
      this.invalidResponseAlert.show();
    });
  }

  public loadPurchases(): void {
    this.loaded = false;
    this.service.getPurchases().subscribe(response => {
      this.purchases = response;
      this.loaded = true;
      console.log(this.purchases);
    }, error => {
      if (error.error === 'You are not authorized') {
        this.router.navigate(['login']);
      }
      else {
        this.invalidResponseAlert.show();
      }
    });
  }

  ngOnInit(): void {
    if (localStorage.getItem('loggatoCashier') === 'false') {
      this.router.navigate(['login']);
      return;
    }
    this.loadEvents();
    this.loadPurchases();
  }

  public renderPlant(event: NormalEvent): void{
    this.seatReservation.loadData(event);
  }

  public actionBegin(arg0: any): void {
    if (arg0.requestType === 'delete') {
      this.loaded = false;
      this.service.deletePurchase(arg0.data[0].id).subscribe(() => { }, error => {
        this.loadPurchases();
        this.invalidResponseAlert.show();
      }, () => {
        this.loadPurchases();
        this.correctDeleteToastAlert.show();
      });
    }
  }

  public doLogout(): void {
    this.authService.logoutUser().subscribe(response => {
      localStorage.setItem('loggatoCashier', 'false');
      this.router.navigate(['login']);
    });
  }

  private createBookingConfirmationDetails(bookingDetails: object[], bookingIdentifiers: object[]): object[] {

    const bookingConfirmationDetails = [];

    for (let i = 0; i < bookingIdentifiers.length; i++) {
      for (let j = 0; j < bookingDetails.length; j++) {
        let find = false;
        for (let k = 0; k < bookingDetails[j]['seats'].length; k++) {
          if(bookingDetails[j]['seats'][k]['id'] === bookingIdentifiers[i]['seat']['id']){
            find = true;
            bookingConfirmationDetails.push(
              {
                booking: this.bookingIdentifier[i],
                price: bookingDetails[j]['seats'][k]['price']
              });
            break;
          }
        }

        if(find)
          break;
      }
    }

    return bookingConfirmationDetails;
  }

  public confirm(): void {
    this.loading = true;
    this.service.bookEventsSeats(this.seatReservation.getReservedSeatByEvent()).subscribe( response => {
          this.bookingIdentifier = response;
        },
        error => { this.noSeatsAlert.show(); },
        () => {
          const bookingConfirmationDetails = this.createBookingConfirmationDetails(this.seatReservation.getReservedSeatByEvent(),
            this.bookingIdentifier);
          this.service.paymentCompletedNotification(bookingConfirmationDetails).subscribe(() => {},
            error => { this.noSeatsAlert.show(); }, () => { this.bookingCompletedToastAlert.show(); this.loading = false;
              this.loadPurchases(); });
        }
      );
  }

  public getTicket(id: number): void {
    window.open('http://localhost:8080/booking/ticket?id=' + id, '_blank');
  }
}
