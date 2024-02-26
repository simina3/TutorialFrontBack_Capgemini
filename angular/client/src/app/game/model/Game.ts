// Modelo de Juego y sus propiedades correspondientes
// tiene 2 objetos para mapear categoría y autor

import { Category } from "src/app/category/model/Category";
import { Author } from "src/app/author/model/Author";

export class Game {
    id: number;
    title: string;
    age: number;
    category: Category;
    author: Author;
}
