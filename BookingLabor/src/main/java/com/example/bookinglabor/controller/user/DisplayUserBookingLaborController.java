package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@AllArgsConstructor
public class DisplayUserBookingLaborController {

    private BookingService bookingService;

    @GetMapping("/your-booking")
    String index(Model model){

        return "/user/booking/index";
    }

    @PostMapping("/save/cart/booking")
    String save(@RequestParam("customer_id") Long customer_id,
                @RequestParam("checkin") String checkin,
                @RequestParam("checkout") String checkout,
                @RequestParam("total_price") double total_price,
                @RequestParam("book_address") String book_address,
                @RequestParam("city_name") String city_name,
                @RequestParam("message") String message,
                HttpSession session, @Valid
                RedirectAttributes res) throws ParseException {

        String notify = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Booking booking = new Booking();
        if(message.isEmpty()) message = "Nothing";
        if(checkin.isEmpty() || checkout.isEmpty() ||
           book_address.isEmpty() || city_name.isEmpty()){
            notify = "Invalid!";
            res.addFlashAttribute("invalid", notify);
            return "redirect:/your-booking-cart";
        }
        booking.setCheckin(formatter.parse(checkin));
        booking.setCheckout(formatter.parse(checkout));
        booking.setTotal_price(total_price);
        booking.setBook_address(book_address);
        booking.setCity_name(city_name);
        booking.setMessage(message);
        System.out.println("Checkin: "+checkin);
        System.out.println("Checkout" + checkout);

        if(total_price < 1.0){
            notify = "Choose our labor, Please!";
            res.addFlashAttribute("invalid",notify);
            return "redirect:/your-booking-cart";
        }
        try{
            if(!bookingService.saveData(booking, session, customer_id)){
                notify = "You are not allowed book " + checkin + " from "+ checkout + ", Please select new check-in and check-out dates.";
                res.addFlashAttribute("invalid", notify);
                return "redirect:/your-booking-cart";
            }
            notify = "Book successfully";
            session.removeAttribute("bookingObjects");
            System.out.println("Book successfully");
            res.addFlashAttribute("success",notify);

            return "redirect:/your-booking-cart";
        }catch (Exception exception){
            notify = "Book failed!!!";
            System.out.println("Book failed!!!");
            res.addFlashAttribute("failed", notify);
//            throw exception;
            return "redirect:/your-booking-cart";
        }

    }


}
