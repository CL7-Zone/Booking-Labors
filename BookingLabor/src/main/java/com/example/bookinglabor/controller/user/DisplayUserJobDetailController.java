package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.model.sessionObject.JobDetailObject;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.JobDetailService;
import com.example.bookinglabor.service.JobService;
import com.example.bookinglabor.service.LaborService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.List;

@Controller
@AllArgsConstructor
public class DisplayUserJobDetailController {

    private JobDetailService jobDetailService;
    private UserService userService;
    private LaborService laborService;
    private JobService jobService;
    private static final int MAX_RECORD = 5;

    @GetMapping(value = "/your-job")
    private String index(Model model){

        String sessionEmail = SecurityUtil.getSessionUser();
        long user_id = userService.findByEmail(sessionEmail).getId();
        long labor_id = laborService.findByUserId(user_id).getId();
        List<JobDetail> jobDetails = jobDetailService.findJobDetailByLaborId(labor_id);
        List<Job> jobs = jobService.findAllJobs();
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");

        model.addAttribute("jobs",jobs);
        model.addAttribute("labor_id",labor_id);
        model.addAttribute("jobDetails",jobDetails);

        return "/user/job/detail";
    }

    @PostMapping(value = "/save/your-job")
    private String store(Model model, HttpSession session,
                         @ModelAttribute("job_detail") JobDetail jobDetail,
                         RedirectAttributes res,
                         HttpServletRequest request){
        try{
            List<JobDetailObject> jobDetails = (List<JobDetailObject>) session.getAttribute("jobObjects");
            long user_id = userService.findByEmail(SecurityUtil.getSessionUser()).getId();
            long labor_id = laborService.findByUserId(user_id).getId();

            System.out.println("size JobDetailObject: "+jobDetails.size());
            System.out.println("size max JobDetail: "+jobDetailService.countJobDetail());

//            if(jobDetails.size() + jobDetailService.countJobDetailByLaborId(labor_id) > MAX_RECORD){
//                res.addFlashAttribute("overLimit", "Your number of jobs over the limited. Your number of jobs must <= 5!!!");
//                return "redirect:/your-cart";
//            }
            jobDetailService.saveData(session, jobDetail);
            System.out.println("saved to job detail");
            session.removeAttribute("jobObjects");

            return "redirect:/your-job";

        }catch (Exception exception){

            System.out.println("Saved job detail failed ex: " + exception);
            return "redirect:/your-job";
        }
    }

    @PostMapping("/update/job-detail/{id}")
    private String update(@ModelAttribute("jobDetail") JobDetail jobDetail,
                          @PathVariable long id,
                          @RequestParam("job_id") long job_id,
                          @RequestParam("labor_id") long labor_id,
                          RedirectAttributes flashMessage) {
        try {
            if(jobDetailService.updateById(job_id, labor_id, jobDetail))
                flashMessage.addFlashAttribute("success", "CẬP NHẬT THÀNH CÔNG");

            return "redirect:/your-job";
        } catch (Exception exception) {
            flashMessage.addFlashAttribute("failed", "Error: " + exception);
            return "redirect:/your-job";
        }
    }

    @PostMapping("/delete/job-detail/{id}")
    private String delete(@PathVariable long id, RedirectAttributes flashMessage){

        try{
            System.out.println("Delete job detail: "+id);

            if(jobDetailService.deleteById(id)){
                flashMessage.addFlashAttribute("success", "XÓA THÀNH CÔNG");
                return "redirect:/your-job";
            }

            flashMessage.addFlashAttribute("failed", "BẠN KHÔNG ĐƯỢC PHÉP XÓA CÔNG VIỆC NÀY VÌ NÓ ĐANG NẰM TRONG DANH SÁCH ỨNG TUYỂN!!!");
            return "redirect:/your-job";
        }catch (Exception exception){
            flashMessage.addFlashAttribute("failed", "Error: "+exception);
            return "redirect:/your-job";
        }

    }

}
