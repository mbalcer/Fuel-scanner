import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {DashboardComponent} from './dashboard/dashboard.component';
import {LoginPanelComponent} from './login-panel/login-panel.component';
import {HistoryComponent} from './dashboard/history/history.component';
import {ScannerComponent} from './dashboard/scanner/scanner.component';
import {NavigationComponent} from './dashboard/navigation/navigation.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from "./material-module";
import {CounterComponent} from './dashboard/counter/counter.component';
import {StatsComponent} from './dashboard/stats/stats.component';
import {InformationComponent} from './dashboard/information/information.component';
import {HomePageComponent} from './dashboard/home-page/home-page.component';
import {RegistrationComponent} from './registration/registration.component';
import {GoogleChartsModule} from "angular-google-charts";

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginPanelComponent,
    HistoryComponent,
    ScannerComponent,
    NavigationComponent,
    CounterComponent,
    StatsComponent,
    InformationComponent,
    HomePageComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    GoogleChartsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
