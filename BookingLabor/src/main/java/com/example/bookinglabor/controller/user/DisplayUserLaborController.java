package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.CityService;
import com.example.bookinglabor.service.LaborService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class DisplayUserLaborController {

    private LaborService laborService;
    private CityService cityService;
    private UserService userService;
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

    @GetMapping("/your-info-labor")
    public String info(Model model, Principal principal){

        List<City> cities = cityService.findAllCities();
        String sessionEmail = SecurityUtil.getSessionUser();
        try{
            UserAccount user = userService.findByEmail(sessionEmail);
            List<Labor> labor = user.getLabors();
            model.addAttribute("laborInfo",labor);
            model.addAttribute("cities",cities);

            return "user/labor/info";

        }catch (Exception error){

            System.out.println(error);
            return "redirect:/login?login=false";
        }
    }

    @GetMapping("/labor-update-info")
    public String create(Model model, Principal principal){

        List<Labor> labors = laborService.findAllLabors();
        List<City> cities = cityService.findAllCities();
        String sessionEmail = SecurityUtil.getSessionUser();
        Labor labor = new Labor();

        model.addAttribute("labors",labors);
        model.addAttribute("cities",cities);
        model.addAttribute("labor", labor);
        model.addAttribute("email", sessionEmail);


        return "user/labor/create";
    }

    @PostMapping("/labor/info/save")
    public String save(RedirectAttributes flashMessage, @ModelAttribute("labor") Labor labor,
                        BindingResult res, Model model, @RequestParam("city_id") int city_id,
                        @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                        @RequestParam("free_time_from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate free_time_from,
                        @RequestParam("free_time_to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate free_time_to) {

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userService.findByEmail(sessionEmail);
        List<Labor> labor_current = user.getLabors();

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
                        labor.setFree_time_from(java.sql.Date.valueOf(free_time_from));
                        labor.setFree_time_to(java.sql.Date.valueOf(free_time_to));
                        this.laborService.createLaborByUserIdAndCityId(user.getId(), (long) city_id, labor);
                    }
                    if(Objects.equals(roleUser.getName(), "CUSTOMER")){
                        this.userService.createUserRoleLabor(roleRepo.findByName("LABOR"));
                        labor.setBirthday(java.sql.Date.valueOf(birthday));
                        labor.setFree_time_from(java.sql.Date.valueOf(free_time_from));
                        labor.setFree_time_to(java.sql.Date.valueOf(free_time_to));
                        this.laborService.createLaborByUserIdAndCityId(user.getId(), (long) city_id, labor);
                    }

                    return "redirect:/labor-update-info?success";
                }
            }

            return "redirect:/labor-update-info?failed";

        }catch (Exception error){

            System.out.println(error);

            flashMessage.addFlashAttribute("failed", "Error: "+error);

            return "redirect:/labor-update-info";
        }
    }



}
