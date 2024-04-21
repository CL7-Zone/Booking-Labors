package com.example.bookinglabor.service.work;

import com.example.bookinglabor.service.SendMailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SendMailWork implements SendMailService {


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void setMailSender(String toEmail, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lol00sever@gmail.com");
        message.setTo(toEmail);
        message.setText(content);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");
    }

}
