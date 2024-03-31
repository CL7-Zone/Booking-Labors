package com.example.bookinglabor.service.test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailTestService {


    @Autowired
    private JavaMailSender mailSender;

    public void setMailSender(String toEmail, String subject, String body){


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lol00sever@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");
    }

}
