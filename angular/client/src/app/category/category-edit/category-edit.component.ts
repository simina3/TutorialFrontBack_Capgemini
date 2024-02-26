import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategoryService } from '../category.service';
import { Category } from '../model/Category';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.scss']
})
export class CategoryEditComponent implements OnInit {

  category : Category;

  constructor(
    public dialogRef: MatDialogRef<CategoryEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    if (this.data.category != null) {
      // this.category = this.data.category; -> Mismo objeto desde listado a ventana de dialogo por lo que cualquier cambio sobre los datos se refresca directamente en pantalla
      this.category = Object.assign({}, this.data.category); // copia del objeto para que tanto el formulario como el listado utilicen objetos distintos
    }
    else {
      this.category = new Category();
    }
  }

  onSave() {
    // llamamos al servicio de CategoryService para conectar con el servidor cuando lo necesitemos
    this.categoryService.saveCategory(this.category).subscribe(result => {
      this.dialogRef.close();
    });    
  }  

  onClose() {
    this.dialogRef.close();
  }

}