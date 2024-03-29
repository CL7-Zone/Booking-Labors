package com.example.bookinglabor.controller;
import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.service.CategoryJobService;
import com.example.bookinglabor.service.JobService;
import com.example.bookinglabor.service.LaborService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Controller
@AllArgsConstructor
public class DisplayHomeController {

    private CategoryJobService categoryJobService;
    private LaborService laborService;
    private JobService jobService;

//    @Autowired
//    private int loadCounter;
//    private int LoadCount(){
//
//        loadCounter++;
//
//        return loadCounter;
//    }

    @GetMapping("/")
    private String index(Model model){

        List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();
        List<Labor> labors = laborService.findAllLabors();
        List<Job> jobs = jobService.findAllJobs();
        Long countJob = categoryJobService.countJob();
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        Function<Long, Long> countJobsByCategoryJobFunction = categoryJobService::countJobsByCategoryJob;

        for(Job job : jobs){
            String money = decimalFormat.format(job.getPrice());
            model.addAttribute("money",money);
        }

        model.addAttribute("countJobsByCategoryJob", countJobsByCategoryJobFunction);
        model.addAttribute("categoryJobs", categoryJobs);
        model.addAttribute("labors", labors);
        model.addAttribute("jobs", jobs);

        return "home/index";
    }

    @GetMapping("/category-job/{id}")
    public String show(@PathVariable("id") Long id, Model model){

        CategoryJob categoryJob = categoryJobService.findCategoryJobById(id);
        Function<Long, Long> countJobsByCategoryJobFunction = categoryJobService::countJobsByCategoryJob;

        model.addAttribute("categoryJob", categoryJob);
        model.addAttribute("countJobsByCategoryJob", countJobsByCategoryJobFunction);

        return "home/show";
    }


}
