package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Customer;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.model.sessionObject.BookingObject;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.text.DecimalFormat;

@Controller
@AllArgsConstructor
public class DisplayBookingCartController {

    private BookingService bookingService;
    private JobDetailService jobDetailService;
    private CityService cityService;
    private UserService userService;
    private CustomerService customerService;
    private LaborService laborService;


    @GetMapping("/your-booking-cart")
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                        Model model, HttpServletRequest request,
                        HttpSession session){

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roleUser = authorities.stream().map(GrantedAuthority::getAuthority).toList();
        List<BookingObject> bookingObjects = (List<BookingObject>) session.getAttribute("bookingObjects");
        List<City> cities = cityService.findAllCities();
        Long user_id = userService.findByEmail(SecurityUtil.getSessionUser()).getId();
        List<Customer> customers = customerService.findByUserId(user_id);
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        double totalPrice = 0;

        for (Customer customer: customers){
            System.out.println("Customer id: "+customer.getId());
            model.addAttribute("customer_id", customer.getId());
            break;
        }
        if(bookingObjects!= null){
            for (BookingObject bookingObject : bookingObjects){
                String money = decimalFormat.format(bookingObject.getPrice());
                model.addAttribute("money", money);
                totalPrice = totalPrice + bookingObject.getPrice();
            }
            for (BookingObject bookingObject : bookingObjects){
                model.addAttribute("cityName", bookingObject.getCity_name());
                break;
            }
        }
        String totalMoney = decimalFormat.format(totalPrice);
        System.out.println("Total: " + totalPrice);

        model.addAttribute("cities", cities);
        model.addAttribute("roleUser", roleUser);
        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("bookingSessions", bookingObjects);

        return "/user/booking/cart";
    }

    @PostMapping("/save/cart/job-detail/{id}")
    public String save(Model model, HttpSession session,
                       HttpServletRequest request,
                       RedirectAttributes res,
                       @PathVariable Long id){

        @SuppressWarnings("unchecked")
        List<BookingObject> bookingObjects = (List<BookingObject>) request.getSession().getAttribute("bookingObjects");
        JobDetail jobDetail = jobDetailService.findById(id);
        long user_id = userService.findByEmail(SecurityUtil.getSessionUser()).getId();
        try{
            long labor_id = laborService.findByUserId(user_id).getId();
            if(labor_id != 0){
                if(labor_id == jobDetail.getLabor().getId()){
                    res.addFlashAttribute("failed","This is your job, therefore you are not allowed to book!!!");
                    return "redirect:/your-booking-cart";
                }
            }
        }catch (Exception ignored){}

        if(jobDetail!= null){
            if(bookingService.saveDataToSessionStore(bookingObjects, request, session, jobDetail)){
                System.out.println("Saved  successfully");
                res.addFlashAttribute("success","Saved successfully");
                return "redirect:/your-booking-cart";
            }
        }
        System.out.println("Saved failed!!!");
        res.addFlashAttribute("failed","Saved failed!!!");
        return "redirect:/your-booking-cart";
    }

    @GetMapping("/delete/booking-cart/{index}")
    private String delete(@PathVariable int index, HttpSession session,
                          RedirectAttributes res, Model model,
                          HttpServletRequest request){

        List<BookingObject> bookingObjects = (List<BookingObject>) session.getAttribute("bookingObjects");

        try{
            if (bookingObjects != null && index >= 0 && index < bookingObjects.size()) {
                System.out.println("Remove index: " + index);
                bookingObjects.remove(index);
                session.setAttribute("bookingObjects", bookingObjects);
                if(bookingObjects.isEmpty()){
                    session.removeAttribute("bookingObjects");
                }
                res.addFlashAttribute("deleteSuccess", "Delete item " + (index+1) + " successfully");
            }
        }catch (Exception exception){
            res.addFlashAttribute("deleteFailed", "Delete failed!!!");
        }
        return "redirect:/your-booking-cart";
    }


}
