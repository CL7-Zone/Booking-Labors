package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.BookingMapper;
import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.model.Customer;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.model.sessionObject.BookingObject;
import com.example.bookinglabor.repo.BookingRepo;
import com.example.bookinglabor.repo.CustomerRepo;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingWork implements BookingService {

    private BookingRepo bookingRepo;
    private JobDetailRepo jobDetailRepo;
    private CustomerRepo customerRepo;
    private UserRepo userRepo;


    @Override
    public List<Booking> findAllBookings() {

        List<Booking> bookings = bookingRepo.findAll();

        return bookings.stream()
                .map(BookingMapper::mapToBooking)
                .collect(Collectors
                .toList());
    }

    @Override
    public boolean saveData(Booking booking, HttpSession session, Long customer_id) {

        List<BookingObject> bookingObjects = (List<BookingObject>) session.getAttribute("bookingObjects");

        if(bookingRepo.invalidBooking(booking.getCheckin(), booking.getCheckout()) > 0) {
            System.out.println(bookingRepo.invalidBooking(booking.getCheckin(), booking.getCheckout()) +" Invalid!!!");

            return false;
        }
        for (BookingObject item : bookingObjects){

            Optional<JobDetail> jobDetail = jobDetailRepo.findById(item.getId());
            Optional<Customer> customer = customerRepo.findById(customer_id);
            if(jobDetail.isPresent() && customer.isPresent()){
                Booking book =  new Booking();
                book.setAccept(0);
                book.setTotal_price(booking.getTotal_price());
                book.setBook_address(booking.getBook_address());
                book.setCity_name(booking.getCity_name());
                book.setMessage(booking.getMessage());
                book.setCheckin(booking.getCheckin());
                book.setCheckout(booking.getCheckout());
                book.setJobDetail(jobDetail.get());
                book.setCustomer(customer.get());
                bookingRepo.save(book);
            }
        }
        return true;
    }

    @Override
    public boolean checkInvalidBooking(int size1, int size2, Long jobDetail_id,
                                       List<BookingObject> bookingCarts) {

        return true;
    }

    @Override
    public boolean saveDataToSessionStore(List<BookingObject> bookingObjects,
                                          HttpServletRequest request,
                                          HttpSession session,
                                          JobDetail jobDetail) {

        Long user_id = userRepo.findByEmail(SecurityUtil.getSessionUser()).getId();
        Long customerId = customerRepo.findByUserId(user_id).get(0).getId();

        if (bookingObjects == null) {
            bookingObjects = new ArrayList<>();
            request.getSession().setAttribute("bookingObjects", bookingObjects);
        }
        BookingObject bookingObject = new BookingObject(
                jobDetail.getId(), jobDetail.getJob().getNameJob(),
                jobDetail.getJob().getImageJob(),
                jobDetail.getLabor().getFull_name(),
                jobDetail.getJob().getPrice(),
                jobDetail.getJob().getCategoryJob().getCategoryName(),
                jobDetail.getLabor().getCity().getCity_name(),
                jobDetail.getLabor().getId()
        );
        List<BookingObject> bookingCarts = (List<BookingObject>) session.getAttribute("bookingObjects");
        System.out.println(bookingObjects.size());

//        if(bookingObjects.size() >= 5){
//            return false;
//        }
//        if(bookingCarts.size() + bookingRepo.countBookingsByCustomerId(customerId) > 5){
//            return false;
//        }
        for(BookingObject book : bookingCarts){
            if(Objects.equals(jobDetail.getId(), book.getId())){
                System.out.println("Saved failed!!!");
                return false;
            }
        }
        try{
            for(BookingObject book : bookingCarts){
                if(Objects.equals(jobDetail.getLabor().getId(), book.getLabor_id())){
                    return false;
                }
            }
        }catch (Exception ignored){
        }
        bookingObjects.add(bookingObject);
        request.getSession().setAttribute("bookingObjects", bookingObjects);

        return true;
    }
}
