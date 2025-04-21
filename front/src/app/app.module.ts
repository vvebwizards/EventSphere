import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ResourceManagementComponent } from './resource-management/resource-management.component';
import { FooterComponent } from './shared/components/footer/footer.component';

import { SignUpComponent } from './sign-up/sign-up.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { EventsComponent } from './events/events.component';
import { MyBookingsComponent } from './my-bookings/my-bookings.component';
import { ReclamationsComponent } from './reclamations/reclamations.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { AddResourceModalComponent } from './add-resource-modal/add-resource-modal.component';
import { ResourceCardComponent } from './shared/components/resource-card/resource-card.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { ResourceDetailsComponent } from './resource-details/resource-details.component';
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { UpdatePasswordComponent } from './auth/update-password/update-password.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    ResourceManagementComponent,
    FooterComponent,
    HeaderComponent,
    ResourceCardComponent,
    SignUpComponent,
    EventsComponent,
    MyBookingsComponent,
    ReclamationsComponent,

    UserProfileComponent,
    AddResourceModalComponent,
    ResourceDetailsComponent,
    ResetPasswordComponent,
    UpdatePasswordComponent,
  

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    FormsModule

  ],
  providers: [provideHttpClient(withInterceptorsFromDi())],
  bootstrap: [AppComponent]
})
export class AppModule { }
