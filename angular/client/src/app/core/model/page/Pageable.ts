// Objeto de paginación común a toda la app

import { SortPage } from './SortPage';

export class Pageable {
    pageNumber: number;     // número de página empezando por 0
    pageSize: number;       // tamaño página
    sort: SortPage[];
}