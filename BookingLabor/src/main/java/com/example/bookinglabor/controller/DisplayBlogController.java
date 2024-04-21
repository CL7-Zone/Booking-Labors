package com.example.bookinglabor.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DisplayBlogController {


    @GetMapping("/blog")
    String blog(){

        return "/blog/index";
    }

}
