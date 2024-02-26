// Servicio por el cual debe pasar cualquier acceso a datos para todas las operaciones de categorías
// También podemos utilizarlo inyectándolo en cualquier componente que lo necesite.

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Category } from './model/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private http: HttpClient
  ) { }

  // Operación de negocio que recupera el listado de categorías de forma reactiva (asíncrona) simulando una petición a backend
  getCategories(): Observable<Category[]> {
    // return new Observable();
    //return of(CATEGORY_DATA);   
    return this.http.get<Category[]>('http://localhost:8080/category'); // sustituimos el código que devuelve datos estáticos por una llamada http al servidor
  }

  // Editar/guardar
  saveCategory(category: Category): Observable<Category> {
    let url = 'http://localhost:8080/category';
        if (category.id != null) url += '/'+category.id;

        return this.http.put<Category>(url, category);
  }

  // Eliminar
  deleteCategory(idCategory : number): Observable<any> {
    return this.http.delete('http://localhost:8080/category/'+idCategory);
  }
}
