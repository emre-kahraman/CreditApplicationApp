package com.example.CreditApplicationApp.mapper;

import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.entity.CreditApplicationResult;
import org.springframework.stereotype.Component;

@Component
public class CreditDTOMapper {

    public CreditDTO convert(CreditApplicationResult creditApplicationResult){
        return CreditDTO.builder()
                .creditResult(creditApplicationResult.getCreditResult())
                .creditLimit(creditApplicationResult.getCreditLimit())
                .build();
    }
}
