import {Component, Inject, Input, OnInit, ViewChild} from '@angular/core';
import {CommandModel, GridComponent} from '@syncfusion/ej2-angular-grids';
import {ItemComponent} from '../../item/item.component';
import {WorkAreaComponent} from '../../work-area.component';
import {DashboardItem} from '../../item/dashboard-item';
import {Show} from '../../../../../model/Show';
import {Router} from '@angular/router';
import {ShowService} from '../services/show.service';
import {ShowCreatorComponent} from '../show-creator/show-creator.component';

@Component({
  selector: 'app-show-list',
  templateUrl: './show-list.component.html',
  styleUrls: ['./show-list.component.css']
})
export class ShowListComponent implements OnInit, ItemComponent {
  @Input() type: string;
  public data: Show[];
  public editSettings: object;
  public titleRules: object;
  public descriptionRules: object;
  public coverRules: object;
  public editParams: object;
  public pageSettings: object;
  public commands: CommandModel[];
  public loaded = false;
  @ViewChild('grid')
  public grid: GridComponent;
  @ViewChild('invalidResponseToastAlert') invalidResponseAlert;
  @ViewChild('correctResponseToastAlert') correctResponseAlert;
  @ViewChild('correctDeleteToastAlert') correctDeleteToastAlert;
  public position = { X: 'Left'};

  constructor(@Inject(WorkAreaComponent) private parent: WorkAreaComponent, private service: ShowService, private router: Router) {}

  public ngOnInit(): void {

    this.loadData();

    this.editSettings = { allowEditing: true, allowDeleting: true, mode: 'Dialog', allowEditOnDblClick: false,
      showDeleteConfirmDialog: true };
    // this.editSettings = { allowDeleting: true, mode: 'Dialog', allowEditOnDblClick: false,
    //   showDeleteConfirmDialog: true };
    this.titleRules = { required: true };
    this.descriptionRules = { required: true };
    this.editParams = {  params: { popupHeight: '300px' }};
    this.coverRules = { required: true };
    this.pageSettings = { pageSize: 50 };
    /*this.commands = [{ type: 'Edit', buttonOption: { iconCss: ' e-icons e-edit', cssClass: 'e-flat' } },
      { type: 'Delete', buttonOption: { iconCss: 'e-icons e-delete', cssClass: 'e-flat' }  },
      { type: 'Save', buttonOption: { iconCss: 'e-icons e-update', cssClass: 'e-flat' } },
      { type: 'Cancel', buttonOption: { iconCss: 'e-icons e-cancel-icon', cssClass: 'e-flat' } }];*/
    this.commands = [{ type: 'Edit', buttonOption: { iconCss: ' e-icons e-edit', cssClass: 'e-flat' } },
      { type: 'Delete', buttonOption: { iconCss: 'e-icons e-delete', cssClass: 'e-flat' } },
    ];
  }

  public loadData(): void {
    this.service.getShows().subscribe(response => {
      this.data = response;
      for (const show of this.data) {
        let actors = '';
        for (const act of show.actors) {
          actors += act.name + ', ';
        }
        show.actors = actors;
        let directors = '';
        for (const dir of show.directors) {
          directors += dir.name + ', ';
        }
        show.directors = directors;
        let categories = '';
        for (const cat of show.categories) {
          categories += cat.name + ', ';
        }
        show.categories = categories;
      }
      this.loaded = true;
    }, error => {
      if (error.error === 'You are not authorized') {
        this.router.navigate(['login']);
      }
      else {
        this.invalidResponseAlert.show();
      }
    });
  }

  public actionBegin(args: any): void {
    if (args.requestType === 'beginEdit'){
      /*this.service.updateShow(args.data).subscribe(response => {
        alert('Oggetto salvato!');
        this.parent.loadComponent(new DashboardItem(ShowListComponent, 'Spettacoli'));
      }, error => {
        alert('Ops.. Qualcosa è andato storto! \n Può essere che l\'elemento esiste già nel database! \n Riprova per favore...');
        this.parent.loadComponent(new DashboardItem(ShowListComponent, 'Spettacoli'));
      });*/
      this.parent.loadComponent(new DashboardItem(ShowCreatorComponent, 'Modify-' + this.data[args.rowIndex].id,
        this.data[args.rowIndex]));
    }
    else if (args.requestType === 'delete') {
      this.loaded = false;
      this.service.deleteShow(args.data[0].id).subscribe(() => { }, error => {
        this.loadData();
        this.invalidResponseAlert.show();
      }, () => {
        this.loadData();
        this.correctDeleteToastAlert.show();
      });
    }
  }
}
