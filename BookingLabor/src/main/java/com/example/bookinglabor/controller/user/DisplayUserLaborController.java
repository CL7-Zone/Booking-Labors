package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class DisplayUserLaborController {

    private LaborService laborService;
    private CustomerService customerService;
    private CityService cityService;
    private UserService userService;
    private JobDetailService jobDetailService;
    private RoleRepo roleRepo;

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/labors")
    public String index(Model model){

        List<Labor> labors = laborService.findAllLabors();

        model.addAttribute("labors", labors);

        return "user/labor/index";
    }

    @GetMapping(value={"/labors/{labor_id}", "/your-menu/labors/{labor_id}"})
    public String show(Model model,
                       @PathVariable Long labor_id,
                       @AuthenticationPrincipal UserDetails userDetails){

        Labor labor = laborService.findById(labor_id);
        List<JobDetail>  laborDetails =  jobDetailService.findJobDetailByLaborId(labor_id);
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        int count = 0;

        for(JobDetail jobDetail : laborDetails){
            String money = decimalFormat.format(jobDetail.getJob().getPrice());
            model.addAttribute("money",money);

            for(CommentSkill commentSkill : jobDetail.getCommentSkills()){
                count++;
            }
        }
        try{
            String sessionEmail = SecurityUtil.getSessionUser();
            if(sessionEmail!=null){
                model.addAttribute("email", sessionEmail);
            }
        }catch (Exception ignored){}

        if (userDetails != null){
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            List<String> roleUser = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
            model.addAttribute("roleUser", roleUser);
        }
        model.addAttribute("laborDetails", laborDetails);
        model.addAttribute("labor", labor);
        model.addAttribute("count", count);

        return "user/labor/show";
    }

    @GetMapping("/your-info-labor")
    public String info(Model model, Principal principal){

        List<City> cities = cityService.findAllCities();
        String sessionEmail = SecurityUtil.getSessionUser();
        try{
            UserAccount user = userService.findByEmail(sessionEmail);
            List<Labor> labor = user.getLabors();
            Long userId = user.getId();

            model.addAttribute("userID",userId);
            model.addAttribute("email",sessionEmail);
            model.addAttribute("laborInfo",labor);
            model.addAttribute("cities",cities);

            return "user/labor/info";

        }catch (Exception error){

            System.out.println(error);
            return "redirect:/login?login=false";
        }
    }

    @GetMapping("/labor-create-info")
    public String create(Model model, Principal principal){

        List<Labor> labors = laborService.findAllLabors();
        List<City> cities = cityService.findAllCities();
        String sessionEmail = SecurityUtil.getSessionUser();
        Long simple_user_id = userService.findByEmailAndProvider(sessionEmail, EnumComponent.SIMPLE).getId();
        List<Customer> simple_user_customer = customerService.findByUserId(simple_user_id);
        Labor simple_user_labor = laborService.findByUserId(simple_user_id);

        if(simple_user_labor != null){
            System.out.println(simple_user_labor.getFull_name());
            model.addAttribute("simple_user_labor", simple_user_labor);
        }

        if(!simple_user_customer.isEmpty()){
            System.out.println(simple_user_customer.get(0).getFull_name());
            model.addAttribute("simple_user_customer", simple_user_customer);
        }
        model.addAttribute("labors",labors);
        model.addAttribute("cities",cities);
        model.addAttribute("email", sessionEmail);


        return "user/labor/create";
    }

    @PostMapping("/labor/info/save")
    public String save(RedirectAttributes flashMessage, @ModelAttribute("labor") Labor labor,
                        BindingResult res, Model model, @RequestParam("city_id") long city_id,
                       @RequestParam("year") String year, @RequestParam("month") String month,
                       @RequestParam("day") String day
                       ) {

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userService.findByEmail(sessionEmail);
        List<Labor> labor_current = user.getLabors();
        LocalDate birthday = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        if(sessionEmail == null){

            return "redirect:/login?auth=unauthorized";
        }

        System.out.println("CITY ID: " + city_id);
        System.out.println("USER ID: " + user.getId());
        System.out.println(birthday);

        try{
            if(labor_current.isEmpty()){

                for(Role roleUser : user.getRoles()) {

                    if(Objects.equals(roleUser.getName(), "USER")){
                        this.userService.updateUserRole(roleRepo.findByName("LABOR"));
                        labor.setBirthday(java.sql.Date.valueOf(birthday));
                        this.laborService.createLaborByUserIdAndCityId(user.getId(),  city_id, labor);
                    }
                    if(Objects.equals(roleUser.getName(), "CUSTOMER")){
                        this.userService.createUserRoleLabor(roleRepo.findByName("LABOR"));
                        labor.setBirthday(java.sql.Date.valueOf(birthday));
                        this.laborService.createLaborByUserIdAndCityId(user.getId(), city_id, labor);
                    }

                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
                    String roleName = "LABOR";
                    updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
                    List<GrantedAuthority> newAuthorities = auth.getAuthorities().stream()
                    .filter(authority -> !authority.getAuthority().equals("ROLE_USER"))
                    .collect(Collectors.toList());
                    newAuthorities.addAll(updatedAuthorities);
                    Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), newAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(newAuth);
                    System.out.println("NEW: " + newAuth);

                    return "redirect:/labor-create-info?success";
                }
            }

            return "redirect:/labor-create-info?failed";

        }catch (Exception error){

            System.out.println(error);

            flashMessage.addFlashAttribute("failed", "Error: "+error);

            return "redirect:/labor-create-info";
        }
    }

    @PostMapping("/update-info/{id}")
    private String update(@PathVariable Long id,
                          @ModelAttribute("labor") Labor labor,
                          @RequestParam("city_id") long city_id,
                          RedirectAttributes flashMessage){

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userService.findByEmail(sessionEmail);

        try{
            System.out.println(labor);
            laborService.update(user.getId(), city_id, labor);
            flashMessage.addFlashAttribute("success", "Update successfully");

            return "redirect:/your-info-labor";
        }catch (Exception exception){

            flashMessage.addFlashAttribute("failed", "Error: "+exception);
            return "redirect:/your-info-labor";
        }
    }



}
