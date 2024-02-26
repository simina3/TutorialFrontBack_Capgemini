import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreModule } from './core/core.module';  // Módulo general de la aplicación
import { CategoryModule } from './category/category.module'; // Nuevo módulo que contiene toda la funcionalidad del dominio Categorías (listado simple)
import { AuthorModule } from './author/author.module'; // Módulo que contiene funcionalidad de Autor (listado paginado) -> añadimos a la app para que se cargue en arranque
import { GameModule } from './game/game.module'; // Módulo que contiene funcionalidad de Juego (listado filtrado) -> añadimos a la app para que se cargue en arranque
import { ClientModule } from './client/client.module';
import { LoanModule } from './loan/loan.module';
import { MatDialogModule } from '@angular/material/dialog';
import { MatNativeDateModule } from '@angular/material/core';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    CategoryModule,
    AuthorModule,
    GameModule,
    ClientModule,
    LoanModule,
    MatDialogModule,
    MatNativeDateModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
