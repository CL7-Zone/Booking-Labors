package com.example.bookinglabor.controller.api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TestApiController {

    @GetMapping("date")
    public String get(Date date) {

        return date.toString();
    }





}
