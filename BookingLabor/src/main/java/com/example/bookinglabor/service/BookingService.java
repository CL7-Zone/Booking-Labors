package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.model.sessionObject.BookingObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface BookingService {


    List<Booking> findAllBookings();

    boolean saveData(Booking booking, HttpSession session, Long customer_id);

    boolean checkInvalidBooking(int size1, int size2, Long jobDetail_id, List<BookingObject> bookingObjects);

    boolean saveDataToSessionStore(List<BookingObject> bookingObjects, HttpServletRequest request, HttpSession session, JobDetail jobDetail);
}
