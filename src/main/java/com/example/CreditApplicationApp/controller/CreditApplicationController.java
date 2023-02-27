package com.example.CreditApplicationApp.controller;

import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.dto.CreditApplicationRequest;
import com.example.CreditApplicationApp.service.CreditApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/credits")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @PostMapping
    public ResponseEntity<CreditDTO> saveCreditApplication(@Valid @RequestBody CreditApplicationRequest creditApplicationRequest){
        return new ResponseEntity<>(creditApplicationService.saveCreditApplication(creditApplicationRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{personalIdentificationNumber}/{birthDate}")
    public ResponseEntity<CreditDTO> getCreditApplication(@Valid @PathVariable String personalIdentificationNumber, @Valid @PathVariable LocalDate birthDate){
        return new ResponseEntity<>(creditApplicationService.getCreditApplication(personalIdentificationNumber, birthDate), HttpStatus.OK);
    }
}
