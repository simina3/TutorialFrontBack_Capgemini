import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Category } from '../model/Category';
import { CategoryService } from '../category.service';
import { MatDialog } from '@angular/material/dialog';
import { CategoryEditComponent } from '../category-edit/category-edit.component';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss']
})
export class CategoryListComponent {
  dataSource = new MatTableDataSource<Category>();
  displayedColumns: string[] = ['id', 'name', 'action'];

  constructor(
    private categoryService: CategoryService,
    public dialog: MatDialog, // Para poder abrir el componente dentro del diálogo obtenemos en el constructor un MatDialog
  ) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(
      categories => this.dataSource.data = categories
    );
  }

  // Método para abrir componente category-edit.component al pulsar el botón Nueva categoría
  createCategory() {    
    // creamos diálogo con componente CatgoryEdit..., le pasamos unos datos de creación poniendo estilos de dialog y objeto data 
    const dialogRef = this.dialog.open(CategoryEditComponent, {   
      data: {}  // Datos que queremos pasar entre componentes.
    });

    // nos suscribimos al evento afterClosed para ejecutar las acciones que creamos oportunas -> volver a cargar el listado inicial
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });    
  }

  // Método para abrir componente category-edit.component al pulsar el botón Editar
  editCategory(category: Category) {
    const dialogRef = this.dialog.open(CategoryEditComponent, {
      data: { category: category }  // le pasamos la categoría que queremos editar
    });

    dialogRef.afterClosed().subscribe(result => { // cargar el listado 
      this.ngOnInit();
    });
  }

  // Método para abrir componente dialog-confirmation.component al pulsar el botón Eliminar
  deleteCategory(category: Category) {    
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: { title: "Eliminar categoría", description: "Atención si borra la categoría se perderán sus datos.<br> ¿Desea eliminar la categoría?" }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.categoryService.deleteCategory(category.id).subscribe(result => {  // llamada a categoryService que utilizaremos más adelante para enlazarlo
          this.ngOnInit();
        }); 
      }
    });
  }
}
