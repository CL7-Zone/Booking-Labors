package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.model.object.JobDetailObject;
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
import java.util.List;

@Controller
@AllArgsConstructor
public class DisplayJobDetailController {

    private JobDetailService jobDetailService;
    private UserService userService;
    private LaborService laborService;
    private JobService jobService;


    @GetMapping(value = "/your-job")
    private String index(Model model){

        String sessionEmail = SecurityUtil.getSessionUser();
        long user_id = userService.findByEmail(sessionEmail).getId();
        long labor_id = laborService.findJobByUserId(user_id).getId();
        List<JobDetail> jobDetails = jobDetailService.findJobDetailByLaborId(labor_id);
        List<Job> jobs = jobService.findAllJobs();

        if(jobDetails!=null){
            for (JobDetail jobDetail : jobDetails){
                System.out.println(jobDetail.getJob().getNameJob());
            }
            model.addAttribute("jobDetails",jobDetails);
        }
        model.addAttribute("jobs",jobs);
        model.addAttribute("labor_id",labor_id);

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

    @PostMapping("/update/job-detail/{id}")
    private String update(@ModelAttribute("jobDetail") JobDetail jobDetail,
                          @PathVariable long id,
                          @RequestParam("job_id") long job_id,
                          @RequestParam("labor_id") long labor_id,
                          RedirectAttributes flashMessage) {
        try {
            if(jobDetailService.updateById(id, job_id, labor_id, jobDetail))
                flashMessage.addFlashAttribute("success", "Update successfully");

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
            jobDetailService.deleteById(id);
            flashMessage.addFlashAttribute("success", "Delete successfully");

            return "redirect:/your-job";
        }catch (Exception exception){
            flashMessage.addFlashAttribute("failed", "Error: "+exception);
            return "redirect:/your-job";
        }

    }

}
