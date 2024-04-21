package com.example.bookinglabor.controller;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.model.Header;
import com.example.bookinglabor.model.Report;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.HeaderService;
import com.example.bookinglabor.service.SendMailService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class DisplayContactController {

    HeaderService headerService;
    UserService userService;
    private SendMailService sendMailService;
    @GetMapping("/contact")
    String contact(){

        return "/contact/contact";
    }

    @GetMapping("/contact/report")
    String report(Model model){

        String userEmail = userService.findByEmailAndProvider(
                SecurityUtil.getSessionUser(),
                EnumComponent.SIMPLE).getEmail();
        List<Header> rule_reports = headerService.findHeadersByType("rule_report");


        model.addAttribute("rule_reports",rule_reports);
        model.addAttribute("userEmail",userEmail);

        return "/contact/report";
    }


    @PostMapping("/send/report")
    String send(RedirectAttributes flashMessage
                , @ModelAttribute("report") Report report){

        try{
            String adminEmail = "lol00sever@gmail.com";
            UserAccount user = userService.findByEmailAndProvider(
            SecurityUtil.getSessionUser(),
            EnumComponent.SIMPLE);
            report.setUserAccount(user);
            System.out.println(report.getEmail_report());

            sendMailService.setMailSender(adminEmail, "TỐ CÁO NGƯỜI DÙNG",
      "EMAIL NGƯỜI BỊ TỐ CÁO: "+ report.getEmail_report()+
            "\n\nID NGƯỜI TỐ CÁO:" + report.getUserAccount().getId()+
            "\n\nSỐ ĐIỆN THOẠI NGƯỜI TỐ CÁO: "+ report.getPhone()+
            "\n\nHÌNH THỨC TỐ CÁO: " + report.getRule_report()+
            "\n\nNỘI DUNG TỐ CÁO: " + report.getContent());
            flashMessage.addFlashAttribute("success","TỐ CÁO THÀNH CÔNG");

            return "redirect:/contact/report";
        }catch (Exception exception){
            return "redirect:/contact/report";
        }
    }


}
