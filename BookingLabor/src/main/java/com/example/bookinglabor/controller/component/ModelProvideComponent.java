package com.example.bookinglabor.controller.component;

import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@AllArgsConstructor
public class ModelProvideComponent {

    private CategoryJobService categoryJobService;
    private LaborService laborService;
    private JobService jobService;
    private UserService userService;
    private JobDetailService jobDetailService;
    private CityService cityService;

    @ModelAttribute
    private void provideModel(Model model){

        List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();
        List<Job> jobs = jobService.findAllJobs();
        List<City> cities = cityService.findAllCities();
        List<Double> prices = jobService.findJobPriceDistinct();

        model.addAttribute("cities", cities);
        model.addAttribute("jobs", jobs);
        model.addAttribute("prices", prices);
        model.addAttribute("categoryJobs" ,categoryJobs);
    }

    @ModelAttribute
    public void addUserRoleToModel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            List<String> roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
            model.addAttribute("userRoles", roles);
        }
    }
}
