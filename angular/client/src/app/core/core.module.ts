import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';         // dependencias de 
import { MatToolbarModule } from '@angular/material/toolbar';   // header.component.html y .scss
import { HeaderComponent } from './header/header.component';
import { RouterModule } from '@angular/router';
import { DialogConfirmationComponent } from './dialog-confirmation/dialog-confirmation.component';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [
    HeaderComponent,
    DialogConfirmationComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule, 
    MatToolbarModule,
    // añadimos componentes utilizados en diálogo de confirmación (ya que este es el nodo padre al que pertenecen)
    MatDialogModule,
    MatButtonModule,
  ], 
  providers: [
    {
      provide: MAT_DIALOG_DATA,
      useValue: {},
    },
  ],
  exports: [
    HeaderComponent     // Exporta este componente para poder utilizarlo desde otras páginas
  ]
})
export class CoreModule { }
