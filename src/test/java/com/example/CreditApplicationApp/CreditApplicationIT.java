package com.example.CreditApplicationApp;

import com.example.CreditApplicationApp.dto.CreditApplicationRequest;
import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.dto.CreditResult;
import com.example.CreditApplicationApp.exception.ApiError;
import com.example.CreditApplicationApp.service.CreditApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditApplicationIT {

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    CreditApplicationService creditApplicationService;
    @LocalServerPort
    int port;

    @Test
    public void itShouldSaveCreditApplicationAndReturnCreditApplicationResult(){
        CreditApplicationRequest creditApplicationRequest = CreditApplicationRequest.builder()
                .personalIdentificationNumber("25123")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(3000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(200))
                .build();

        String uri = "http://localhost:"+port+"/api/credits";

        ResponseEntity<CreditDTO> responseEntity = restTemplate.postForEntity(uri, creditApplicationRequest, CreditDTO.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseEntity.getBody().getCreditResult(), CreditResult.APPROVED);
        assertEquals(responseEntity.getBody().getCreditLimit(), BigDecimal.valueOf(10020));
    }

    @Test
    public void itShouldReturnCreditApplicationResult(){
        CreditApplicationRequest creditApplicationRequest = CreditApplicationRequest.builder()
                .personalIdentificationNumber("251234")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(3000))
                .phone("1234")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(200))
                .build();

        creditApplicationService.saveCreditApplication(creditApplicationRequest);

        String personalIdentificationNumber = creditApplicationRequest.getPersonalIdentificationNumber();
        LocalDate birthDate = creditApplicationRequest.getBirthDate();

        String uri = "http://localhost:"+port+"/api/credits/"+personalIdentificationNumber+"/"+birthDate;

        ResponseEntity<CreditDTO> responseEntity = restTemplate.getForEntity(uri, CreditDTO.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getCreditResult(), CreditResult.APPROVED);
        assertEquals(responseEntity.getBody().getCreditLimit(), BigDecimal.valueOf(10020).setScale(2));
    }

    @Test
    public void itShouldReturnCreditApplicationNotFound(){

        String personalIdentificationNumber = "123";
        LocalDate birthDate = LocalDate.now();

        String uri = "http://localhost:"+port+"/api/credits/"+personalIdentificationNumber+"/"+birthDate;

        ResponseEntity<ApiError> responseEntity = restTemplate.getForEntity(uri, ApiError.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(responseEntity.getBody().getHttpStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void itShouldReturnRequestMethodNotSupported(){

        String uri = "http://localhost:"+port+"/api/credits";

        ResponseEntity<ApiError> responseEntity = restTemplate.getForEntity(uri, ApiError.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
        assertEquals(responseEntity.getBody().getHttpStatus(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    public void itShouldReturnMethodArgumentNotValid(){

        CreditApplicationRequest creditApplicationRequest = CreditApplicationRequest.builder()
                .personalIdentificationNumber(null)
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(3000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(200))
                .build();

        String uri = "http://localhost:"+port+"/api/credits";

        ResponseEntity<ApiError> responseEntity = restTemplate.postForEntity(uri,creditApplicationRequest, ApiError.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getHttpStatus(), HttpStatus.BAD_REQUEST);
    }
}
