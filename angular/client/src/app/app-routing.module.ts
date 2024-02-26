// Componente ruoting de Angular

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryListComponent } from './category/category-list/category-list.component'; 
import { AuthorListComponent } from './author/author-list/author-list.component';
import { GameListComponent } from './game/game-list/game-list.component';
import { ClientListComponent } from './client/client-list/client-list.component';
import { LoanListComponent } from './loan/loan-list/loan-list.component';

const routes: Routes = [
  { path: '', redirectTo: '/games', pathMatch: 'full'},   // path vacío para indicar que si no pone ruta, por defecto la página inicial redirija al path /games (nuevo path que hemos añadido)
  { path: 'categories', component: CategoryListComponent },   // Ruta del componente category-list para poder acceder a él
  { path: 'authors', component: AuthorListComponent },  // Ruta en menú para poder acceder a la pantalla de Autores
  { path: 'games', component: GameListComponent },  // Ruta en menú para acceder/navegar a la pantalla de Juegos/Catálogo
  { path: 'clients', component: ClientListComponent },
  { path: 'loans', component: LoanListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


// Lazy Load: patron de diseño para retrasar la carga o inicialización
// {
//   path: 'customers',
//   loadChildren: () => import('./customers/customers.module').then(m => m.CustomersModule)
// },