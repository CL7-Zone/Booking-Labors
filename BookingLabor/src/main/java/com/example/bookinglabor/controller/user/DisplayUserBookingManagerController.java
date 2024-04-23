package com.example.bookinglabor.controller.user;


import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.model.Apply;
import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Controller
@AllArgsConstructor
public class DisplayUserBookingManagerController {

    private BookingService bookingService;
    private UserService userService;
    private LaborService laborService;
    private CustomerService customerService;
    private JobDetailService jobDetailService;
    ApplyService applyService;
    @Autowired
    private SendMailService sendMailService;

    @GetMapping("/booking-manager-by-labor")
    String index(Model model, @AuthenticationPrincipal UserDetails userDetails){


        Long user_id = userService.findByEmailAndProvider(SecurityUtil.getSessionUser(), EnumComponent.SIMPLE).getId();
        Labor labor = laborService.findByUserId(user_id);
        List<JobDetail> jobDetails = jobDetailService.findJobDetailByLaborId(labor.getId());
        List<Apply> appliesByUser = applyService.findAppliesByUserAccountId(user_id);
        LocalDateTime currentAcceptTime = LocalDateTime.now();
        //Lambda function vế trước là tham số đầu vào
        // vế sau là tham số đầu ra
        //truyền tham số ngoài view invalidAcceptFunction.apply(id);
        Function<Long, Integer> invalidAcceptFunction = (id) -> {
            LocalDateTime accept_time = LocalDateTime.now();
            return bookingService.invalidAcceptBooking(accept_time, id);
        };
        System.out.println("Function: "+invalidAcceptFunction);
        System.out.println("Type function: "+invalidAcceptFunction.getClass());

        model.addAttribute("invalidAcceptFunction", invalidAcceptFunction);
        model.addAttribute("jobDetails",jobDetails);
        model.addAttribute("applies",appliesByUser);
        model.addAttribute("labor",labor);

        return "/user/booking/detail";
    }


    @GetMapping("/booking-manager-by-labor/{id}")
    String show(Model model, @PathVariable Long id){

        return "redirect:/booking-manager-by-labor";
    }


    @PostMapping("/accept-booking/{id}")
    String update(@PathVariable Long id,
                  @RequestParam("accept") int accept,
                  @RequestParam("book_address") String book_address,
                  @RequestParam("message") String message,
                  @RequestParam("city_name") String city_name,
                  @RequestParam("total_price") double total_price,
                  @RequestParam("customer_id") Long customer_id,
                  @RequestParam("job_detail_id") Long job_detail_id,
                  @RequestParam("checkin") String checkin,
                  @RequestParam("checkout") String checkout,
                  @RequestParam("cancel_time") String cancel_time,
                  RedirectAttributes res){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        LocalDateTime cancelDateTime = LocalDateTime.parse(cancel_time, formatter);
        Booking booking = new Booking();
        String email = SecurityUtil.getSessionUser();
        String customerName = customerService.findById(customer_id).getFull_name();
        String customerEmail = customerService.findById(customer_id).getUserAccount().getEmail();
        Long user_id = userService.findByEmail(SecurityUtil.getSessionUser()).getId();
        String laborName = laborService.findByUserId(user_id).getFull_name();
        String laborPhone = laborService.findByUserId(user_id).getPhone_number();
        LocalDateTime currentAcceptTime = LocalDateTime.now();
        System.out.println(customerEmail);

        if(bookingService.countBookingsByJobDetailIdAndId(job_detail_id, id) < 1){
            System.out.println("Không được phép!!!");
            return "redirect:/booking-manager-by-labor";
        }
        if(bookingService.invalidAcceptBooking(currentAcceptTime, id) > 0){
            res.addFlashAttribute("failed","QUÁ HẠN ĐỒNG Ý!");
            return "redirect:/booking-manager-by-labor";
        }
        try{
            Date checkinDate = dateFormat.parse(checkin);
            Date checkoutDate = dateFormat.parse(checkout);
            booking.setId(id);
            booking.setStatus(0);
            booking.setAccept(1);
            booking.setBook_address(book_address);
            booking.setMessage(message);
            booking.setCity_name(city_name);
            booking.setTotal_price(total_price);
            booking.setCheckin(checkinDate);
            booking.setCheckout(checkoutDate);
            booking.setCancel_time(cancelDateTime);
            System.out.println(booking);
            bookingService.updateById(booking, job_detail_id, customer_id);
            sendMailService.setMailSender(customerEmail, "Đồng ý Hóa đơn số: " + id,
        "Xin chào "+ customerName+",\n\nHóa đơn đặt của bạn đã được " +
                laborName +" đồng ý" +
                "\n\nSố điện thoại người lao động: " + laborPhone +
                "\n\nHãy nhớ gặp mặt lao động đúng lịch hẹn nhé!" +
                "\n\nNếu có bất kỳ thắc mắc nào vui lòng liên hệ với chúng tôi." +
                "\n\nBest regards,\nBookingLabor Website");

            res.addFlashAttribute("success","ĐỒNG Ý THÀNH CÔNG");
            return "redirect:/booking-manager-by-labor";
        }catch (ParseException exception){

            res.addFlashAttribute("failed","ERROR: "+exception.getMessage());
            return "redirect:/booking-manager-by-labor";
        }
    }



}
