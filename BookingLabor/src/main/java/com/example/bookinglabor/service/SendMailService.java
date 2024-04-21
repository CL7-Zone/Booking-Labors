package com.example.bookinglabor.service;

import org.springframework.stereotype.Service;

@Service
public interface SendMailService{

    void setMailSender(String toEmail, String subject, String body);


}
