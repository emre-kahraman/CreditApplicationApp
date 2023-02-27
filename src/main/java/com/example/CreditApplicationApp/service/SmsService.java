package com.example.CreditApplicationApp.service;

import com.example.CreditApplicationApp.dto.SendSmsRequest;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    @Value("${twilio.id}")
    private String twilioId;
    @Value("${twilio.password}")
    private String twilioPassword;

    @PostConstruct
    public void setUpTwilio(){
        Twilio.init(twilioId, twilioPassword);
    }

    public void sendSms(SendSmsRequest sendSmsRequest){
        try {
            Message message = Message.creator(new PhoneNumber(sendSmsRequest.getPhone())
                    , new PhoneNumber("test")
                    , ("Credit Result: " + sendSmsRequest.getCreditResult() + " Credit Limit: " + sendSmsRequest.getCreditLimit())).create();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        ListenableFuture<ResourceSet<Message>> future = Message.reader().readAsync();
        Futures.addCallback(
                future,
                new FutureCallback<ResourceSet<Message>>() {
                    public void onSuccess(ResourceSet<Message> messages) {
                        log.info("Sms Send To Customer with Phone: {} ", sendSmsRequest.getPhone());
                    }
                    public void onFailure(Throwable t) {
                        log.error("Failed to Send Sms To Customer with Phone: {} " + sendSmsRequest.getPhone());
                    }
                });
    }
}
