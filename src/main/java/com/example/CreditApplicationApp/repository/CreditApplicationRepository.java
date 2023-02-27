package com.example.CreditApplicationApp.repository;

import com.example.CreditApplicationApp.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {

    CreditApplication getByPersonalIdentificationNumberAndBirthDate(String personalIdentificationNumber, LocalDate birthDate);
}
