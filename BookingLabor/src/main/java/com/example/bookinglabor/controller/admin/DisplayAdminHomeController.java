package com.example.bookinglabor.controller.admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DisplayAdminHomeController {


    @GetMapping("/admin/home")
    private String index(Model model){

        System.out.println("Load Home Admin");

        return "admin/home/index";
    }



}
