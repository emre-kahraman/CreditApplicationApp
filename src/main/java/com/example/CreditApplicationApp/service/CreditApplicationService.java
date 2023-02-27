package com.example.CreditApplicationApp.service;

import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.dto.CreditApplicationRequest;
import com.example.CreditApplicationApp.dto.SendSmsRequest;
import com.example.CreditApplicationApp.entity.CreditApplication;
import com.example.CreditApplicationApp.entity.CreditApplicationResult;
import com.example.CreditApplicationApp.entity.Customer;
import com.example.CreditApplicationApp.handler.*;
import com.example.CreditApplicationApp.mapper.CreditDTOMapper;
import com.example.CreditApplicationApp.repository.CreditApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class CreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;
    private final CreditScoreService creditScoreService;
    private final SmsService smsService;
    private final CreditDTOMapper creditDTOMapper;
    private CreditApplicationHandler creditApplicationHandler;

    public CreditApplicationService(CreditApplicationRepository creditApplicationRepository, CreditScoreService creditScoreService, SmsService smsService, CreditDTOMapper creditDTOMapper) {
        this.creditApplicationRepository = creditApplicationRepository;
        this.creditScoreService = creditScoreService;
        this.smsService = smsService;
        this.creditDTOMapper = creditDTOMapper;
        creditApplicationHandler = CreditApplicationHandler.link(new ScoreLessThen500Handler(),
                new ScoreBetween500And1000IncomeLessThen5000Handler(),
                new ScoreBetween500And1000IncomeBetween5000And10000Handler(),
                new ScoreBetween500And1000IncomeHigherThen10000Handler(),
                new ScoreHigherThen1000Handler());
    }

    public CreditDTO saveCreditApplication(CreditApplicationRequest creditApplicationRequest) {
        log.info("CreditApplicationRequest: {} received", creditApplicationRequest);
        Integer creditScore = creditScoreService.getCreditScore(creditApplicationRequest.getPersonalIdentificationNumber());
        CreditDTO creditDTO = creditApplicationHandler.handle(creditScore, creditApplicationRequest);
        Customer customer = new Customer();
        CreditApplicationResult creditApplicationResult = CreditApplicationResult.builder()
                .creditResult(creditDTO.getCreditResult())
                .creditLimit(creditDTO.getCreditLimit())
                .build();
        CreditApplication creditApplication = CreditApplication.builder()
                .personalIdentificationNumber(creditApplicationRequest.getPersonalIdentificationNumber())
                .fullName(creditApplicationRequest.getFullName())
                .monthlyIncome(creditApplicationRequest.getMonthlyIncome())
                .phone(creditApplicationRequest.getPhone())
                .birthDate(creditApplicationRequest.getBirthDate())
                .deposit(creditApplicationRequest.getDeposit())
                .creditApplicationResult(creditApplicationResult)
                .customer(customer)
                .build();
        creditApplicationResult.setCreditApplication(creditApplication);
        CreditApplication savedCreditApplication = creditApplicationRepository.save(creditApplication);
        log.info("Credit Application: {} saved to database", savedCreditApplication);
        CreditApplicationResult savedCreditApplicationResult = savedCreditApplication.getCreditApplicationResult();
        log.info("CreditApplicationResult: {} saved to database", savedCreditApplicationResult);
        smsService.sendSms(SendSmsRequest.builder()
                .phone(savedCreditApplication.getPhone())
                .creditResult(savedCreditApplicationResult.getCreditResult())
                .creditLimit(savedCreditApplicationResult.getCreditLimit())
                .build());
        return creditDTOMapper.convert(savedCreditApplicationResult);
    }

    public CreditDTO getCreditApplication(String personalIdentificationNumber, LocalDate birthDate) {
        log.info("CreditApplicationInquiry Request with personalIdentificationNumber {} birthDate {}",personalIdentificationNumber, birthDate);
        CreditApplication creditApplication = creditApplicationRepository.getByPersonalIdentificationNumberAndBirthDate(personalIdentificationNumber, birthDate);
        if(creditApplication == null)
            return null;
        CreditApplicationResult creditApplicationResult = creditApplication.getCreditApplicationResult();
        log.info("CreditApplicationResult {} fetched from database with personalIdentificationNumber {} birthDate {}"
                , creditApplicationResult
                , personalIdentificationNumber
                , birthDate);
        return creditDTOMapper.convert(creditApplicationResult);
    }
}
