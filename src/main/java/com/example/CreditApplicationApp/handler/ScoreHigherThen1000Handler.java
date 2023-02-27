package com.example.CreditApplicationApp.handler;

import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.dto.CreditApplicationRequest;
import com.example.CreditApplicationApp.dto.CreditResult;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class ScoreHigherThen1000Handler extends CreditApplicationHandler {
    @Override
    public CreditDTO handle(Integer creditScore, CreditApplicationRequest creditApplicationRequest) {
        if(creditScore>=1000) {
            CreditDTO creditDTO = CreditDTO.builder()
                    .creditResult(CreditResult.APPROVED)
                    .creditLimit(creditApplicationRequest.getMonthlyIncome().multiply(BigDecimal.valueOf(creditLimitMultiplier)))
                    .build();
            if (creditApplicationRequest.getDeposit() != null)
                creditDTO.setCreditLimit(creditDTO.getCreditLimit()
                        .add(creditApplicationRequest.getDeposit()
                                .divide(BigDecimal.valueOf(2))));
            log.info("CreditApplicationRequest {} handled by {} with result {}"
                    , creditApplicationRequest
                    , this.getClass()
                    , creditDTO);
            return creditDTO;
        }
        return checkNext(creditScore, creditApplicationRequest);
    }
}
