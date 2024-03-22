package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.BookingMapper;
import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.repo.BookingRepo;
import com.example.bookinglabor.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingWork implements BookingService {

    private BookingRepo bookingRepo;

    @Override
    public List<Booking> findAllBookings() {

        List<Booking> bookings = bookingRepo.findAll();

//        return bookings.stream().map(BookingMapper::mapToBooking).collect(Collectors.toList());
        return  null;
    }
}
