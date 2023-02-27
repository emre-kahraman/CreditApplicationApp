package com.example.CreditApplicationApp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
public class CreditDTO {

    private CreditResult creditResult;
    private BigDecimal creditLimit;
}
