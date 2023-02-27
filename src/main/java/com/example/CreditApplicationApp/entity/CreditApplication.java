package com.example.CreditApplicationApp.entity;

import com.example.CreditApplicationApp.dto.CreditResult;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Indexed;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "credit_application", indexes = {
        @Index(
                name = "index",
                columnList = "personalIdentificationNumber, birthDate"
        )
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "creditApplicationResult")
public class CreditApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String personalIdentificationNumber;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private BigDecimal monthlyIncome;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column
    private BigDecimal deposit;
    @OneToOne(mappedBy = "creditApplication",cascade = CascadeType.ALL)
    private CreditApplicationResult creditApplicationResult;
    @OneToOne(mappedBy = "creditApplication", cascade = CascadeType.ALL)
    private Customer customer;
}
