package com.example.CreditApplicationApp.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class SendSmsRequest {

    private String phone;
    private CreditResult creditResult;
    private BigDecimal creditLimit;
}
