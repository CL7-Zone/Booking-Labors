package com.example.bookinglabor.controller.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin/profile")
    UserDetails index(@AuthenticationPrincipal UserDetails userDetails){

        System.out.println(userDetails);

        return userDetails;
    }

}
