package com.example.bookinglabor.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DisplayBookingCartController {


    @GetMapping("/your-booking-cart")
    public String index(Model model){

        return "/user/booking/cart";
    }

    @PostMapping("/save/cart/job-detail/{id}")
    public String save(Model model, @PathVariable Long id){

        System.out.println(id);

        return "redirect:/your-booking-cart";
    }


}
