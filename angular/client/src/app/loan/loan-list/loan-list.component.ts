import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { LoanEditComponent } from '../loan-edit/loan-edit.component';
import { LoanService } from '../loan.service';
import { GameService } from 'src/app/game/game.service';
import { ClientService } from 'src/app/client/client.service';
import { Loan } from '../model/Loan';
import { LoanFilter } from '../model/LoanFilter';
import { Client } from 'src/app/client/model/Client';
import { Game } from 'src/app/game/model/Game';

@Component({
  selector: 'app-loan-list',
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.scss']
})
export class LoanListComponent implements OnInit {
  // paginado
  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = ['id', 'title', 'name', 'startDate', 'endDate', 'action'];
  // filtrado
  clients: Client[];
  games: Game[];
  filterClient: Client;
  filterGame: Game;
  filterDate: Date;
  loanFilter: LoanFilter = {};

  constructor(
    private loanService: LoanService,
    public dialog: MatDialog,
    private gameService: GameService,
    private clientService: ClientService,
  ) { }

  ngOnInit(): void {

    this.gameService.getGames().subscribe(
      games => this.games = games
    );

    this.clientService.getClients().subscribe(
      clients => this.clients = clients
    );

    this.loadPage();
  }

  onCleanFilter(): void {
    this.filterGame = null;
    this.filterClient = null;
    this.filterDate = null;

    this.onSearch();
  }

  onSearch(): void {
    this.loadPage();
  }

  loadPage(event?: PageEvent) {
    let pageable: Pageable = {
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

    // Inicializamos las propiedades del objeto filters
    this.loanFilter = {
      game: this.filterGame?.id || undefined, // Utilizamos undefined si filterGame es null o undefined
      client: this.filterClient?.id || undefined, // Utilizamos undefined si filterCliente es null o undefined
      filteredDate: this.filterDate ? this.formatDate(this.filterDate) : undefined // Utilizamos undefined si filterDate es null
    };

    this.loanService.getLoans(pageable, this.loanFilter).subscribe(data => {
      this.dataSource.data = data.content;
      this.pageNumber = data.pageable.pageNumber;
      this.pageSize = data.pageable.pageSize;
      this.totalElements = data.totalElements;
    });

  }
  // Función para formatear la fecha
  formatDate(date: Date | null): string | undefined {
    return date ? `${date.getFullYear().toString()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}` : undefined;
  }

  createLoan() {
    const dialogRef = this.dialog.open(LoanEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  deleteLoan(loan: Loan) {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: { title: "Eliminar préstamo", description: "Atención si borra el préstamo se perderán sus datos.<br> ¿Desea eliminar el préstamo?" }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loanService.deleteLoan(loan.id).subscribe(result => {
          this.ngOnInit();
        });
      }
    });
  }
}
