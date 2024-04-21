package com.example.bookinglabor.controller.admin;

import com.example.bookinglabor.model.City;
import com.example.bookinglabor.service.CityService;
import com.example.bookinglabor.service.HeaderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@AllArgsConstructor
@Controller
public class DisplayAdminCreatesController {

    private CityService cityService;
    HeaderService headerService;
    @GetMapping("/admin/work/creates")
    public String creates(Model model){

        List<City> cities = cityService.findAllCities();
        model.addAttribute("cities",cities);

        return "admin/work/creates";
    }

    @PostMapping("/header/create/save")
    public String saveHeader(Model model,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes flashMessage){
        if (file.isEmpty()) {

            flashMessage.addFlashAttribute("isEmpty", "Upload file, Please!");
            System.out.println("Upload file, Please!");

            return "redirect:/admin/work/creates";
        }
        try{
            flashMessage.addFlashAttribute("success", "Insert data successfully");
            headerService.saveAllDataFromExcel(file);
            return "redirect:/admin/work/creates";

        }catch (Exception error){

            flashMessage.addFlashAttribute("failed", "Error: "+error);
            return "redirect:/admin/work/creates";
        }
    }





}
