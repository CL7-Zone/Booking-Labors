package com.example.bookinglabor;
import com.example.bookinglabor.config.TwilioConfig;
import com.twilio.Twilio;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BookingLaborApplication {

    @Autowired
    private TwilioConfig twilioConfig;

    public static void main(String[] args) {


        SpringApplication.run(BookingLaborApplication.class, args);

        System.out.println("run");
    }

    @PostConstruct
    public void initTwilio() {

        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
    }

}
