package com.example.bookinglabor.controller.admin.work;

import com.example.bookinglabor.service.LaborService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class AdminWorkLaborController {

    private LaborService laborService;


    @PostMapping("/labors/create/save")
    public String store(@RequestParam("file") MultipartFile file, RedirectAttributes flashMessage) {


        if (file.isEmpty()) {

            flashMessage.addFlashAttribute("isEmpty", "Upload file, Please!");
            System.out.println("Upload file, Please!");

            return "redirect:/admin/work/creates";
        }

        try{

            this.laborService.saveAllDataFromExcel(file);
            flashMessage.addFlashAttribute("success", "Insert data successfully");

            return "redirect:/admin/work/creates";

        }catch (Exception error){

            flashMessage.addFlashAttribute("failed", "Error: "+error);

            return "redirect:/admin/work/creates";
        }
    }
}
