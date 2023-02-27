package com.example.CreditApplicationApp;

import com.example.CreditApplicationApp.dto.CreditApplicationRequest;
import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.dto.CreditResult;
import com.example.CreditApplicationApp.entity.CreditApplication;
import com.example.CreditApplicationApp.entity.CreditApplicationResult;
import com.example.CreditApplicationApp.handler.CreditApplicationHandler;
import com.example.CreditApplicationApp.mapper.CreditDTOMapper;
import com.example.CreditApplicationApp.repository.CreditApplicationRepository;
import com.example.CreditApplicationApp.service.CreditApplicationService;
import com.example.CreditApplicationApp.service.CreditScoreService;
import com.example.CreditApplicationApp.service.SmsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditApplicationServiceTests {

    @InjectMocks
    CreditApplicationService creditApplicationService;
    @Mock
    CreditApplicationRepository creditApplicationRepository;
    @Mock
    CreditScoreService creditScoreService;
    @Mock
    CreditDTOMapper creditDTOMapper;
    @Mock
    SmsService smsService;

    @Test
    public void itShouldSaveCreditApplicationRequestAndReturnCreditDTO(){
        CreditApplicationRequest creditApplicationRequest = CreditApplicationRequest.builder()
                .personalIdentificationNumber("123")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(3000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(200))
                .build();
        CreditDTO creditDTO = CreditDTO.builder()
                .creditResult(CreditResult.APPROVED)
                .creditLimit(BigDecimal.valueOf(10000))
                .build();
        CreditApplicationResult creditApplicationResult = CreditApplicationResult.builder()
                .creditResult(creditDTO.getCreditResult())
                .creditLimit(creditDTO.getCreditLimit())
                .build();
        CreditApplication creditApplication = CreditApplication.builder()
                .id(1l)
                .personalIdentificationNumber(creditApplicationRequest.getPersonalIdentificationNumber())
                .fullName(creditApplicationRequest.getFullName())
                .monthlyIncome(creditApplicationRequest.getMonthlyIncome())
                .phone(creditApplicationRequest.getPhone())
                .birthDate(creditApplicationRequest.getBirthDate())
                .deposit(creditApplicationRequest.getDeposit())
                .creditApplicationResult(creditApplicationResult)
                .build();

        when(creditScoreService.getCreditScore(creditApplicationRequest.getPersonalIdentificationNumber())).thenReturn(750);
        when(creditApplicationRepository.save(any())).thenReturn(creditApplication);
        when(creditDTOMapper.convert(creditApplicationResult)).thenReturn(creditDTO);
        doNothing().when(smsService).sendSms(any());

        CreditDTO creditDTOResult = creditApplicationService.saveCreditApplication(creditApplicationRequest);

        assertEquals(creditDTOResult.getCreditResult(), CreditResult.APPROVED);
        assertEquals(creditDTOResult.getCreditLimit(), BigDecimal.valueOf(10000));
    }

    @Test
    public void itShouldGetCreditApplicationResult(){
        String personalIdentificationNumber = "123";
        LocalDate birthDate = LocalDate.now();
        CreditApplicationResult creditApplicationResult = CreditApplicationResult.builder()
                .creditResult(CreditResult.APPROVED)
                .creditLimit(BigDecimal.valueOf(10000))
                .build();
        CreditDTO creditDTO = CreditDTO.builder()
                .creditResult(CreditResult.APPROVED)
                .creditLimit(BigDecimal.valueOf(10000))
                .build();
        CreditApplication creditApplication = CreditApplication.builder()
                .id(1l)
                .personalIdentificationNumber("123")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(3000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(200))
                .creditApplicationResult(creditApplicationResult)
                .build();

        when(creditApplicationRepository.getByPersonalIdentificationNumberAndBirthDate(any(), any())).thenReturn(creditApplication);
        when(creditDTOMapper.convert(creditApplicationResult)).thenReturn(creditDTO);

        CreditDTO creditDTOResult = creditApplicationService.getCreditApplication(personalIdentificationNumber, birthDate);

        assertEquals(creditDTOResult.getCreditResult(), CreditResult.APPROVED);
        assertEquals(creditDTOResult.getCreditLimit(), BigDecimal.valueOf(10000));
    }

    @Test
    public void itShouldReturnNullIfPersonalIdentificationNumberOrBirthDateDoNotMatch(){
        String personalIdentificationNumber = "1234";
        LocalDate birthDate = LocalDate.now();
        CreditApplicationResult creditApplicationResult = CreditApplicationResult.builder()
                .creditResult(CreditResult.APPROVED)
                .creditLimit(BigDecimal.valueOf(10000))
                .build();
        CreditApplication creditApplication = CreditApplication.builder()
                .id(1l)
                .personalIdentificationNumber("123")
                .fullName("test")
                .monthlyIncome(BigDecimal.valueOf(3000))
                .phone("123")
                .birthDate(LocalDate.now())
                .deposit(BigDecimal.valueOf(200))
                .creditApplicationResult(creditApplicationResult)
                .build();

        when(creditApplicationRepository.getByPersonalIdentificationNumberAndBirthDate(any(), any())).thenReturn(null);

        CreditDTO creditDTOResult = creditApplicationService.getCreditApplication(personalIdentificationNumber, birthDate);

        assertEquals(creditDTOResult, null);
    }
}
