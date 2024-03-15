package com.example.bookinglabor.controller;


import com.example.bookinglabor.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DisplayMenuUserController {



    @GetMapping("/your-menu")
    public String index(Model model, Principal principal){

        String sessionEmail = SecurityUtil.getSessionUser();
        System.out.println(sessionEmail);

        if(principal != null){

            if(sessionEmail != null){

                return "user/index";
            }
        }

        return "redirect:/login?login=false";

    }
}
