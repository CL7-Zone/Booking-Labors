package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.*;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.repo.LaborRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class DisplayUserPostController {

    private LaborService laborService;
    private JobService jobService;
    private CategoryJobService categoryJobService;
    private UserService userService;
    private JobDetailService jobDetailService;
    private CityService cityService;
    private LaborRepo laborRepo;
    private JobDetailRepo jobDetailRepo;
    private PostService postService;

    @GetMapping(value = {"/post/create"})
    private String create(Model model){

        String email = SecurityUtil.getSessionUser();
        List<City> cities = cityService.findAllCities();
        List<Job> jobs = jobService.findAllJobs();
        List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();

        List<String> currentRoleUser = new ArrayList<>();

        model.addAttribute("cities", cities);
        model.addAttribute("jobs", jobs);
        model.addAttribute("categoryJobs", categoryJobs);
        model.addAttribute("roleUser", currentRoleUser);

        return "/user/post/create";
    }

    @PostMapping(value = {"/save/post"})
    String save(Model model, @ModelAttribute("post") Post post,
                @RequestParam("city_id") Long city_id,
                @RequestParam("category_id") Long category_id,
                @RequestParam("job_id") Long job_id,
                RedirectAttributes flashMessage){
        try{
            postService.saveData(post, city_id, category_id, job_id);

            flashMessage.addFlashAttribute("success", "create successfully");
            return "redirect:/post/create";

        }catch (Exception exception){

            flashMessage.addFlashAttribute("failed", "create failed");
            return "redirect:/post/create";
        }
    }



}
