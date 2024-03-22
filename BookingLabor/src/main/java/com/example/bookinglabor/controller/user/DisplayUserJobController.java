package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.JobService;
import com.example.bookinglabor.service.LaborService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class DisplayUserJobController {

    private JobService jobService;
    private UserService userService;
    private LaborService laborService;

    @GetMapping("/jobs")
    private String index(Model model){

        List<Job> jobs = jobService.findAllJobs();
        String email = SecurityUtil.getSessionUser();
        UserAccount user =   userService.findByEmail(email);
        List<Role> roles = user.getRoles();
        List<String> currentRoleUser = new ArrayList<>();

        try{
            Long labor_id = laborService.findJobByUserId(user.getId()).getId();
            model.addAttribute("labor_id", labor_id);

        }catch (Exception error){
            System.out.println("ERROR: "+ error);
        }
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
        String email = SecurityUtil.getSessionUser();
        if(email!=null){
            UserAccount role =   userService.findByEmail(email);
            List<Role> roles = role.getRoles();
            List<String> currentRoleUser = new ArrayList<>();
            Long userID =   userService.findByEmail(email).getId();

            try{
                Long labor_id = laborService.findJobByUserId(userID).getId();

                if(labor_id !=  null){
                    model.addAttribute("labor_id", labor_id);
                }
            }catch (Exception ignored){
                System.out.println("=>"+ignored);
            }
            for (Role r : roles) {
                currentRoleUser.add("ROLE_"+r.getName());
            }
            model.addAttribute("roleUser", currentRoleUser);
        }

        model.addAttribute("job", job);

        return "/user/job/show";
    }



}
