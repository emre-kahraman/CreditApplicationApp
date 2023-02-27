package com.example.CreditApplicationApp.handler;

import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.dto.CreditApplicationRequest;
import com.example.CreditApplicationApp.dto.CreditResult;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class ScoreLessThen500Handler extends CreditApplicationHandler {
    @Override
    public CreditDTO handle(Integer creditScore, CreditApplicationRequest creditApplicationRequest) {
        if(creditScore<500) {
            CreditDTO creditDTO = CreditDTO.builder()
                    .creditResult(CreditResult.REJECTED)
                    .creditLimit(BigDecimal.valueOf(0))
                    .build();
            log.info("CreditApplicationRequest {} handled by {} with result {}"
                    , creditApplicationRequest
                    , this.getClass()
                    , creditDTO);
            return creditDTO;
        }
        return checkNext(creditScore, creditApplicationRequest);
    }
}
