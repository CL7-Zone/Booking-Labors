package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking,Long> {



}
