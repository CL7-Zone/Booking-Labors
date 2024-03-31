package com.example.bookinglabor.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DisplayPostController {


    @GetMapping(value = {"/post/create"})
    private String create(){

        return "/user/post/create";
    }



}
