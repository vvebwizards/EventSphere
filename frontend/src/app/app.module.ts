import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ResourceManagementComponent } from './resource-management/resource-management.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ResourceCardComponent } from './shared/components/resource-card/resource-card.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ReclamationsComponent } from './reclamations/reclamations.component';
import { MyBookingsComponent } from './my-bookings/my-bookings.component';
import { EventsComponent } from './events/events.component';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    ResourceManagementComponent,
    FooterComponent,
    HeaderComponent,
    SignUpComponent,
    ResourceCardComponent,
    ReclamationsComponent,
    MyBookingsComponent,
    EventsComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
