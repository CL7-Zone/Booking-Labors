package com.example.bookinglabor.service;

import com.example.bookinglabor.dto.BookingDto;
import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.model.sessionObject.BookingObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingService {


    List<Booking> findAllBookings();

    List<BookingDto> findAllBookingDTOs();

    boolean saveData(Booking booking, HttpSession session, Long customer_id);

    boolean checkInvalidBooking(int size1, int size2, Long jobDetail_id, List<BookingObject> bookingObjects);

    boolean saveDataToSessionStore(List<BookingObject> bookingObjects,
                                   HttpServletRequest request,
                                   HttpSession session, JobDetail jobDetail,
                                   Map<String, String> notify);

    List<BookingDto> findBookingsByCustomerId(Long customer_id);

    List<BookingDto> findBookingsByJobDetailId(Long job_detail_id);

    void updateById(Booking booking, Long job_detail_id, Long customer_id);

    int invalidCancelBooking(LocalDateTime cancel_time, Long id);

    int invalidAcceptBooking(LocalDateTime accept_time, Long id);

    int countByJobDetailId(Long job_detail_id);


}
