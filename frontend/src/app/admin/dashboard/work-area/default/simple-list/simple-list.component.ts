import {Component, Inject, Input, OnInit, ViewChild} from '@angular/core';
import { CommandModel, GridComponent, ToolbarService} from '@syncfusion/ej2-angular-grids';
import { ListService } from '../services/list.service';
import {ItemComponent} from '../../item/item.component';
import {WorkAreaComponent} from '../../work-area.component';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'ej-grid-simple-list',
  templateUrl: './simple-list.component.html',
  styleUrls: ['./simple-list.component.css'],
  providers: [ToolbarService]
})
export class SimpleListComponent implements OnInit, ItemComponent {

  @Input() type: string;
  public data: any;
  public editSettings: object;
  public nameRules: object;
  public idRules: object;
  public editParams: object;
  public pageSettings: object;
  public commands: CommandModel[];
  public loaded = false;
  public toolbar: string[];

  @ViewChild('grid')
  public grid: GridComponent;
  @ViewChild('invalidResponseToastAlert') invalidResponseAlert;
  @ViewChild('correctResponseToastAlert') correctResponseAlert;
  @ViewChild('correctDeleteToastAlert') correctDeleteToastAlert;
  public position = { X: 'Left'};

  constructor(@Inject(WorkAreaComponent) private parent: WorkAreaComponent, private service: ListService) {
  }

  public ngOnInit(): void {

    this.loadData();

    this.editSettings = { allowEditing: true, allowDeleting: true, mode: 'Dialog', allowEditOnDblClick: false,
      showDeleteConfirmDialog: true};
    this.nameRules = { required: true };
    this.idRules = { required: false, maxLenghth: 0 };
    this.editParams = {  params: { popupHeight: '300px' }};
    this.pageSettings = {pageCount: 8};
    this.commands = [{ type: 'Edit', buttonOption: { iconCss: ' e-icons e-edit', cssClass: 'e-flat' } },
      { type: 'Delete', buttonOption: { iconCss: 'e-icons e-delete', cssClass: 'e-flat' }  },
      { type: 'Save', buttonOption: { iconCss: 'e-icons e-update', cssClass: 'e-flat' } },
      { type: 'Cancel', buttonOption: { iconCss: 'e-icons e-cancel-icon', cssClass: 'e-flat' } }];
  }

  public loadData(): void {
    switch (this.type){
      case 'Attori':
        this.service.getActors().subscribe(response => {
          this.data = response;
          this.loaded = true;
        }, error => {
          this.invalidResponseAlert.show();
        });
        break;
      case 'Registi':
        this.service.getDirectors().subscribe(response => {
          this.data = response;
          this.loaded = true;
        }, error => {
          this.invalidResponseAlert.show();
        });
        break;
      case 'Categorie':
        this.service.getCategories().subscribe(response => {
          this.data = response;
          this.loaded = true;
        }, error => {
          this.invalidResponseAlert.show();
        });
        break;
      default:
        this.data = null;
    }
  }

  public actionBegin(args: any): void {
    if (args.requestType === 'save'){
      this.loaded = false;
      switch (this.type){
        case 'Attori':
          this.service.updateActor(args.data).subscribe(response => {}, error => {
            this.loadData();
            this.invalidResponseAlert.show();
          }, () => {
            this.loadData();
            this.correctResponseAlert.show();
          });
          break;
        case 'Registi':
          this.service.updateDirector(args.data).subscribe(response => {}, error => {
            this.loadData();
            this.invalidResponseAlert.show();
          }, () => {
            this.loadData();
            this.correctResponseAlert.show();
          });
          break;
        case 'Categorie':
          this.service.updateCategorie(args.data).subscribe(response => {}, error => {
            this.loadData();
            this.invalidResponseAlert.show();
          }, () => {
            this.loadData();
            this.correctResponseAlert.show();
          });
          break;
      }
    }
    else if (args.requestType === 'delete'){
      this.loaded = false;
      switch (this.type){
        case 'Attori':
          this.service.deleteActor(args.data[0].id).subscribe(response => {}, error => {
            this.loadData();
            this.invalidResponseAlert.show();
          }, () => {
            this.loadData();
            this.correctDeleteToastAlert.show();
          });
          break;
        case 'Registi':
          this.service.deleteDirector(args.data[0].id).subscribe(response => {}, error => {
            this.loadData();
            this.invalidResponseAlert.show();
          }, () => {
            this.loadData();
            this.correctDeleteToastAlert.show();
          });
          break;
        case 'Categorie':
          this.service.deleteCategorie(args.data[0].id).subscribe(response => {}, error => {
            this.loadData();
            this.invalidResponseAlert.show();
          }, () => {
            this.loadData();
            this.correctDeleteToastAlert.show();
          });
          break;
      }
    }
  }

}
