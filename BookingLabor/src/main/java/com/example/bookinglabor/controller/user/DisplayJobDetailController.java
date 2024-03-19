package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.service.JobDetailService;
import com.example.bookinglabor.service.JobService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class DisplayJobDetailController {

    private UserService userService;

    @GetMapping("/your-job-detail")
    private String index(Model model){

        return "/user/job/detail";
    }


    @PostMapping("/save/job")
    private String save(@AuthenticationPrincipal UserDetails userDetails, Model model){

        if (userDetails != null) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            List<String> roleUser = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
            System.out.println(roleUser + " saved");

        }

        return "redirect:/your-job-detail";
    }

}
