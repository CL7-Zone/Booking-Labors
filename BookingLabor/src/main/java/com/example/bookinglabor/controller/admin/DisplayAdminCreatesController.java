package com.example.bookinglabor.controller.admin;

import com.example.bookinglabor.model.City;
import com.example.bookinglabor.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class DisplayAdminCreatesController {

    private CityService cityService;
    @GetMapping("/admin/work/creates")
    public String creates(Model model){

        List<City> cities = cityService.findAllCities();
        model.addAttribute("cities",cities);

        return "admin/work/creates";
    }



}
