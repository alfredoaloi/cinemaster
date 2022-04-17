import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ICreateOrderRequest, IPayPalConfig} from 'ngx-paypal';
import {BookingEventService} from './services/booking-event.service';

@Component({
  selector: 'app-paypal-payment-executor',
  templateUrl: './paypal-payment-executor.component.html',
  styleUrls: ['./paypal-payment-executor.component.css']
})
export class PaypalPaymentExecutorComponent implements OnInit {

  @ViewChild('bookingFailedToastAlert') bookingFailedAlert;
  @ViewChild('paymentFailedToastAlert') paymentFailedAlert;
  @ViewChild('couponInvalidAlert') couponInvalidAlert;
  @ViewChild('bookingCompletedToastAlert') bookingCompletedAlert;
  @ViewChild('paymentCompletedToastAlert') paymentCompletedAlert;
  @ViewChild('notAuthorizedAlert') notAuthorizedAlert;
  @ViewChild('couponValidAlert') couponValidAlert;
  public position = { X: 'Left'};

  public payPalConfig?: IPayPalConfig;
  bookingCompleted: boolean;
  bookingTrying: boolean;
  bookingConfirmation: boolean;
  couponTrying: boolean;
  bookingIdentifier: object[];
  couponCode: string;
  discount: number;
  numberOfSeatsBooked: number;
  @Input() eventsBookingDetails: object[];
  @Input() eventsBookingTotalPrice: number;
  @Output() bookingCompletedEmitter = new EventEmitter<boolean>();
  @Output() totalPriceChanged = new EventEmitter<number>();

  constructor(private bookingEventService: BookingEventService) { }

  ngOnInit(): void {
    this.bookingCompleted = false;
    this.bookingTrying = false;
    this.bookingConfirmation = false;
    this.couponTrying = false;
  }

  private countTotalSeatsBooked(bookingDetails: object[]): number {
    let seats = 0;
    for (let i = 0; i < bookingDetails.length; i++) {
      seats += bookingDetails[i]['seats'].length;
    }

    return seats;
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

  private checkBookingAvailability(): void{
    this.bookingTrying = true;
    this.bookingEventService.bookEventsSeats(this.eventsBookingDetails).subscribe(
      data => {
        this.bookingIdentifier = data;
      },
      error => {
        if(error.status === 403)
          this.notAuthorizedAlert.show();
        else
          this.bookingFailedAlert.show();
        this.bookingTrying = false;
      },
      () => {
        this.bookingCompletedAlert.show();
        this.bookingCompleted = true;
        if(this.eventsBookingTotalPrice > 0){
          this.initConfig();
        } else {
          this.bookingConfirmation = true;
          const bookingConfirmationDetails = this.createBookingConfirmationDetails(this.eventsBookingDetails, this.bookingIdentifier);
          this.bookingEventService.paymentCompletedNotification(bookingConfirmationDetails).subscribe(
            value => {

            },
            error => {
              this.paymentFailedAlert.show();
            },
            () => {
              this.paymentCompletedAlert.show();
              this.bookingCompleted = false;
              this.bookingTrying = false;
              this.bookingConfirmation = false;
            }
          );
        }

      }
    );
  }

  private verifyCouponAvailability(): void {
    this.couponTrying = true;
    this.bookingEventService.verifiyCouponValidity(this.couponCode).subscribe(
      data => {
        this.discount = data;
      },
      error => {
        this.couponInvalidAlert.show();
        this.couponTrying = false;
      },
      () => {
        this.couponValidAlert.show();
        this.couponTrying = false;
        this.eventsBookingTotalPrice -= this.discount;
        this.totalPriceChanged.emit(this.eventsBookingTotalPrice);
        if(this.eventsBookingTotalPrice < 0)
          this.eventsBookingTotalPrice = 0;
      }
    );
  }

  private initConfig(): void {
    this.numberOfSeatsBooked = this.countTotalSeatsBooked(this.eventsBookingDetails);
    this.bookingCompletedEmitter.emit(true);
    this.payPalConfig = {
      currency: 'EUR',
      clientId: 'AUuQsZ6W_kSfRMWY_JWNer6Ho-eU3XDcVAgce3CZYj8LhYJO4ZiL9AME6LMbWHPxGkbTVp3zHpoQudsR',
      createOrderOnClient: (data) => <ICreateOrderRequest>{
        intent: 'CAPTURE',
        purchase_units: [
          {
            amount: {
              currency_code: 'EUR',
              value: this.eventsBookingTotalPrice.toString(),
              breakdown: {
                item_total: {
                  currency_code: 'EUR',
                  value: this.eventsBookingTotalPrice.toString()
                }
              }
            },
            items: [
              {
                name: 'Prenotazione di ' + this.numberOfSeatsBooked.toString() + ' Biglietti CineMaster',
                quantity: '1',
                category: 'DIGITAL_GOODS',
                unit_amount: {
                  currency_code: 'EUR',
                  value: this.eventsBookingTotalPrice.toString(),
                },
              }
            ]
          }
        ]
      },
      advanced: {
        commit: 'true'
      },
      style: {
        label: 'paypal',
        layout: 'vertical'
      },
      onApprove: (data, actions) => {
        actions.order.get().then(details => {});
      },
      onClientAuthorization: (data) => {
        this.bookingConfirmation = true;
        const bookingConfirmationDetails = this.createBookingConfirmationDetails(this.eventsBookingDetails, this.bookingIdentifier);
        this.bookingEventService.paymentCompletedNotification(bookingConfirmationDetails).subscribe(
          value => {

          },
          error => {
            this.paymentFailedAlert.show();
          },
          () => {
            this.paymentCompletedAlert.show();
            this.bookingCompleted = false;
            this.bookingTrying = false;
            this.bookingConfirmation = false;
            this.bookingCompletedEmitter.emit(false);
          }
        );
      },
      onCancel: (data, actions) => {},
      onError: err => {},
      onClick: (data, actions) => {},
    };
  }

}
