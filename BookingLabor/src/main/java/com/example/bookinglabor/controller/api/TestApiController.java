package com.example.bookinglabor.controller.api;
import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Header;
import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.sessionObject.AuthObject;
import com.example.bookinglabor.service.*;
import com.example.bookinglabor.service.test.SendMailTestService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@AllArgsConstructor
public class TestApiController {

    private SendMailTestService senderService;
    private VonageSendSmsService vonageSendSmsService;
    private JobService jobService;
    CategoryJobService categoryJobService;
    HeaderService headerService;
    BookingService bookingService;

    @GetMapping("/header-api")
    public List<Header> header() {

        try {
            HashMap<String, String> map = new HashMap<>();

            return headerService.findAllHeaders();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception.getMessage());

            return null;
        }
    }

    @GetMapping("/booking-api")
    public List<Booking> booking() {

        try {
            HashMap<String, String> map = new HashMap<>();
            List<Job> jobs = jobService.findAllJobs();

            return bookingService.findAllBookings();

        }catch (Exception exception){

            System.out.println("ERROR job: "+exception.getMessage());

            return null;
        }
    }



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
