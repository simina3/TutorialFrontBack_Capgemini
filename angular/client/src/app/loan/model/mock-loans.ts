import { LoanPage } from "./LoanPage";

export const LOAN_DATA: LoanPage = {
    content: [
        { 
            id: 1, 
            game: {id: 1, title: 'Juego 1', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' }}, 
            client: {id: 1, name: 'Cliente 1'}, 
            startDate: new Date(),
            endDate: new Date() 
        },
        { 
            id: 2, 
            game: {id: 2, title: 'Juego 2', age: 7, category: { id: 2, name: 'Categoría 2' }, author: { id: 2, name: 'Autor 2', nationality: 'Nacionalidad 2' }}, 
            client: {id: 2, name: 'Cliente 2'}, 
            startDate: new Date(),
            endDate: new Date() 
        },
    ],  
    pageable : {
        pageSize: 5,
        pageNumber: 0,
        sort: [
            {property: "id", direction: "ASC"}
        ]
    },
    totalElements: 7
}
