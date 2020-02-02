import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoginPanelComponent} from "./login-panel/login-panel.component";
import {ScannerComponent} from "./dashboard/scanner/scanner.component";
import {HistoryComponent} from "./dashboard/history/history.component";
import {CounterComponent} from "./dashboard/counter/counter.component";
import {StatsComponent} from "./dashboard/stats/stats.component";
import {InformationComponent} from "./dashboard/information/information.component";
import {HomePageComponent} from "./dashboard/home-page/home-page.component";
import { RegistrationComponent } from "./registration/registration.component";
import { AuthGaurdService } from './service/auth-gaurd.service';

const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: '',
        component: HomePageComponent,
        outlet: 'panel',
        canActivate:[AuthGaurdService]
      },
      {
        path: 'scanner',
        component: ScannerComponent,
        outlet: 'panel',
        canActivate:[AuthGaurdService]
      },
      {
        path: 'counter',
        component: CounterComponent,
        outlet: 'panel',
        canActivate:[AuthGaurdService]
      },
      {
        path: 'history',
        component: HistoryComponent,
        outlet: 'panel',
        canActivate:[AuthGaurdService]
      },
      {
        path: 'stats',
        component: StatsComponent,
        outlet: 'panel',
        canActivate:[AuthGaurdService]
      },
      {
        path: 'info',
        component: InformationComponent,
        outlet: 'panel',
        canActivate:[AuthGaurdService]
      }
    ]
  },
  {
    path: 'login',
    component: LoginPanelComponent,
  },
  {
    path: 'registration',
    component: RegistrationComponent,
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
   }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
