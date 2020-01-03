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

const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: '',
        component: HomePageComponent,
        outlet: 'panel'
      },
      {
        path: 'scanner',
        component: ScannerComponent,
        outlet: 'panel'
      },
      {
        path: 'counter',
        component: CounterComponent,
        outlet: 'panel'
      },
      {
        path: 'history',
        component: HistoryComponent,
        outlet: 'panel'
      },
      {
        path: 'stats',
        component: StatsComponent,
        outlet: 'panel'
      },
      {
        path: 'info',
        component: InformationComponent,
        outlet: 'panel'
      }
    ]
  },
  {
    path: '',
    component: LoginPanelComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
