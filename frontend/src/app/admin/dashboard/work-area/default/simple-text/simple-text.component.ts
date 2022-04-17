import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {InPlaceEditorComponent, MultiSelectService, RteService} from '@syncfusion/ej2-angular-inplace-editor';
import {TextBoxModel} from '@syncfusion/ej2-inputs';
import {ItemComponent} from '../../item/item.component';
import {SimpleTextService} from './services/simple-text.service';

@Component({
  selector: 'app-simple-text',
  templateUrl: './simple-text.component.html',
  styleUrls: ['./simple-text.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [RteService, MultiSelectService]
})
export class SimpleTextComponent implements OnInit, ItemComponent {

  @Input() type: string;

  @ViewChild('inplaceTitleEditor')
  public titleEditorObj: InPlaceEditorComponent;
  @ViewChild('invalidResponseToastAlert') invalidResponseAlert;
  @ViewChild('correctResponseToastAlert') correctResponseAlert;
  @ViewChild('invalidNameToastAlert') invalidNameToastAlert;
  public position = { X: 'Left'};

  public titleEditorValue = 'Inserisci il nome';
  public scrollParent: HTMLElement;
  public titleEditorModel: TextBoxModel = {
    placeholder: 'Inserisci il nome'
  };

  public titleRule: { [name: string]: { [rule: string]: object } } = {
    Title: { required: [true, 'Inserisci un nome valido'] }
  };

  constructor(private service: SimpleTextService) {
  }

  ngOnInit(): void {
  }

  storeElement(): void {
    if (this.titleEditorObj.value !== null){
      switch (this.type){
        case 'Attori':
          this.service.addActor({name: this.titleEditorObj.value}).subscribe(() => {}, error => {
              this.invalidResponseAlert.show();
            },
            () => {
              this.correctResponseAlert.show();
            });
          break;
        case 'Registi':
          this.service.addDirector({name: this.titleEditorObj.value}).subscribe(() => {}, error => {
              this.invalidResponseAlert.show();
            },
            () => {
              this.correctResponseAlert.show();
            });
          break;
        case 'Categorie':
          this.service.addGenre({name: this.titleEditorObj.value}).subscribe(() => {}, error => {
              this.invalidResponseAlert.show();
            },
            () => {
              this.correctResponseAlert.show();
            });
          break;
        case 'Sale':
          this.service.addRoom({name: this.titleEditorObj.value}).subscribe(() => {}, error => {
              this.invalidResponseAlert.show();
            },
            () => {
              this.correctResponseAlert.show();
            });
          break;
      }
    } else {
      this.invalidNameToastAlert.show();
    }
  }

}
