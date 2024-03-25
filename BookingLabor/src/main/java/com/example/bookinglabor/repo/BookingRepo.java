package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.model.JobDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Long> {

    @Query(value ="SELECT COUNT(*) FROM bookings " +
                  "WHERE accept = 0 " +
                  "AND :checkin <= checkout " +
                  "AND :checkout >= checkin",
                  nativeQuery = true)
    int invalidBooking(@Param("checkin") Date checkin, @Param("checkout") Date checkout);

    @Query(value ="SELECT COUNT(*) FROM bookings " +
                  "WHERE bookings.customer_id = :customer_id",
            nativeQuery = true)
    int countBookingsByCustomerId(@Param("customer_id") Long customer_id);
}
