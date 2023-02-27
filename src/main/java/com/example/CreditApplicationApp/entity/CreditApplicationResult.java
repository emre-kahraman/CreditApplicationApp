package com.example.CreditApplicationApp.entity;

import com.example.CreditApplicationApp.dto.CreditResult;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "credit_application_result")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreditApplicationResult {

    @Id
    private Long id;
    @OneToOne
    @MapsId
    private CreditApplication creditApplication;
    @Column(nullable = false)
    private CreditResult creditResult;
    private BigDecimal creditLimit;
}
