package com.example.CreditApplicationApp.handler;

import com.example.CreditApplicationApp.dto.CreditDTO;
import com.example.CreditApplicationApp.dto.CreditApplicationRequest;

public abstract class CreditApplicationHandler {

    private CreditApplicationHandler next;
    protected static final Integer creditLimitMultiplier = 4;

    public static CreditApplicationHandler link(CreditApplicationHandler first, CreditApplicationHandler... chain){
        CreditApplicationHandler head = first;
        for(CreditApplicationHandler creditApplicationHandler : chain){
            head.next = creditApplicationHandler;
            head = creditApplicationHandler;
        }
        return first;
    }

    public abstract CreditDTO handle(Integer creditScore, CreditApplicationRequest creditApplicationRequest);

    protected CreditDTO checkNext(Integer creditScore, CreditApplicationRequest creditApplicationRequest){
        if(next == null)
            return null;
        return next.handle(creditScore, creditApplicationRequest);
    }
}
