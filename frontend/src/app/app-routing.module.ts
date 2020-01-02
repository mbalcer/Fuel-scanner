import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoginPanelComponent} from "./login-panel/login-panel.component";
import {ScannerComponent} from "./dashboard/scanner/scanner.component";
import {HistoryComponent} from "./dashboard/history/history.component";

const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'scanner',
        component: ScannerComponent,
        outlet: 'panel'
      },
      {
        path: 'history',
        component: HistoryComponent,
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
