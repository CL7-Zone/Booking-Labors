package com.example.bookinglabor.controller.user;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.JobDetailService;
import com.example.bookinglabor.service.JobService;
import com.example.bookinglabor.service.LaborService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Controller
public class DisplayUserMenuController {


    private LaborService laborService;
    private JobService jobService;
    private UserService userService;
    private JobDetailService jobDetailService;

    @GetMapping("/your-menu")
    public String index(Model model, Principal principal, @AuthenticationPrincipal UserDetails userDetails){

        List<Labor> labors = laborService.findAllLabors();
        List<Job> jobs = jobService.findAllJobs();
        List<JobDetail> jobDetails = jobDetailService.findAllJobDetails();
        String email = SecurityUtil.getSessionUser();
        UserAccount role =   userService.findByEmail(email);
        List<Role> roles = role.getRoles();
        List<String> currentRoleUser = new ArrayList<>();
        Long userID =   userService.findByEmail(email).getId();
        Labor labor = laborService.findJobByUserId(userID);

        try{

            if (userDetails != null) {

                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                List<String> roleUser = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

                System.out.println("LABOR EMAIL: "+labor.getUserAccount().getEmail());
                System.out.println("LABOR ID: "+labor.getId());
                System.out.println(roleUser);
            }

            model.addAttribute("labor_id", labor.getId());

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);
        }

        for (Role r : roles) {
            currentRoleUser.add("ROLE_"+r.getName());
        }
        model.addAttribute("roleUser", currentRoleUser);
        model.addAttribute("jobs", jobs);
        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("email", email);
        model.addAttribute("labors", labors);

        return "user/index";
    }
}
