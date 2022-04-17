import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {WorkAreaComponent} from '../admin/dashboard/work-area/work-area.component';
import {CommandModel, GridComponent} from '@syncfusion/ej2-angular-grids';
import {PersonalAreaService} from '../services/personal-area.service';
import {Timestamp} from 'rxjs/internal-compatibility';
import {DashboardItem} from '../admin/dashboard/work-area/item/dashboard-item';
import {Router} from '@angular/router';
import {Toast} from '@syncfusion/ej2-notifications';

@Component({
  selector: 'app-personal-area',
  templateUrl: './personal-area.component.html',
  styleUrls: ['./personal-area.component.css'],
})
export class PersonalAreaComponent implements OnInit {



  public commands: CommandModel[];
  public editSettings: object;
  public editParams: object;
  public pageSettings: object;
  public data: object;
  public personalData: any;
  public modifica: boolean;
  public modificaPassword: boolean;
  public male: boolean;
  public other: boolean;



  constructor(private service: PersonalAreaService, private router: Router) {

  }

  ngOnInit(): void {

    if (localStorage.getItem('loggatoAdmin') === 'true' || localStorage.getItem('loggatoUser') === 'false') {
      this.router.navigate(['login']);
      return;
    }

    this.personalData={"firstName":"","username":"","lastName":"","email":"","birthdate":"","gender":"","hashedPassword":""};
    this.modifica=false;
    this.modificaPassword=false;
    this.editSettings = { allowEditing: true, allowDeleting: true, mode: 'Dialog', allowEditOnDblClick: false,
      showDeleteConfirmDialog: true };
    this.editParams = {  params: { popupHeight: '300px' }};
    this.pageSettings = { pageSize: 50 };
    this.commands = [
      { type: 'Delete', buttonOption: { iconCss: 'e-icons e-delete', cssClass: 'e-flat' }  },
      { type: 'Save', buttonOption: { iconCss: 'e-icons e-update', cssClass: 'e-flat' } },
      { type: 'Cancel', buttonOption: { iconCss: 'e-icons e-cancel-icon', cssClass: 'e-flat' } }];
    this.loadBooking();
    this.loadPersonalData();

  }
  public actionBegin(args: any): void {
    if (args.requestType === 'delete') {
      this.service.deleteBooking(args.data[0].bookingId).subscribe(response => {
        this.loadBooking();
        const t = new Toast({
          title: 'Operazione effettuata',
          content: 'Prenotazione rimossa correttamente.',
          cssClass: 'e-toast-success'
        }); t.appendTo('#toastDiv'); t.show();
      },error => {
        const t = new Toast({
          title: 'Qualcosa è andat storto.',
          content: 'La prenotazione non è stata rimossa correttamente.',
          cssClass: 'e-toast-danger'
        }); t.appendTo('#toastDiv'); t.show();
          this.loadBooking();
        });
    }
  }
  public loadPersonalData(): void{
    this.service.getPersonalData().subscribe(response => {
      this.personalData=response;
      if(this.personalData.gender=="MALE") {
        this.male = true;
        this.personalData.gender="Maschio";
      }
      else if(this.personalData.gender=="FEMALE") {
        this.male = false;
        this.personalData.gender = "Femmina";
      }
      else {
        this.personalData.gender="Altro";
        this.other = true;
      }
    });

  }


  modificaDati() {
    this.modifica=true;
  }
  modificaPass() {
    this.modificaPassword=true;
  }

  salvaDati(name: string,surname: string, username: string, email: string, birthDate: string, gender: string) {
    var newData={"id":"","firstName":"","username":"","lastName":"","email":"","birthdate":"","gender":""};
    newData.id=this.personalData.id;
    newData.firstName = name;
    newData.lastName = surname;
    newData.username = username;
    newData.email = email;
    newData.birthdate = birthDate;
    if(gender=="Maschio")
      newData.gender="MALE";
    else if(gender=="Femmina")
      newData.gender="FEMALE"
    else if(gender=="Altro")
      newData.gender="OTHER";
    else
      newData.gender = gender;
    this.service.savePersonalData(newData).subscribe(response => {
      const t = new Toast({
        title: 'Operazione effettuata',
        content: 'Dati cambiati correttamente.',
        cssClass: 'e-toast-success'
      }); t.appendTo('#toastDiv'); t.show();
      this.modificaPassword=false;
      this.modifica=false;
    }, error => {
      const t = new Toast({
        title: 'Operazione non completata.',
        content: 'Si è riscontrato un errore inaspettato.',
        cssClass: 'e-toast-danger'
      }); t.appendTo('#toastDiv'); t.show();
      this.modificaPassword=false;
      this.modifica=false;
      });
  }
  salvaPassword(password: string) {
    var newData={"id":"","hashedPassword":""};
    newData.id=this.personalData.id;
    if(password!="********" && password!="")
      newData.hashedPassword = password;
    else
      newData.hashedPassword = null;
    this.service.savePassword(newData).subscribe(response => {
      const t = new Toast({
        title: 'Operazione effettuata',
        content: 'Password cambiata correttamente.',
        cssClass: 'e-toast-success'
      }); t.appendTo('#toastDiv'); t.show();
      this.modificaPassword=false;
      this.modifica=false;
    }, error => {
      const t = new Toast({
        title: 'Operazione non completata.',
        content: 'Si è riscontrato un errore inaspettato.',
        cssClass: 'e-toast-danger'
      }); t.appendTo('#toastDiv'); t.show();
      this.modificaPassword=false;
      this.modifica=false;
    });
  }
  private loadBooking() {
    this.service.getBookings().subscribe(response => {
      this.data=response;
    });
  }
}
