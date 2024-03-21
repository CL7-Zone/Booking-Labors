package com.example.bookinglabor.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DisplayBookingController {


    @GetMapping("/your-booking-cart")
    public String index(Model model){

        return "/user/booking/cart";
    }


}
