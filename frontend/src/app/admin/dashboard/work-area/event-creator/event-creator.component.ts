import {AfterContentInit, Component, Input, OnInit, TemplateRef, ViewChild, ViewContainerRef} from '@angular/core';
import {EventRoom} from '../../../../model/EventRoom';
import {Show} from '../../../../model/Show';
import {AddEvent} from '../../../../model/AddEvent';
import {EventPrice} from '../../../../model/EventPrice';
import {ItemComponent} from '../item/item.component';
import {ListService} from '../default/services/list.service';
import {EventCreatorService} from './services/event-creator.service';
import {ShowService} from '../shows/services/show.service';
import {RoomService} from '../rooms/services/room.service';

@Component({
  selector: 'app-event-creator',
  templateUrl: './event-creator.component.html',
  styleUrls: ['./event-creator.component.css']
})
export class EventCreatorComponent implements OnInit, ItemComponent {

  @Input() type: string;

  @ViewChild('timeSlotTemplate', {read: TemplateRef}) timeSlotsTemplate: TemplateRef<any>;
  @ViewChild('timesContainer', {read: ViewContainerRef}) timeSlotsContainer: ViewContainerRef;
  @ViewChild('invalidFieldsToastAlert') invalidFieldsAlert;
  @ViewChild('invalidResponseToastAlert') invalidResponseAlert;
  @ViewChild('correctResponseToastAlert') correctResponseAlert;
  public position = { X: 'Left'};

  invalidResponseAlertBodyText: string;
  timeFormat = 'HH:mm';
  timeInterval = 15;
  timeSlotsViewCreated: number;
  invalidFields: boolean;

  eventRoomsLoaded: boolean;
  showsLoaded: boolean;

  availablesShowsList: string[] = [];
  eventRoomsList: number[] = [];

  eventRoomsObjects: EventRoom[];
  availablesShowsObjects: Show[];

  showSelectedName: string;
  eventRoomNameSelected: string;
  eventPriceSelected: EventPrice;
  eventStardDate: Date;
  eventEndDate: Date;
  eventTimeSlotsSelected: Date[];
  requestResponseEvent: AddEvent;
  eventId: string;

  constructor(private roomService: RoomService, private showService: ShowService, private eventCreatorService: EventCreatorService) {}

  ngOnInit(): void {
    this.timeSlotsViewCreated = 1;
    this.eventPriceSelected = {
      standardPrice: 0,
      vipPrice: 0,
      premiumPrice: 0
    };

    this.eventTimeSlotsSelected = [];
    this.eventRoomsLoaded = false;
    this.showsLoaded = false;
    this.roomService.getRooms().subscribe( (responseData) => {
      this.assignRooms(responseData);
      this.eventRoomsLoaded = true;
    });
    this.showService.getShows().subscribe( (responseData) => {
      this.assignShows(responseData);
      this.showsLoaded = true;
    });
  }

  insertNewTimeSlot(): void {
    this.timeSlotsContainer.createEmbeddedView(this.timeSlotsTemplate, {name: this.timeSlotsViewCreated});
    this.timeSlotsViewCreated++;
  }


