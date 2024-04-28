package com.example.bookinglabor.mapper;

import com.example.bookinglabor.dto.BookingDto;
import com.example.bookinglabor.model.Booking;

public class BookingMapper {


    public static BookingDto mapToBookingDto(Booking booking){

        if (booking == null || booking.getJobDetail() == null) {

            System.out.println("booking dto null!!!");

            return null;
        }

        return BookingDto.builder()
                .id(booking.getId())
                .accept(booking.getAccept())
                .status(booking.getStatus())
                .total_price(booking.getTotal_price())
                .book_address(booking.getBook_address())
                .message(booking.getMessage())
                .city_name(booking.getCity_name())
                .checkin(booking.getCheckin())
                .checkout(booking.getCheckout())
                .cancel_time(booking.getCancel_time())
                .createdOn(booking.getCreatedOn())
                .updatedOn(booking.getUpdatedOn())
                .jobDetail(booking.getJobDetail())
                .customer(booking.getCustomer())
                .build();
    }

    public static Booking mapToBooking(Booking book){

        Booking booking = Booking.builder()
                .id(book.getId())
                .accept(book.getAccept())
                .status(book.getStatus())
                .total_price(book.getTotal_price())
                .book_address(book.getBook_address())
                .message(book.getMessage())
                .city_name(book.getCity_name())
                .checkin(book.getCheckin())
                .checkout(book.getCheckout())
                .cancel_time(book.getCancel_time())
                .createdOn(book.getCreatedOn())
                .updatedOn(book.getUpdatedOn())
                .build();

        if (booking != null) {

            return booking;

        } else {

            System.out.println("" + null);

            return null;
        }
    }
}
