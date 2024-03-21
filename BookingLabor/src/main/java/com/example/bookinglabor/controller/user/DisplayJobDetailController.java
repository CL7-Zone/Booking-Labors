package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.object.JobDetailObject;
import com.example.bookinglabor.service.JobDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class DisplayJobDetailController {

    private JobDetailService jobDetailService;

    @GetMapping(value = "/your-job")
    private String index(Model model){

        return "/user/job/detail";
    }

    @PostMapping(value = "/save/your-job")
    private String store(Model model, HttpSession session, HttpServletRequest request){

        try{
            List<JobDetailObject> jobDetails = (List<JobDetailObject>) session.getAttribute("jobObjects");

            if(jobDetails != null){
                jobDetailService.saveData(session);
                System.out.println("saved to job detail");
            }
            session.removeAttribute("jobObjects");

            return "redirect:/your-job";

        }catch (Exception exception){

            System.out.println("Saved job detail failed ex: " + exception);
            return "redirect:/your-job";
        }
    }

}
