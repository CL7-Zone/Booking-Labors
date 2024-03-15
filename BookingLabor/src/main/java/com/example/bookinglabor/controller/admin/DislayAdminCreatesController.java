package com.example.bookinglabor.controller.admin;

import com.example.bookinglabor.service.CategoryJobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class DislayAdminCreatesController {

    @GetMapping("/admin/work/creates")
    public String creates(Model model){


        return "admin/work/creates";
    }



}
