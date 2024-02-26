import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { AuthorEditComponent } from '../author-edit/author-edit.component';
import { AuthorService } from '../author.service';
import { Author } from '../model/Author';

@Component({
selector: 'app-author-list',
templateUrl: './author-list.component.html',
styleUrls: ['./author-list.component.scss']
})
export class AuthorListComponent implements OnInit {

  // variables del componente mat-paginator de html
  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Author>();
  displayedColumns: string[] = ['id', 'name', 'nationality', 'action'];

  constructor(
    private authorService: AuthorService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.loadPage();
  }
   
  // Método para cargar cargar datos
  loadPage(event?: PageEvent) {

    // construcción de un objeto Pageable con valores actuales del componente paginador
    let pageable : Pageable =  {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [{
        property: 'id',
        direction: 'ASC'
      }]
    }

    if (event != null) {
        pageable.pageSize = event.pageSize
        pageable.pageNumber = event.pageIndex;
    }
    
    // lanza petición con datos de pageable en el body (si es mock no funcionará el cambio de página)
    this.authorService.getAuthors(pageable).subscribe(data => {
        this.dataSource.data = data.content;
        this.pageNumber = data.pageable.pageNumber;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
    });
  }  

  // Creación de Autor
  createAuthor() {      
    const dialogRef = this.dialog.open(AuthorEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });      
  }  

  // Editar
  editAuthor(author: Author) {    
    const dialogRef = this.dialog.open(AuthorEditComponent, {
      data: { author: author }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });    
   }

   //Eliminar
  deleteAuthor(author: Author) {    
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: { title: "Eliminar autor", description: "Atención si borra el autor se perderán sus datos.<br> ¿Desea eliminar el autor?" }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.authorService.deleteAuthor(author.id).subscribe(result =>  {
          this.ngOnInit();
        }); 
      }
    });
  }  
}