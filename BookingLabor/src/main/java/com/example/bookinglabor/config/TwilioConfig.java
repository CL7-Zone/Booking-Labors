package com.example.bookinglabor.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioConfig {

    private String accountSid;
    private String authToken;
    private String trialNumber;




}
