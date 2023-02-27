package com.example.CreditApplicationApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreditScoreService {

    public Integer getCreditScore(String personalIdentificationNumber){
        Integer creditScore = Integer.valueOf(personalIdentificationNumber.substring(0,2));
        log.info("creditScore {} fetched by Customer with personalIdentificationNumber: {}"
                , creditScore
                , personalIdentificationNumber);
        return creditScore*20;
    }
}
