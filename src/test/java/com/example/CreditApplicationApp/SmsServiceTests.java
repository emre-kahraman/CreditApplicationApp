package com.example.CreditApplicationApp;

import com.example.CreditApplicationApp.dto.CreditResult;
import com.example.CreditApplicationApp.dto.SendSmsRequest;
import com.example.CreditApplicationApp.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class SmsServiceTests {

    @InjectMocks
    SmsService smsService;

    @Mock
    Message message;

    @Value("${twilio.id}")
    private String twilioId;
    @Value("${twilio.password}")
    private String twilioPassword;

    @BeforeAll
    public static void setUp(){

    }

    @Test
    public void itShouldSendSms(){
        Twilio.init(twilioId, twilioPassword);

        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phone("test")
                .creditResult(CreditResult.APPROVED)
                .creditLimit(BigDecimal.valueOf(10000))
                .build();

        smsService.sendSms(sendSmsRequest);
    }
}
