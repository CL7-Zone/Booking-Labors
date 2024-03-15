package com.example.bookinglabor.controller;

import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.service.LaborService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class DisplayCustomerController {

    private LaborService laborService;

    @GetMapping("/customer-update-info")
    public String create(){

        return "user/customer/create";
    }


}
