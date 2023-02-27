package com.example.CreditApplicationApp.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private CreditApplication creditApplication;
}
