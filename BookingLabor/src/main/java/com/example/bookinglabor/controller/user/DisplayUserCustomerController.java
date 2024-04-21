package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.controller.component.EnumComponent;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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

    @GetMapping("/customer-create-info")
    public String create(Model model, Principal principal){

        List<Labor> labors = laborService.findAllLabors();
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
            model.addAttribute("simple_user_customer", simple_user_customer.get(0));
        }

        model.addAttribute("isCustomerBirthday",
                StringUtils.isEmpty(simple_user_customer) && simple_user_labor == null);
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

                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
                    String roleName = "CUSTOMER";
                    updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
                    List<GrantedAuthority> newAuthorities = auth.getAuthorities().stream()
                    .filter(authority -> !authority.getAuthority().equals("ROLE_USER"))
                    .collect(Collectors.toList());
                    newAuthorities.addAll(updatedAuthorities);
                    Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), newAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(newAuth);
                    System.out.println("NEW: " + newAuth);

                    return "redirect:/customer-create-info?success";
                }
            }

            return "redirect:/customer-create-info?failed";


        }catch (Exception exception){

            System.out.println("ERROR: "+exception);
            flashMessage.addFlashAttribute("failed", "Error: "+exception);

            return "redirect:/labor-create-info";
        }
    }

}
