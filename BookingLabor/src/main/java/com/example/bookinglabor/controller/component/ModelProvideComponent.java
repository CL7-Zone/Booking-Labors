package com.example.bookinglabor.controller.component;

import com.example.bookinglabor.model.*;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
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
    HeaderService headerService;

    @ModelAttribute
    private void provideModel(Model model){

        List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();
        List<Job> jobs = jobService.findAllJobs();
        List<City> cities = cityService.findAllCities();
        List<Double> prices = jobService.findJobPriceDistinct();
        List<Header> New = headerService.findHeadersByType("new");
        List<Header> contact = headerService.findHeadersByType("contact");
        List<Header> jobTitle = headerService.findHeadersByType("job");
        List<Header> customer = headerService.findHeadersByType("customer");
        List<Header> hashtag = headerService.findHeadersByType("hashtag");

        model.addAttribute("hashtag", hashtag);
        model.addAttribute("cities", cities);
        model.addAttribute("jobs", jobs);
        model.addAttribute("prices", prices);
        model.addAttribute("categoryJobs" ,categoryJobs);
    }

    @ModelAttribute
    public void addUserRoleToModel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            System.out.println("Authorization: "+authorities);

            UserAccount simple_user = userService.findByEmailAndProvider(SecurityUtil.getSessionUser(), EnumComponent.SIMPLE);

            if(simple_user != null){
                List<Role> userRoles = simple_user.getRoles();
                List<String> currentRoleUser = new ArrayList<>();

                for (Role r : userRoles) {
                    currentRoleUser.add("ROLE_"+r.getName());
                }
                model.addAttribute("userRoles", currentRoleUser);
            }


        }

    }
}
