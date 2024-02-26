import { Pageable } from "src/app/core/model/page/Pageable";
import { Author } from "./Author";

export class AuthorPage {
    content: Author[];      // listado de los resultados paginados
    pageable: Pageable;     // Paginación
    totalElements: number;  // número total de elementos en la tabla
}