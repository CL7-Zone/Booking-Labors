package com.example.bookinglabor.controller.user;
import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.model.sessionObject.JobDetailObject;
import com.example.bookinglabor.security.SecurityUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.example.bookinglabor.service.JobDetailService;
import com.example.bookinglabor.service.JobService;
import com.example.bookinglabor.service.LaborService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@AllArgsConstructor
public class DisplayJobCartController {

    private UserService userService;
    private JobService jobService;
    private JobDetailService jobDetailService;
    private LaborService laborService;

    @SuppressWarnings("unchecked")
    @GetMapping("/your-cart")
    private String index(Model model, HttpSession session){

        List<JobDetailObject> jobDetails = (List<JobDetailObject>) session.getAttribute("jobObjects");
        String email = SecurityUtil.getSessionUser();
        UserAccount role =   userService.findByEmail(email);
        List<Role> roles = role.getRoles();
        List<String> currentRoleUser = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        long user_id = userService.findByEmail(SecurityUtil.getSessionUser()).getId();
        long labor_id = laborService.findByUserId(user_id).getId();

        if(jobDetails!= null){
            for (JobDetailObject jobDetail : jobDetails) {

                String money = decimalFormat.format(jobDetail.getPrice());
                model.addAttribute("money", money);
            }
        }
        for (Role r : roles) {
            currentRoleUser.add("ROLE_"+r.getName());
        }
        System.out.println("Number job detail: "+ jobDetailService.countJobDetailByLaborId(labor_id));
        model.addAttribute("roleUser", currentRoleUser);
        model.addAttribute("jobDetails",jobDetails);

        return "user/job/cart";
    }


    @PostMapping("/save/cart/job/{id}")
    private String save(@AuthenticationPrincipal UserDetails userDetails,
                        @RequestParam("id") Long job_id, Model model,
                        @RequestParam("labor_id") Long labor_id, RedirectAttributes res,
                        HttpServletRequest request, HttpSession session){

        try{
            @SuppressWarnings("unchecked")
            List<JobDetailObject> jobDetailObjects = (List<JobDetailObject>) request.getSession().getAttribute("jobObjects");

            if (userDetails != null) {

                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                List<String> roleUser = authorities.stream().map(GrantedAuthority::getAuthority).toList();
                String sessionEmail = SecurityUtil.getSessionUser();
                UserAccount user = userService.findByEmail(sessionEmail);
                Job job = jobService.findById(job_id);
                System.out.println(roleUser + " saved to session store");
                System.out.println("Job id: "+ job.getId());
                System.out.println("Job name: "+ job.getNameJob());

                switch (jobDetailService.saveDataToSessionStore(jobDetailObjects, request, session, job, labor_id)){
                    case 0:
                        res.addFlashAttribute("overLimit", "You are not allowed to select the same job!!!");
                        return "redirect:/your-cart";
                    case 1:
                        res.addFlashAttribute("overLimit", "You have saved into the cart over the limit!!!");
                        return "redirect:/your-cart";
                    case 2:
                        res.addFlashAttribute("overLimit", "You have had this job!!!");
                        return "redirect:/your-cart";
                    case 3:
                        res.addFlashAttribute("overLimit", "Your number of jobs over the limited. Your number of jobs must <= 5!!!");
                        return "redirect:/your-cart";
                }
            }
            return "redirect:/your-cart?success";

        }catch (Exception exception){

            System.out.println("ERROR SAVED CART: "+exception);
            return "redirect:/your-cart?failed";
        }
    }

    @PostMapping("/delete/job-cart/{index}")
    private String delete(@PathVariable int index, HttpSession session, RedirectAttributes res,
                          Model model, HttpServletRequest request){

        List<JobDetailObject> jobDetails = (List<JobDetailObject>) session.getAttribute("jobObjects");

        try{
            if (jobDetails != null && index >= 0 && index < jobDetails.size()) {
                System.out.println("Remove index: " + index);
                jobDetails.remove(index);
                session.setAttribute("jobObjects", jobDetails);
                if(jobDetails.isEmpty()){
                    session.removeAttribute("jobObjects");
                }
                res.addFlashAttribute("deleteSuccess", "Delete item " + (index+1) + " successfully");
            }

        }catch (Exception exception){
            res.addFlashAttribute("deleteFailed", "Delete failed!!!");
        }

        return "redirect:/your-cart";
    }


}
