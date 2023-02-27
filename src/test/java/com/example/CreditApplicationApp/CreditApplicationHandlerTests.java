package com.example.CreditApplicationApp;

import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.dto.CreditApplicationRequest;
import com.example.CreditApplicationApp.dto.CreditResult;
import com.example.CreditApplicationApp.handler.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreditApplicationHandlerTests {

    CreditApplicationHandler creditApplicationHandler = CreditApplicationHandler.link(new ScoreLessThen500Handler(),
            new ScoreBetween500And1000IncomeLessThen5000Handler(),
            new ScoreBetween500And1000IncomeBetween5000And10000Handler(),
            new ScoreBetween500And1000IncomeHigherThen10000Handler(),
            new ScoreHigherThen1000Handler());;

    @Test
    public void itShouldReturnCreditResultRejectWhenCreditScoreLessThen500(){
        CreditApplicationRequest creditApplicationRequest = CreditApplicationRequest.builder().personalIdentificationNumber("123")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(3000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(200))
                .build();

        CreditDTO creditDTO = creditApplicationHandler.handle(Integer.valueOf(400), creditApplicationRequest);

        assertEquals(creditDTO.getCreditResult(), CreditResult.REJECTED);
        assertEquals(creditDTO.getCreditLimit(), BigDecimal.valueOf(0));
    }

    @Test
    public void itShouldReturnCreditResultApprovedWhenCreditScoreBetween500And1000IncomeLessThen5000(){
        CreditApplicationRequest creditApplicationRequest = CreditApplicationRequest.builder().personalIdentificationNumber("123")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(3000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(5000))
                .build();

        CreditDTO creditDTO = creditApplicationHandler.handle(Integer.valueOf(750), creditApplicationRequest);

        assertEquals(creditDTO.getCreditResult(), CreditResult.APPROVED);
        assertEquals(creditDTO.getCreditLimit(), BigDecimal.valueOf(10500));
    }

    @Test
    public void itShouldReturnCreditResultApprovedWhenCreditScoreBetween500And1000IncomeBetween5000And10000(){
        CreditApplicationRequest creditApplicationRequest = CreditApplicationRequest.builder().personalIdentificationNumber("123")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(6000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(5000))
                .build();

        CreditDTO creditDTO = creditApplicationHandler.handle(Integer.valueOf(750), creditApplicationRequest);

        assertEquals(creditDTO.getCreditResult(), CreditResult.APPROVED);
        assertEquals(creditDTO.getCreditLimit(), BigDecimal.valueOf(21000));
    }

    @Test
    public void itShouldReturnCreditResultApprovedWhenCreditScoreBetween500And1000IncomeHigherThen10000(){
        CreditApplicationRequest creditApplicationRequest = CreditApplicationRequest.builder().personalIdentificationNumber("123")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(15000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(5000))
                .build();

        CreditDTO creditDTO = creditApplicationHandler.handle(Integer.valueOf(750), creditApplicationRequest);

        assertEquals(creditDTO.getCreditResult(), CreditResult.APPROVED);
        assertEquals(creditDTO.getCreditLimit(), BigDecimal.valueOf(31250));
    }

    @Test
    public void itShouldReturnCreditResultApprovedWhenCreditScoreHigherThen1000(){
        CreditApplicationRequest creditApplicationRequest = CreditApplicationRequest.builder().personalIdentificationNumber("123")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(30000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(10000))
                .build();

        CreditDTO creditDTO = creditApplicationHandler.handle(Integer.valueOf(1500), creditApplicationRequest);

        assertEquals(creditDTO.getCreditResult(), CreditResult.APPROVED);
        assertEquals(creditDTO.getCreditLimit(), BigDecimal.valueOf(125000));
    }
}
