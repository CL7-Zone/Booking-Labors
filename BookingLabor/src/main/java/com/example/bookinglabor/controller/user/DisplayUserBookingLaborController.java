package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.dto.BookingDto;
import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.model.Customer;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@AllArgsConstructor
@WebListener
public class DisplayUserBookingLaborController implements HttpSessionListener {

    private BookingService bookingService;
    private CustomerService customerService;
    private UserService userService;
    private JobDetailService jobDetailService;
    private SessionService sessionService;
    private CookieService cookieService;
    private SendMailService sendMailService;

    @GetMapping("/your-booking")
    String index(Model model, HttpServletRequest request, HttpSession session,
                 @AuthenticationPrincipal UserDetails userDetails){

        Long user_id = userService.findByEmail(SecurityUtil.getSessionUser()).getId();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roleUser = authorities.stream().map(GrantedAuthority::getAuthority).toList();
        String cancelCount = cookieService.getCookieValue(request, "cancelCount");

        if (cancelCount != null) {
            System.out.println(cancelCount);
            model.addAttribute("cancelCount", cancelCount);
        }
        model.addAttribute("roleUser", roleUser);
        try{
            List<Customer> customers = customerService.findByUserId(user_id);
            Long customerID = 0L;
            for (Customer customer : customers){
                customerID = customer.getId();
                break;
            }
            List<BookingDto> bookings = bookingService.findBookingsByCustomerId(customerID);

            model.addAttribute("bookings", bookings);
            model.addAttribute("customer_id", customerID);

            return "/user/booking/index";
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return "/user/booking/index";
        }
    }

    @PostMapping("/save/cart/booking")
    String save(@RequestParam("customer_id") Long customer_id,
                @RequestParam("checkin") String checkin,
                @RequestParam("checkout") String checkout,
                @RequestParam("total_price") double total_price,
                @RequestParam("book_address") String book_address,
                @RequestParam("city_name") String city_name,
                @RequestParam("message") String message,
                @RequestParam("job_detail_id") Long job_detail_id,
                HttpServletRequest request,
                HttpServletResponse response,
                HttpSession session, @Valid
                RedirectAttributes res) throws ParseException {

        String notify = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Booking booking = new Booking();
        JobDetail jobDetail = jobDetailService.findById(job_detail_id);

        if(message.isEmpty()) message = "Nothing";
        if(checkin.isEmpty() || checkout.isEmpty() ||
           book_address.isEmpty() || city_name.isEmpty()){
            notify = "KHÔNG HỢP LỆ!";
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
        System.out.println("Checkout" + job_detail_id);

        if(total_price < 1.0){
            notify = "VUI LÒNG CHỌN ỨNG VIÊN!";
            res.addFlashAttribute("invalid",notify);
            return "redirect:/your-booking-cart";
        }
        try{
            if(!bookingService.saveData(booking, session, customer_id)){
                notify = "Bạn không được phép tuyển từ " + checkin + " đến "+ checkout + ", Vui lòng chọn thời gian làm việc mới cho ứng viên!";
                res.addFlashAttribute("invalid!", notify);
                return "redirect:/your-booking-cart";
            }
            notify = "Book successfully";
            session.removeAttribute("bookingObjects");
            System.out.println("TUYỂN THÀNH CÔNG");
            res.addFlashAttribute("success",notify);

            return "redirect:/your-booking-cart";
        }catch (Exception exception){
            notify = "TUYỂN THẤT BẠI!";
            System.out.println("Book failed!!!\n Error: " + exception.getMessage());
            res.addFlashAttribute("failed", notify);
            throw exception;
//            return "redirect:/your-booking-cart";
        }

    }

    @PostMapping("/update/booking/{id}")
    private String update(@PathVariable Long id, Model model,
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
                          @RequestParam("labor_email") String labor_email,
                          @RequestParam("labor_name") String labor_name,
                          HttpServletRequest request,
                          HttpSession session,
                          HttpServletResponse response,
                          RedirectAttributes res){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        LocalDateTime cancelDateTime = LocalDateTime.parse(cancel_time, formatter);
        Booking booking = new Booking();
        String email = SecurityUtil.getSessionUser();
        String customerName = customerService.findById(customer_id).getFull_name();
        String cancelCount = cookieService.getCookieValue(request, "cancelCount");
        LocalDateTime current_cancel_time = LocalDateTime.now();
        System.out.println("Current cancel time: " + current_cancel_time);

        System.out.println("COUNT: "+bookingService.invalidCancelBooking(current_cancel_time, id));

        System.out.println("Book id: "+id);

        if (bookingService.countBookingsByCustomer_IdAndId(customer_id, id) < 1) {
            System.out.println("Không được phép!");
            return "redirect:/your-booking";
        }
        if (bookingService.invalidCancelBooking(current_cancel_time, id) > 0) {
            res.addFlashAttribute("notAllowed","Quá thời hạn hủy, bạn không được phép hủy!");
            return "redirect:/your-booking";
        }
        try{
            Date checkinDate = dateFormat.parse(checkin);
            Date checkoutDate = dateFormat.parse(checkout);
            booking.setId(id);
            booking.setStatus(1);
            booking.setAccept(accept);
            booking.setBook_address(book_address);
            booking.setMessage(message);
            booking.setCity_name(city_name);
            booking.setTotal_price(total_price);
            booking.setCheckin(checkinDate);
            booking.setCheckout(checkoutDate);
            booking.setCancel_time(cancelDateTime);
            System.out.println(booking);
            System.out.println(labor_email);
            bookingService.updateById(booking, job_detail_id, customer_id);
            sendMailService.setMailSender(email, "Hủy Hóa đơn số: " + id,
       "Xin chào "+ customerName+",\n\nBạn đã hủy đơn thành công," +
            "\n\nNếu có bất kỳ thắc mắc nào vui lòng liên hệ với chúng tôi." +
            "\n\nBest regards,\nBookingLabor Website");
            sendMailService.setMailSender(labor_email, "Hủy Hóa đơn số: " + id,
        "Xin chào "+ labor_name+ "," +
            "\n\n" + customerName + " đã hủy hóa đơn này!" +
            "\n\nNếu có bất kỳ thắc mắc nào vui lòng liên hệ với chúng tôi." +
            "\n\nBest regards,\nBookingLabor Website");

            res.addFlashAttribute("success","Cancel successfully");
            return "redirect:/your-booking";
        }catch (ParseException  exception){

            res.addFlashAttribute("failed","ERROR: "+exception.getMessage());
            return "redirect:/your-booking";
        }
    }


}
