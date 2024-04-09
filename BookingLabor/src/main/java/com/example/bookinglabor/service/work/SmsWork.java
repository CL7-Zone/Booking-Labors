package com.example.bookinglabor.service.work;
import com.example.bookinglabor.config.TwilioConfig;
import com.example.bookinglabor.controller.component.Tcomponent;
import com.example.bookinglabor.service.VonageSendSmsService;
import com.twilio.rest.api.v2010.account.Message;
import com.example.bookinglabor.service.TwilioSendSmsService;
import com.twilio.type.PhoneNumber;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SmsWork implements VonageSendSmsService {

    @Override
    public void sendSms(String phoneNumber, String messageBody) {

        VonageClient client = VonageClient.builder().apiKey("91e2e292")
        .apiSecret("r9joEfQT0fRiGulE").build();
        TextMessage message = new TextMessage("Vonage APIs",
        phoneNumber, messageBody);
        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
        }
    }



}
