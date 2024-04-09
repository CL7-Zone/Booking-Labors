package com.example.bookinglabor.controller.api;
import com.example.bookinglabor.service.TwilioSendSmsService;
import com.example.bookinglabor.service.VonageSendSmsService;
import com.example.bookinglabor.service.test.SendMailTestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
public class TestApiController {

    private SendMailTestService senderService;
    private VonageSendSmsService vonageSendSmsService;

    @GetMapping("date")
    public String get(Date date) {

        return date.toString();
    }

    @GetMapping("/send-sms")
    List<String> sendSms() throws URISyntaxException, IOException, InterruptedException {

        List<String> objects = new ArrayList<>();
        try{

            vonageSendSmsService.sendSms("","");
            objects.add("Send sms successfully");

            return objects;
        }catch (Exception exception){

            System.out.println(exception.getMessage());
            objects.add("Send sms failed");
//            throw exception;
            return objects;
        }
    }

    @GetMapping("/send-mail")
    private String send(){

        try{

            senderService.setMailSender("tranthetuong@dtu.edu.vn",
                    "This is email body",
                    "This is email subject");

            return "send mail successfully";

        }catch (Exception exception){
            System.out.println("ERROR: "+exception.getMessage());
            return "send mail " + "ERROR: "+exception.getMessage();
        }
    }





}
