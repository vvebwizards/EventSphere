import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ResourceManagementComponent } from './resource-management/resource-management.component';
import { EventsComponent } from './events/events.component';
import { MyBookingsComponent } from './my-bookings/my-bookings.component';
import { ReclamationsComponent } from './reclamations/reclamations.component';
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { UpdatePasswordComponent } from './auth/update-password/update-password.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ResourceDetailsComponent } from './resource-details/resource-details.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signUp', component: SignUpComponent },
  { path: 'resource-management', component: ResourceManagementComponent },
  { path: 'events', component: EventsComponent },
  { path: 'my-bookings', component: MyBookingsComponent },
  { path: 'reclamations', component: ReclamationsComponent },
  {path:'resourceDetails/:id',component:ResourceDetailsComponent},
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'update-password', component: UpdatePasswordComponent },
  { path: 'profile', component: UserProfileComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
