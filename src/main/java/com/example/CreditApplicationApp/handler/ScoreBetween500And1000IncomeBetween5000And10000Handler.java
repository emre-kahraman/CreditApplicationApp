package com.example.CreditApplicationApp.handler;

import com.example.CreditApplicationApp.dto.CreditResult;
import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.dto.CreditApplicationRequest;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class ScoreBetween500And1000IncomeBetween5000And10000Handler extends CreditApplicationHandler {
    @Override
    public CreditDTO handle(Integer creditScore, CreditApplicationRequest creditApplicationRequest) {
        if(creditScore>=500
                && creditScore<1000
                && creditApplicationRequest.getMonthlyIncome().compareTo(BigDecimal.valueOf(5000))>=0
                && creditApplicationRequest.getMonthlyIncome().compareTo(BigDecimal.valueOf(10000))<=0) {
            CreditDTO creditDTO = CreditDTO.builder()
                    .creditResult(CreditResult.APPROVED)
                    .creditLimit(BigDecimal.valueOf(20000))
                    .build();
            if (creditApplicationRequest.getDeposit() != null)
                creditDTO.setCreditLimit(creditDTO.getCreditLimit()
                        .add(creditApplicationRequest.getDeposit().
                                divide(BigDecimal.valueOf(5))));
            log.info("CreditApplicationRequest {} handled by {} with result {}"
                    , creditApplicationRequest
                    , this.getClass()
                    , creditDTO);
            return creditDTO;
        }
        return checkNext(creditScore, creditApplicationRequest);
    }
}
