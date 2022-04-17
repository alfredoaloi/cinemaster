import {Component, OnInit} from '@angular/core';
import {L10n, loadCldr, setCulture, setCurrencyCode} from '@syncfusion/ej2-base';
declare var require: any;
// loadCldr(
//   require('cldr-data/main/it/numbers.json'),
//   require('cldr-data/main/it/ca-gregorian.json'),
//   require('cldr-data/supplemental/numberingSystems.json'),
//   require('cldr-data/main/it/timeZoneNames.json'),
//   require('cldr-data/supplemental/weekdata.json'),
//   require('cldr-data/main/it/currencies.json')
// );

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'CineMasterFrontEnd';

  ngOnInit(): void {
    setCulture('it');
    setCurrencyCode('EUR');
    L10n.load({
      it: {
        datepicker: {
          placeholder: 'Seleziona la data',
          today: 'Oggi'
        },
        uploader: {
          invalidMaxFileSize : 'Il file selezionato supera la dimensione massima di 1.5 MB',
          invalidFileType : 'Il file selezionato non è un file immagine supportato',
          readyToUploadMessage : 'File pronto all\'upload',
          Browse : 'Scegli file'
        },
        grid: {
          ConfirmDelete : 'Sicuro di voler rimuovere l\'elemento?',
          EmptyRecord: 'Nessun elemento disponibile'
        },
        pager: {
          currentPageInfo: '{0} Di {1} Pagine',
          totalItemsInfo: '({0} Oggetti)',
        }
      }
    });
    console.log(localStorage.getItem('loggatoUser'));
    if(localStorage.getItem('loggatoUser')==null)
      localStorage.setItem('loggatoUser','false');
    if(localStorage.getItem('loggatoAdmin')==null)
      localStorage.setItem('loggatoAdmin','false');
    if(localStorage.getItem('loggatoCashier')==null)
      localStorage.setItem('loggatoCashier','false');

  }
}
