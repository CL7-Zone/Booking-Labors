package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.JobService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DisplayUserJobController {

    private JobService jobService;
    private UserService userService;


    @GetMapping("/jobs")
    private String index(Model model){

        List<Job> jobs = jobService.findAllJobs();
        String email = SecurityUtil.getSessionUser();
        UserAccount role =   userService.findByEmail(email);
        List<Role> roles = role.getRoles();
        List<String> currentRoleUser = new ArrayList<>();
        for (Role r : roles) {
            currentRoleUser.add("ROLE_"+r.getName());
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("roleUser", currentRoleUser);

        return "/user/job/index";
    }

    @GetMapping("/jobs/show/{id}")
    private String show(Model model, @PathVariable Long id){

        Job job = jobService.findJobById(id);
        model.addAttribute("job", job);

        return "/user/job/show";
    }



}
