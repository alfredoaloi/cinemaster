import {Component, Inject, Input, OnInit, ViewChild} from '@angular/core';
import {ItemComponent} from '../item/item.component';
import {CommandModel, GridComponent} from '@syncfusion/ej2-angular-grids';
import {AddEvent} from '../../../../model/AddEvent';
import {WorkAreaComponent} from '../work-area.component';
import {Router} from '@angular/router';
import {EventListService} from './services/event-list.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit, ItemComponent {
  @Input() type: string;
  public data: AddEvent[];
  public showRules: object;
  public roomRules: object;
  public pricesRules: object;
  public startDateRules: object;
  public endDateRules: object;
  public editparams: object;
  public pageSettings: object;
  public loaded = false;
  it: any;

  @ViewChild('grid')
  public grid: GridComponent;
  @ViewChild('invalidResponseToastAlert') invalidResponseAlert;
  @ViewChild('correctResponseToastAlert') correctResponseAlert;
  @ViewChild('correctDeleteToastAlert') correctDeleteToastAlert;

  public commands = [{ type: 'Delete', buttonOption: { iconCss: 'e-icons e-delete', cssClass: 'e-flat' }  }];
  public editSettings = { allowEditing: true, allowDeleting: true, mode: 'Dialog', allowEditOnDblClick: false,
    showDeleteConfirmDialog: true };
  public position = { X: 'Left'};

  constructor(@Inject(WorkAreaComponent) private parent: WorkAreaComponent, private service: EventListService, private router: Router) {}

  ngOnInit(): void {

    this.loadData();

    this.showRules = { required: true };
    this.roomRules = { required: true };
    this.pricesRules = { required: true };
    this.startDateRules = { required: true, format: 'YYYY-MM-DD'};
    this.endDateRules = { required: true, format: 'YYYY-MM-DD'};
    this.editparams = {  params: { popupHeight: '300px' }};
    this.pageSettings = { pageSize: 50 };

  }

  public loadData(): void {
    this.service.getEvents().subscribe(response => {
      this.data = response;
      this.loaded = true;
    }, error => {
      this.invalidResponseAlert.show();
    });
  }

  public actionBegin(arg0: any): void {
    if (arg0.requestType === 'delete') {
      this.loaded = false;
      this.service.deleteEvent(arg0.data[0].id).subscribe(() => { }, error => {
        this.loadData();
        this.invalidResponseAlert.show();
      }, () => {
        this.loadData();
        this.correctDeleteToastAlert.show();
      });
    }
  }

}
