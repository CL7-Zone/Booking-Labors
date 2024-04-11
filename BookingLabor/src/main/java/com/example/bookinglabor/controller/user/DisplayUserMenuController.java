package com.example.bookinglabor.controller.user;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.model.sessionObject.AuthObject;
import com.example.bookinglabor.model.sessionObject.UserObject;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.repo.LaborRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;

@Controller
public class DisplayUserMenuController {


    private final LaborService laborService;
    private final JobService jobService;
    private final CategoryJobService categoryJobService;
    private final UserService userService;
    private final JobDetailService jobDetailService;
    private final CityService cityService;
    private final LaborRepo laborRepo;
    private final JobDetailRepo jobDetailRepo;
    private final SendMailService sendMailService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public DisplayUserMenuController(LaborService laborService, JobService jobService, CategoryJobService categoryJobService, UserService userService, JobDetailService jobDetailService, CityService cityService, LaborRepo laborRepo, JobDetailRepo jobDetailRepo, SendMailService sendMailService, UserDetailsService userDetailsService) {
        this.laborService = laborService;
        this.jobService = jobService;
        this.categoryJobService = categoryJobService;
        this.userService = userService;
        this.jobDetailService = jobDetailService;
        this.cityService = cityService;
        this.laborRepo = laborRepo;
        this.jobDetailRepo = jobDetailRepo;
        this.sendMailService = sendMailService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/your-menu")
    public String index(Model model, Principal principal,
                        @AuthenticationPrincipal UserDetails userDetails,
                        HttpServletRequest request, HttpSession session,
                        @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                        @RequestParam(value = "size", required = false, defaultValue = "6") int size)
        {

        Page<JobDetail> jobDetailList = jobDetailRepo.findAll(PageRequest.of(pageNumber, size));
        Page<Labor> laborList = laborRepo.findAll(PageRequest.of(pageNumber, size));
        List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();
        List<Labor> labors = laborService.findAllLabors();
        List<Job> jobs = jobService.findAllJobs();
        List<City> cities = cityService.findAllCities();
        List<Double> prices = jobService.findJobPriceDistinct();
        List<JobDetail> jobDetails = jobDetailService.findAllJobDetails();
        String email = SecurityUtil.getSessionUser();

        try{
            Long userID =   userService.findByEmail(email).getId();
            Labor labor = laborService.findByUserId(userID);

            System.out.println("LABOR EMAIL: "+labor.getUserAccount().getEmail());
            System.out.println("LABOR ID: "+labor.getId());
            model.addAttribute("labor_id", labor.getId());

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);
        }
        model.addAttribute("email", email);
        model.addAttribute("cities", cities);
        model.addAttribute("jobs", jobs);
        model.addAttribute("prices", prices);
        model.addAttribute("labors", labors);
        model.addAttribute("categoryJobs", categoryJobs);
        model.addAttribute("jobDetails", jobDetailList.getContent());
        model.addAttribute("pages", new int[jobDetailList.getTotalPages()]);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("laborList", laborList.getContent());
        model.addAttribute("pageLabors", new int[laborList.getTotalPages()]);

        return "user/index";
    }

    @GetMapping("/auth")
    String auth(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("User detail: " + userDetails);
        } else {
            System.out.println("User is not logged in.");
        }

        return "/auth/auth";
    }


    @PostMapping("/auth/save")
    String authSave(@Valid @ModelAttribute("user") UserDto user,
                    HttpServletRequest request,
                    HttpSession session, RedirectAttributes flashMessage) throws ServletException {
        try{
            @SuppressWarnings("unchecked")
            List<AuthObject> authObject = (List<AuthObject>) request.getSession().getAttribute("authObject");
            userService.saveDataToSessionStore(authObject, user, request, session, "");
            List<AuthObject> authObj = (List<AuthObject>) session.getAttribute("authObject");
            for(AuthObject userSend : authObj){
                sendMailService.setMailSender(userSend.getEmail(), "XÁC THỰC TÀI KHOẢN",
            "Xin chào "+ userSend.getEmail()+ "," +
                "\n\nMã xác thực tài khoản của bạn là: " + userSend.getToken()+
                "\n\nNếu có bất kỳ thắc mắc nào vui lòng liên hệ với chúng tôi." +
                "\n\nBest regards,\nBookingLabor Website");
                break;
            }
            flashMessage.addFlashAttribute("auth",
"CHÚNG TÔI ĐÃ GỬI MÃ XÁC THỰC VỀ GMAIL CỦA BẠN VUI LÒNG KIỂM TRA GMAIL VÀ NHẬP VÀO MÃ XÁC THỰC!");
            return "redirect:/auth";

        }catch (Exception exception){
            return "redirect:/auth";
        }
    }

    @PostMapping("/auth/account")
    String authAccount(HttpSession session,
                       @RequestParam("auth_token") String auth_token,
                       @AuthenticationPrincipal UserDetails userDetails,
                       RedirectAttributes flashMessage) {

        List<AuthObject> authObj = (List<AuthObject>) session.getAttribute("authObject");
        try{
            if(!userService.checkAuthToken(authObj,auth_token)){
                flashMessage.addFlashAttribute("failed","SAI MÃ XÁC THỰC!");
                return "redirect:/auth";
            }
            //todo

            for(AuthObject auth : authObj){
                System.out.println("username: "+auth.getEmail());
            }

            return "redirect:/your-menu";
        }catch (Exception exception){
            throw exception;
//            return "redirect:/login";
        }
    }


    @PostMapping("/user/search")
    private String search(@RequestParam("nameJob") String nameJob, Model model,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                          @RequestParam(value = "size", required = false, defaultValue = "6") int size){

        if(nameJob != null){
            Page<JobDetail> jobDetailList = jobDetailService.findJobDetailsByNameJob(nameJob, PageRequest.of(pageNumber, size));
            Page<Labor> laborList = laborRepo.findAll(PageRequest.of(pageNumber, size));
            List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();
            List<Labor> labors = laborService.findAllLabors();
            List<Job> jobs = jobService.findAllJobs();
            List<City> cities = cityService.findAllCities();
            List<Double> prices = jobService.findJobPriceDistinct();
            List<JobDetail> jobDetails = jobDetailService.findAllJobDetails();
            String email = SecurityUtil.getSessionUser();
            List<String> currentRoleUser = new ArrayList<>();
            Long userID =   userService.findByEmail(email).getId();
            Labor labor = laborService.findByUserId(userID);
            DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
            try{
                if (userDetails != null) {

                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                    List<String> roleUser = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

                    System.out.println("LABOR EMAIL: "+labor.getUserAccount().getEmail());
                    System.out.println("LABOR ID: "+labor.getId());
                    System.out.println(roleUser);
                }
                model.addAttribute("labor_id", labor.getId());

            }catch (Exception exception){
                System.out.println("ERROR: "+exception);
            }
            model.addAttribute("roleUser", currentRoleUser);
            model.addAttribute("cities", cities);
            model.addAttribute("jobs", jobs);
            model.addAttribute("prices", prices);
            model.addAttribute("email", email);
            model.addAttribute("labors", labors);
            model.addAttribute("categoryJobs", categoryJobs);
            model.addAttribute("jobDetails", jobDetailList.getContent());
            model.addAttribute("pages", new int[jobDetailList.getTotalPages()]);
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("laborList", laborList.getContent());
            model.addAttribute("pageLabors", new int[laborList.getTotalPages()]);
        }
        return "user/index";
    }



}
