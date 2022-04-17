import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { MenuModule, SidebarModule } from '@syncfusion/ej2-angular-navigations';
import { CommandColumnService, EditService, GridModule, PageService} from '@syncfusion/ej2-angular-grids';
import { ButtonModule, CheckBoxModule } from '@syncfusion/ej2-angular-buttons';
import { TextBoxModule, NumericTextBoxModule, UploaderModule } from '@syncfusion/ej2-angular-inputs';
import { DropDownListModule, MultiSelectModule } from '@syncfusion/ej2-angular-dropdowns';
import { InPlaceEditorModule } from '@syncfusion/ej2-angular-inplace-editor';
import { DialogModule } from '@syncfusion/ej2-angular-popups';
import { DatePickerModule, TimePickerModule } from '@syncfusion/ej2-angular-calendars';
import { ToastModule } from '@syncfusion/ej2-angular-notifications';
import { TabModule } from '@syncfusion/ej2-angular-navigations';
import { SlickCarouselModule } from 'ngx-slick-carousel';

import { WorkAreaComponent } from './admin/dashboard/work-area/work-area.component';
import { ItemDirective } from './admin/dashboard/work-area/item/item.directive';
import { ItemService } from './admin/dashboard/work-area/item/item.service';
import { SimpleTextComponent } from './admin/dashboard/work-area/default/simple-text/simple-text.component';
import { SimpleListComponent } from './admin/dashboard/work-area/default/simple-list/simple-list.component';
import { ShowCreatorComponent } from './admin/dashboard/work-area/shows/show-creator/show-creator.component';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { HomeComponent } from './home/home.component';
import { CarouselComponent } from './home/carousel/carousel.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FooterComponent } from './footer/footer.component';
import { ShowDetailComponent } from './show-detail/show-detail.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { NavbarComponent } from './home/navbar/navbar.component';
import { SeatReservationComponent } from './show-detail/seat-reservation/seat-reservation.component';
import {SplitterModule} from '@syncfusion/ej2-angular-layouts';
import {ShowsSliderComponent} from './home/shows-slider/shows-slider.component';
import { RoomsListComponent } from './admin/dashboard/work-area/rooms/rooms-list/rooms-list.component';
import { SearchResultsComponent } from './home/search-results/search-results.component';
import {ResultItemComponent} from './home/search-results/result-item/result-item.component';
import {RoomCreatorComponent} from './admin/dashboard/work-area/rooms/room-creator/room-creator.component';
import { EventCreatorComponent } from './admin/dashboard/work-area/event-creator/event-creator.component';
import {RegistrationComponent} from './login/registration/registration.component';
import {NgxPayPalModule} from 'ngx-paypal';
import { PaypalPaymentExecutorComponent } from './paypal-payment-executor/paypal-payment-executor.component';
import { EventListComponent } from './admin/dashboard/work-area/event-list/event-list.component';
import { ShowListComponent } from './admin/dashboard/work-area/shows/show-list/show-list.component';
import { PersonalAreaComponent } from './personal-area/personal-area.component';
import { CashierComponent } from './cashier/cashier.component';
import { CashierPlantComponent } from './cashier/plant/cashier-plant/cashier-plant.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    WorkAreaComponent,
    ItemDirective,
    SimpleTextComponent,
    SimpleListComponent,
    ShowCreatorComponent,
    HomeComponent,
    CarouselComponent,
    FooterComponent,
    ShowDetailComponent,
    ErrorPageComponent,
    NavbarComponent,
    SeatReservationComponent,
    RoomCreatorComponent,
    ShowsSliderComponent,
    RoomsListComponent,
    SearchResultsComponent,
    ResultItemComponent,
    EventCreatorComponent,
    RegistrationComponent,
    PaypalPaymentExecutorComponent,
    EventListComponent,
    ShowListComponent,
    PersonalAreaComponent,
    CashierComponent,
    CashierPlantComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot([
      {path: 'personalArea', component: PersonalAreaComponent},
      {path: 'admin/dashboard', component: DashboardComponent},
      {path: 'home', component: HomeComponent},
      {path: 'login', component: LoginComponent},
      {path: 'show/:id', component: ShowDetailComponent},
      {path: 'seats', component: SeatReservationComponent},
      {path: 'cashier', component: CashierComponent},
      {path: '**', component: ErrorPageComponent},
      {path: 'error404', component: ErrorPageComponent},
    ]),
    ReactiveFormsModule,
    HttpClientModule,
    MenuModule,
    CommonModule,
    SidebarModule,
    GridModule,
    InPlaceEditorModule,
    DropDownListModule,
    FormsModule,
    ButtonModule,
    NumericTextBoxModule,
    DialogModule,
    DatePickerModule,
    TimePickerModule,
    ToastModule,
    TextBoxModule,
    UploaderModule,
    CheckBoxModule,
    MultiSelectModule,
    NgbModule,
    SlickCarouselModule,
    TabModule,
    SplitterModule,
    NgxPayPalModule
  ],
  providers: [CommandColumnService, EditService, PageService, ItemService, HomeComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
