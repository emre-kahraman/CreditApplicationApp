package com.example.CreditApplicationApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@ToString
public class CreditApplicationRequest {

    @NotNull
    private String personalIdentificationNumber;
    @NotNull
    private String fullName;
    @NotNull
    private BigDecimal monthlyIncome;
    @NotNull
    private String phone;
    @NotNull
    private LocalDate birthDate;
    private BigDecimal deposit;
}
