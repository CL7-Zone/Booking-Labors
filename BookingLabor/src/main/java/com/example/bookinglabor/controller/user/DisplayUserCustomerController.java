package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.Customer;
import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.CustomerService;
import com.example.bookinglabor.service.LaborService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Controller
@AllArgsConstructor
public class DisplayUserCustomerController {

    private LaborService laborService;
    private CustomerService customerService;
    private UserService userService;
    private RoleRepo roleRepo;
    @GetMapping("/your-info-customer")
    private String index(Model model,  Principal principal){

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userService.findByEmail(sessionEmail);
        List<Customer> customer = user.getCustomers();
        model.addAttribute("customerInfo",customer);

        return "user/customer/info";

    }

    @GetMapping("/customer-update-info")
    public String create(Model model, Principal principal){

        List<Labor> labors = laborService.findAllLabors();
        String sessionEmail = SecurityUtil.getSessionUser();

        model.addAttribute("email",sessionEmail);

        return "user/customer/create";
    }

    @PostMapping("/customer/info/save")
    public String save(RedirectAttributes flashMessage, @ModelAttribute("customer") Customer customer,
                        @RequestParam("year") String year, @RequestParam("month") String month,
                        @RequestParam("day") String day) throws ParseException {

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userService.findByEmail(sessionEmail);
        List<Customer> customer_current = user.getCustomers();
        LocalDate birthday = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        java.sql.Date birthday_customer = java.sql.Date.valueOf(birthday);

        System.out.println(birthday_customer);
        System.out.println("CUSTOMER USER ID: " + user.getId());


        if(sessionEmail == null){

            return "redirect:/login?auth=unauthorized";
        }

        try{

            if(customer_current.isEmpty()){

                for(Role roleUser : user.getRoles()) {

                    if(Objects.equals(roleUser.getName(), "USER")){

                        this.userService.updateUserRole(roleRepo.findByName("CUSTOMER"));
                        customer.setBirthday_customer(birthday_customer);
                        System.out.println("Customer object: "+customer);
                        this.customerService.createCustomerByUserId(user.getId(), customer);
                    }
                    if(Objects.equals(roleUser.getName(), "LABOR")){
                        this.userService.createUserRoleCustomer(roleRepo.findByName("CUSTOMER"));
                        customer.setBirthday_customer(birthday_customer);
                        System.out.println("Customer object: "+customer);
                        this.customerService.createCustomerByUserId(user.getId(), customer);
                    }

                    return "redirect:/customer-update-info?success";
                }
            }

            return "redirect:/customer-update-info?failed";


        }catch (Exception exception){

            System.out.println("ERROR: "+exception);
            flashMessage.addFlashAttribute("failed", "Error: "+exception);

            return "redirect:/labor-update-info";
        }
    }

}
