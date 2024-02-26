// Servicio por el cual debe pasar cualquier acceso a datos para todas las operaciones de Autores

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Pageable } from '../core/model/page/Pageable';
import { Author } from './model/Author';
import { AuthorPage } from './model/AuthorPage';
//import { AUTHOR_DATA_LIST } from './model/mock-authors-list';
//import { AUTHOR_DATA } from './model/mock-authors';

@Injectable({
    providedIn: 'root'
})
export class AuthorService {

    constructor(
        private http: HttpClient
    ) { }

    // Operación de negocio que recupera el listado de Autores según la página que se desee (formato Page)
    getAuthors(pageable: Pageable): Observable<AuthorPage> {
        //return of(AUTHOR_DATA);
        return this.http.post<AuthorPage>('http://localhost:8080/author', {pageable:pageable});
    }

    // Editar/guardar
    saveAuthor(author: Author): Observable<void> {
        let url = 'http://localhost:8080/author';
        if (author.id != null) url += '/'+author.id;

        return this.http.put<void>(url, author);
    }

    // Eliminar
    deleteAuthor(idAuthor : number): Observable<void> {
        return this.http.delete<void>('http://localhost:8080/author/'+idAuthor);
    }    

    // Recuperar lista de Autores (normal)
    getAllAuthors(): Observable<Author[]> {
        //return of(AUTHOR_DATA_LIST);
        return this.http.get<Author[]>('http://localhost:8080/author');
    }
}
