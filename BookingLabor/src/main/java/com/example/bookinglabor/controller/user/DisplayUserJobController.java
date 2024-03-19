package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DisplayUserJobController {

    private JobService jobService;

    @GetMapping("/jobs")
    private String index(Model model){

        List<Job> jobs = jobService.findAllJobs();
        model.addAttribute("jobs", jobs);

        return "/user/job/index";
    }

    @GetMapping("/jobs/show/{id}")
    private String show(Model model, @PathVariable Long id){

        Job job = jobService.findJobById(id);
        model.addAttribute("job", job);

        return "/user/job/show";
    }



}
