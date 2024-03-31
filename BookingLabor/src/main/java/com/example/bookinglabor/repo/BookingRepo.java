package com.example.bookinglabor.repo;

import com.example.bookinglabor.dto.BookingDto;
import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.model.Customer;
import com.example.bookinglabor.model.JobDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Long> {

    @Query(value ="SELECT COUNT(*) FROM bookings " +
                  "WHERE status = 0 " +
                  "AND :checkin <= checkout " +
                  "AND :checkout >= checkin",
                  nativeQuery = true)
    int invalidBooking(@Param("checkin") Date checkin, @Param("checkout") Date checkout);

    @Query(value ="SELECT COUNT(*) FROM bookings " +
                  "WHERE bookings.customer_id = :customer_id",
            nativeQuery = true)
    int countBookingsByCustomerId(@Param("customer_id") Long customer_id);

    @Query(value ="SELECT * FROM bookings WHERE bookings.customer_id = :customer_id",
            nativeQuery = true)
    List<Booking> findBookingsByCustomerId(@Param("customer_id") Long customer_id);

    @Query(value ="SELECT * FROM bookings WHERE bookings.job_detail_id = :job_detail_id",
            nativeQuery = true)
    List<Booking> findBookingsByJobDetailId(@Param("job_detail_id") Long job_detail_id);

    @Query(value ="SELECT COUNT(*) FROM bookings " +
                  "WHERE :cancel_time >= cancel_time AND id = :id",
            nativeQuery = true)
    int invalidCancelBooking(@Param("cancel_time") LocalDateTime cancel_time, @Param("id") Long id);


    @Query(value ="SELECT COUNT(*) FROM bookings " +
                  "WHERE checkin <= :accept_time AND id = :id",
            nativeQuery = true)
    int invalidAcceptBooking(@Param("accept_time") LocalDateTime accept_time, @Param("id") Long id);
}
