package com.example.bookinglabor.controller.api;
import com.example.bookinglabor.service.test.SendMailTestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.event.EventListener;
import java.util.Date;

@RestController
@AllArgsConstructor
public class TestApiController {

    @Autowired
    private SendMailTestService senderService;

    @GetMapping("date")
    public String get(Date date) {

        return date.toString();
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
