package com.example.bookinglabor.controller.user;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.repo.LaborRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@AllArgsConstructor
@Controller
public class DisplayUserMenuController {


    private LaborService laborService;
    private JobService jobService;
    private CategoryJobService categoryJobService;
    private UserService userService;
    private JobDetailService jobDetailService;
    private CityService cityService;
    private LaborRepo laborRepo;
    private JobDetailRepo jobDetailRepo;

    @GetMapping("/your-menu")
    public String index(Model model, Principal principal, @AuthenticationPrincipal UserDetails userDetails,
                        @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                        @RequestParam(value = "size", required = false, defaultValue = "6") int size){

        Page<JobDetail> jobDetailList = jobDetailRepo.findAll(PageRequest.of(pageNumber, size));
        Page<Labor> laborList = laborRepo.findAll(PageRequest.of(pageNumber, size));
        List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();
        List<Labor> labors = laborService.findAllLabors();
        List<Job> jobs = jobService.findAllJobs();
        List<City> cities = cityService.findAllCities();
        List<Double> prices = jobService.findJobPriceDistinct();
        List<JobDetail> jobDetails = jobDetailService.findAllJobDetails();
        String email = SecurityUtil.getSessionUser();

        try{
            Long userID =   userService.findByEmail(email).getId();
            Labor labor = laborService.findByUserId(userID);

            System.out.println("LABOR EMAIL: "+labor.getUserAccount().getEmail());
            System.out.println("LABOR ID: "+labor.getId());
            model.addAttribute("labor_id", labor.getId());

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);
        }
        model.addAttribute("email", email);
        model.addAttribute("cities", cities);
        model.addAttribute("jobs", jobs);
        model.addAttribute("prices", prices);
        model.addAttribute("labors", labors);
        model.addAttribute("categoryJobs", categoryJobs);
        model.addAttribute("jobDetails", jobDetailList.getContent());
        model.addAttribute("pages", new int[jobDetailList.getTotalPages()]);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("laborList", laborList.getContent());
        model.addAttribute("pageLabors", new int[laborList.getTotalPages()]);

        return "user/index";
    }

    @PostMapping("/user/search")
    private String search(@RequestParam("nameJob") String nameJob, Model model,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                          @RequestParam(value = "size", required = false, defaultValue = "6") int size){

        if(nameJob != null){

            Page<JobDetail> jobDetailList = jobDetailService.findJobDetailsByNameJob(nameJob, PageRequest.of(pageNumber, size));
            Page<Labor> laborList = laborRepo.findAll(PageRequest.of(pageNumber, size));
            List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();
            List<Labor> labors = laborService.findAllLabors();
            List<Job> jobs = jobService.findAllJobs();
            List<City> cities = cityService.findAllCities();
            List<Double> prices = jobService.findJobPriceDistinct();
            List<JobDetail> jobDetails = jobDetailService.findAllJobDetails();
            String email = SecurityUtil.getSessionUser();

            List<String> currentRoleUser = new ArrayList<>();
            Long userID =   userService.findByEmail(email).getId();
            Labor labor = laborService.findByUserId(userID);
            DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");

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
            model.addAttribute("roleUser", currentRoleUser);
            model.addAttribute("cities", cities);
            model.addAttribute("jobs", jobs);
            model.addAttribute("prices", prices);
            model.addAttribute("email", email);
            model.addAttribute("labors", labors);
            model.addAttribute("categoryJobs", categoryJobs);
            model.addAttribute("jobDetails", jobDetailList.getContent());
            model.addAttribute("pages", new int[jobDetailList.getTotalPages()]);
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("laborList", laborList.getContent());
            model.addAttribute("pageLabors", new int[laborList.getTotalPages()]);
        }
        return "user/index";
    }



}
