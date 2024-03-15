package com.example.bookinglabor.controller;

import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.service.LaborService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class DisplayLaborController {

    private LaborService laborService;

    @GetMapping("/labors")
    public String index(Model model){

        List<Labor> labors = laborService.findAllLabors();

        model.addAttribute("labors", labors);

        return "user/labor/index";
    }

    @GetMapping("/labor-update-info")
    public String create(){

        return "user/labor/create";
    }
}
