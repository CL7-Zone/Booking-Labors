package com.example.bookinglabor.service.work;

import com.example.bookinglabor.dto.BookingDto;
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
import com.example.bookinglabor.service.SendMailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingWork implements BookingService {

    private BookingRepo bookingRepo;
    private JobDetailRepo jobDetailRepo;
    private CustomerRepo customerRepo;
    private UserRepo userRepo;
    @Autowired
    private SendMailService sendMailService;

    @Override
    public List<Booking> findAllBookings() {

        List<Booking> bookings = bookingRepo.findAll();

        return bookings.stream()
                .map(BookingMapper::mapToBooking)
                .collect(Collectors
                .toList());
    }

    @Override
    public List<BookingDto> findAllBookingDTOs() {

        List<Booking> bookings = bookingRepo.findAll();

        return bookings.stream()
                .map(BookingMapper::mapToBookingDto)
                .collect(Collectors
                .toList());
    }

    @Override
    public List<BookingDto> findBookingsByCustomerId(Long customer_id) {

        List<Booking> bookings = bookingRepo.findBookingsByCustomerId(customer_id);

        return bookings.stream()
                .map(BookingMapper::mapToBookingDto)
                .collect(Collectors
                .toList());
    }

    @Override
    public List<BookingDto> findBookingsByJobDetailId(Long job_detail_id) {

        List<Booking> bookings = bookingRepo.findBookingsByJobDetailId(job_detail_id);

        return bookings.stream()
                .map(BookingMapper::mapToBookingDto)
                .collect(Collectors
                .toList());
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
                jobDetail.getOfficial_work_address(),
                jobDetail.getLabor().getFull_name(),
                jobDetail.getJob().getPrice(),
                jobDetail.getJob().getCategoryJob().getCategoryName(),
                jobDetail.getLabor().getCity().getCity_name(),
                jobDetail.getLabor().getId()
        );
        List<BookingObject> bookingCarts = (List<BookingObject>) session.getAttribute("bookingObjects");

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
        }catch (Exception ignored){}

        bookingObjects.add(bookingObject);
        request.getSession().setAttribute("bookingObjects", bookingObjects);

        return true;
    }

    @Override
    public boolean saveData(Booking booking, HttpSession session, Long customer_id) {

        List<BookingObject> bookingObjects = (List<BookingObject>) session.getAttribute("bookingObjects");
        List<BookingDto> bookings = findAllBookingDTOs();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime cancelLimitTime = currentTime.plusMinutes(2);
        System.out.println("Limit time: "+cancelLimitTime);

        for (BookingObject item : bookingObjects){
            Optional<JobDetail> jobDetail = jobDetailRepo.findById(item.getId());
            Optional<Customer> customer = customerRepo.findById(customer_id);

            if(jobDetail.isPresent() && customer.isPresent()){
                if(bookings != null){
                    for(BookingDto b : bookings){
                        if(Objects.equals(b.getJobDetail().getLabor().getId(),
                            jobDetail.get().getLabor().getId())){
                            if(bookingRepo.invalidBooking(booking.getCheckin(),
                                booking.getCheckout()) > 0) {
                                System.out.println(bookingRepo
                                .invalidBooking(booking.getCheckin(),
                                booking.getCheckout()) +" Invalid!!!");

                                return false;
                            }
                        }
                    }
                }
                Booking book =  new Booking();
                book.setAccept(0);
                book.setStatus(0);
                book.setTotal_price(booking.getTotal_price());
                book.setBook_address(booking.getBook_address());
                book.setCity_name(booking.getCity_name());
                book.setMessage(booking.getMessage());
                book.setCheckin(booking.getCheckin());
                book.setCheckout(booking.getCheckout());
                book.setCancel_time(cancelLimitTime);
                book.setJobDetail(jobDetail.get());
                book.setCustomer(customer.get());
                bookingRepo.save(book);
                sendMailService.setMailSender(jobDetail.get().getLabor().getUserAccount().getEmail()
                , "Hóa đơn số: " + book.getId(), "Xin chào "+ item.getLabor_name() +
                "\n\n" + customer.get().getFull_name()+" đã book bạn," +
                "\n\nNếu có bất kỳ thắc mắc nào vui lòng liên hệ với chúng tôi," +
                "\n\nTruy cập vào đường dẫn này để đồng ý lịch hẹn với người đặt:" +
                "\n\nhttp://localhost:8080/your-booking-labor/" + book.getId() +
                "\n\nBest regards," +
                "\nBookingLabor Website");
            }
        }
        return true;
    }

    @Override
    public void updateById(Booking booking, Long job_detail_id, Long customer_id) {

        Optional<JobDetail> jobDetail = jobDetailRepo.findById(job_detail_id);
        Optional<Customer> customer = customerRepo.findById(customer_id);

        if(jobDetail.isPresent() && customer.isPresent()){
            Booking bookingUpdate = BookingMapper.mapToBooking(booking);
            assert bookingUpdate != null;
            bookingUpdate.setJobDetail(jobDetail.get());
            bookingUpdate.setCustomer(customer.get());
            System.out.println("updated");
            bookingRepo.save(bookingUpdate);
        }


    }

    @Override
    public int invalidCancelBooking(LocalDateTime cancel_time, Long id) {

        return bookingRepo.invalidCancelBooking(cancel_time, id);
    }

    @Override
    public int invalidAcceptBooking(LocalDateTime accept_time, Long id) {

        return  bookingRepo.invalidAcceptBooking(accept_time, id);
    }

    @Override
    public int countByJobDetailId(Long job_detail_id) {

        return bookingRepo.countByJobDetailId(job_detail_id);
    }

}
