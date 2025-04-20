import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ResourceManagementComponent } from './resource-management/resource-management.component';
import { EventsComponent } from './events/events.component';
import { ReclamationsComponent } from './reclamations/reclamations.component';
import { MyBookingsComponent } from './my-bookings/my-bookings.component';
const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signUp', component: SignUpComponent },
  { path: 'resource-management', component: ResourceManagementComponent },
  { path: 'events', component: EventsComponent },
  { path: 'my-bookings', component: MyBookingsComponent },
  { path: 'reclamations', component: ReclamationsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