  createNewEvent(): void {

    this.evaluateEventEndDate();
    this.evaluateEventPrice();
    this.evaluateEventRoomSelected();
    this.evaluateEventStartDate();
    this.evaluateEventTimeSlots();
    this.evaluateShowSelected();
    this.evaluateEventStartAndEndDate();


    if (this.invalidFields) {
      this.invalidFieldsAlert.show();
      this.invalidFields = false;
    } else {

      let room: any;
      let show: any;
      const startTimes = [];

      // tslint:disable-next-line:prefer-for-of
      for (let i = 0; i < this.eventRoomsObjects.length; i++) {
        if (this.eventRoomsObjects[i].name === this.eventRoomNameSelected) {
          room = {
            id: this.eventRoomsObjects[i].id
          };
          break;
        }
      }

      // tslint:disable-next-line:prefer-for-of
      for (let i = 0; i < this.availablesShowsObjects.length; i++) {
        if (this.availablesShowsObjects[i].name === this.showSelectedName) {
          show = {
            id: this.availablesShowsObjects[i].id,
          };
          break;
        }
      }

      let month;
      let day;

      if (this.eventStardDate.getMonth() + 1 < 10) {
        month = '0' + (this.eventStardDate.getMonth() + 1).toString();
      }
      else {
        month = (this.eventStardDate.getMonth() + 1).toString();
      }

      if (this.eventStardDate.getDate() < 10) {
        day = '0' + this.eventStardDate.getDate().toString();
      }
      else {
        day = this.eventStardDate.getDate().toString();
      }

      const formattedEventStartDate = this.eventStardDate.getFullYear().toString() + '-' +
        month + '-' + day;

      if (this.eventEndDate.getMonth() + 1 < 10) {
        month = '0' + (this.eventEndDate.getMonth() + 1).toString();
      }
      else {
        month = (this.eventEndDate.getMonth() + 1).toString();
      }

      if (this.eventEndDate.getDate() < 10) {
        day = '0' + this.eventEndDate.getDate().toString();
      }
      else {
        day = this.eventEndDate.getDate().toString();
      }

      const formattedEventEndDate = this.eventEndDate.getFullYear().toString() + '-' +
        month + '-' + day;

      let startTimeHours;
      let startTimeMinutes;

      // tslint:disable-next-line:prefer-for-of
      for (let i = 0; i < this.eventTimeSlotsSelected.length; i++) {
        if (this.eventTimeSlotsSelected[i].getHours() < 10) {
          startTimeHours = '0' + this.eventTimeSlotsSelected[i].getHours().toString();
        }
        else {
          startTimeHours = this.eventTimeSlotsSelected[i].getHours().toString();
        }

        if (this.eventTimeSlotsSelected[i].getMinutes() < 10) {
          startTimeMinutes = '0' + this.eventTimeSlotsSelected[i].getMinutes().toString();
        }
        else {
          startTimeMinutes = this.eventTimeSlotsSelected[i].getMinutes().toString();
        }

        startTimes.push(startTimeHours + ':' + startTimeMinutes);
      }

      const eventToAdd: AddEvent = {
        id: null,
        show,
        price: this.eventPriceSelected,
        startDate: formattedEventStartDate,
        endDate: formattedEventEndDate,
        startTimes,
        room
      };

      this.eventCreatorService.createNewEvent(eventToAdd).subscribe(
        data => {},
        error => {
          this.invalidResponseAlertBodyText = error.error +' perché la sala è già occupata da altri eventi';
          this.invalidResponseAlert.show();
        },
        () => {
          this.correctResponseAlert.show();
        });
    }
  }

  evaluateEventTimeSlots(): boolean {
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.eventTimeSlotsSelected.length; i++) {
      if (this.eventTimeSlotsSelected[i] === undefined || this.eventTimeSlotsSelected[i] === null){
        this.invalidFields = true;
        return false;
      }
    }

    return true;
  }

  evaluateEventStartDate(): boolean {
    if (this.eventStardDate === undefined || this.eventStardDate === null){
      this.invalidFields = true;
      return false;
    }

    return true;
  }

  evaluateEventStartAndEndDate(): boolean {
    if (Math.floor((Date.UTC(this.eventEndDate.getFullYear(), this.eventEndDate.getMonth(),
      this.eventEndDate.getDate()) - Date.UTC(this.eventStardDate.getFullYear(), this.eventStardDate.getMonth(),
      this.eventStardDate.getDate())) / (1000 * 60 * 60 * 24)) < 0) {
      return false;
    }
    return true;
  }

  evaluateEventEndDate(): boolean {
    if (this.eventEndDate === undefined || this.eventEndDate === null){
      this.invalidFields = true;
      return false;
    }

    return true;
  }

  evaluateEventRoomSelected(): boolean {
    if (this.eventRoomNameSelected === undefined || this.eventRoomNameSelected === null) {
      this.invalidFields = true;
      return false;
    }

    return true;
  }

  evaluateShowSelected(): boolean {
    if (this.showSelectedName === undefined || this.showSelectedName === null){
      this.invalidFields = true;
      return false;
    }

    return true;
  }

  evaluateEventPrice(): boolean {
    if (isNaN(Number(this.eventPriceSelected.standardPrice)) || Number(this.eventPriceSelected.standardPrice) === 0){
      this.invalidFields = true;
      return false;
    }

    if (isNaN(Number(this.eventPriceSelected.premiumPrice)) || Number(this.eventPriceSelected.premiumPrice) === 0){
      this.invalidFields = true;
      return false;
    }

    if (isNaN(Number(this.eventPriceSelected.vipPrice)) || Number(this.eventPriceSelected.vipPrice) === 0){
      this.invalidFields = true;
      return false;
    }

    return true;
  }

  assignRooms(rooms): void {
    this.eventRoomsObjects = rooms;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < rooms.length; i++) {
      this.eventRoomsList.push(rooms[i].name);
    }
  }

  assignShows(shows): void {
    this.availablesShowsObjects = shows;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < shows.length; i++) {
      this.availablesShowsList.push(shows[i].name);
    }
  }

}
