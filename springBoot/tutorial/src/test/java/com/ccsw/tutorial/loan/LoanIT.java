package com.ccsw.tutorial.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanFilterDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    public static final Long EXISTS_LOAN_ID = 2L;
    public static final Long NOT_EXISTS_LOAN_ID = 0L;
    public static final Long DELETE_LOAN_ID = 6L;

    private static final Long EXISTS_GAME = 1L;
    private static final Long NOT_EXISTS_GAME = 0L;

    private static final Long EXISTS_CLIENT = 3L;
    private static final Long NOT_EXISTS_CLIENT = 0L;

    private static final LocalDate EXISTS_DATE = LocalDate.of(2024, 1, 15);
    private static final LocalDate NOT_EXISTS_DATE = LocalDate.of(2001, 2, 12);
    public static final LocalDate START_DATE = LocalDate.parse("2024-03-03");
    public static final LocalDate END_DATE = LocalDate.parse("2024-03-09");

    private static final int TOTAL_LOANS = 8;
    private static final int PAGE_SIZE = 5;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {};

    @Test
    public void findWithoutFiltersShouldReturnAllLoans() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE)); // Solicitamos solo la primera página, pero sin
                                                                  // filtros

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(PAGE_SIZE, response.getBody().getSize());
    }

    @Test
    public void findExistsClientShouldReturnLoans() {

        int LOANS_WITH_CLIENT_3 = 2;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        LoanFilterDto loanFilterDto = new LoanFilterDto();
        loanFilterDto.setClient(EXISTS_CLIENT);

        searchDto.setLoanFilterDto(loanFilterDto);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_CLIENT_3, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsClientShouldReturnEmpty() {

        int LOANS_WITH_CLIENT_6 = 0;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        LoanFilterDto loanFilterDto = new LoanFilterDto();
        loanFilterDto.setClient(NOT_EXISTS_CLIENT);

        searchDto.setLoanFilterDto(loanFilterDto);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_CLIENT_6, response.getBody().getContent().size());
    }

    @Test
    public void findExistsGameShouldReturnLoans() {

        int LOANS_WITH_GAME_1 = 3;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        LoanFilterDto loanFilterDto = new LoanFilterDto();
        loanFilterDto.setGame(EXISTS_GAME);

        searchDto.setLoanFilterDto(loanFilterDto);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_GAME_1, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsGameShouldReturnEmpty() {

        int LOANS_WITH_GAME_7 = 0;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        LoanFilterDto loanFilterDto = new LoanFilterDto();
        loanFilterDto.setGame(NOT_EXISTS_GAME);

        searchDto.setLoanFilterDto(loanFilterDto);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_GAME_7, response.getBody().getContent().size());
    }

    @Test
    public void findExistsDateShouldReturnLoans() {

        int LOANS_WITH_DATE = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        LoanFilterDto loanFilter = new LoanFilterDto();
        loanFilter.setFilteredDate(EXISTS_DATE);

        searchDto.setLoanFilterDto(loanFilter);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_DATE, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsDateShouldReturnEmpty() {

        int LOANS_WITH_DATEE = 0;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        LoanFilterDto loanFilter = new LoanFilterDto();
        loanFilter.setFilteredDate(NOT_EXISTS_DATE);

        searchDto.setLoanFilterDto(loanFilter);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_DATEE, response.getBody().getContent().size());
    }

    @Test
    public void saveLoanShouldCreateNew() {

        LoanDto loanDto = new LoanDto();
        ClientDto clientDto = new ClientDto();
        GameDto gameDto = new GameDto();

        clientDto.setId(1L);
        loanDto.setClient(clientDto);

        gameDto.setId(4L);
        loanDto.setGame(gameDto);

        loanDto.setStartDate(START_DATE);
        loanDto.setEndDate(END_DATE);

        long newLoanSize = TOTAL_LOANS + 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(loanDto), Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, (int) newLoanSize));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());
    }

    public static final LocalDate END_DATE_BEFORE_START = LocalDate.parse("2024-03-01");
    @Test
    public void saveEndDateBeforeStartDateShouldThrowException() {
        LoanDto loanDto = new LoanDto();
        ClientDto clientDto = new ClientDto();
        GameDto gameDto = new GameDto();

        clientDto.setId(EXISTS_CLIENT);
        loanDto.setClient(clientDto);

        gameDto.setId(EXISTS_GAME);
        loanDto.setGame(gameDto);

        loanDto.setStartDate(START_DATE);
        loanDto.setEndDate(END_DATE_BEFORE_START);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    public static final LocalDate END_DATE_MORE_THAN_14 = LocalDate.parse("2024-03-30");
    @Test
    public void saveWithLoanPeriodMoreThan14ShouldThrowException() {
            LoanDto loanDto = new LoanDto();
            ClientDto clientDto = new ClientDto();
            GameDto gameDto = new GameDto();
    
            clientDto.setId(EXISTS_CLIENT);
            loanDto.setClient(clientDto);
    
            gameDto.setId(EXISTS_GAME);
            loanDto.setGame(gameDto);
    
            loanDto.setStartDate(START_DATE);
            loanDto.setEndDate(END_DATE_MORE_THAN_14);
    
            ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                    new HttpEntity<>(loanDto), Void.class);
    
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    public static final LocalDate START_DATE_G1 = LocalDate.parse("2024-01-15");
    public static final LocalDate END_DATE_G1 = LocalDate.parse("2024-01-25");
    @Test
    public void saveWith2ClientsSameGameShouldThrowException() {
        LoanDto loanDto = new LoanDto();
        ClientDto clientDto = new ClientDto();
        GameDto gameDto = new GameDto();
        
        clientDto.setId(5L);
        loanDto.setClient(clientDto);

        gameDto.setId(EXISTS_GAME);
        loanDto.setGame(gameDto);
        
        loanDto.setStartDate(START_DATE_G1);
        loanDto.setEndDate(END_DATE_G1);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    public static final LocalDate START_DATE_G5 = LocalDate.parse("2024-01-28");
    public static final LocalDate END_DATE_G5 = LocalDate.parse("2024-01-31");
    @Test
    public void saveWith2GamesSameDayShouldThrowException() {
        LoanDto loanDto = new LoanDto();
        ClientDto clientDto = new ClientDto();
        GameDto gameDto = new GameDto();
        
        clientDto.setId(EXISTS_CLIENT);
        loanDto.setClient(clientDto);

        gameDto.setId(5L);
        loanDto.setGame(gameDto);
        
        loanDto.setStartDate(START_DATE_G5);
        loanDto.setEndDate(END_DATE_G5);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteCategory() {

        long newLoansSize = TOTAL_LOANS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_LOAN_ID, HttpMethod.DELETE, null,
                Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoansSize, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowException() {

        long deleteLoanId = TOTAL_LOANS + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + deleteLoanId,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
