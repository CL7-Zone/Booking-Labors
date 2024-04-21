package com.example.bookinglabor.service;

public interface TwilioSendSmsService {

    void sendSms(String phoneNumber, String body);


}
