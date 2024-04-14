package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.Customer;

import java.util.stream.Collectors;

public class CustomerMapper {

    public static Customer mapToCustomerApi(Customer customer) {

        Customer customerUser = Customer.builder()
                .id(customer.getId())
                .full_name(customer.getFull_name())
                .ident_code(customer.getIdent_code())
                .address(customer.getAddress())
                .phone_number(customer.getPhone_number())
                .birthday_customer(customer.getBirthday_customer())
                .bookings(customer.getBookings().stream()
                .map(BookingMapper::mapToBooking)
                .collect(Collectors.toList()))
                .createdOn(customer.getCreatedOn())
                .updatedOn(customer.getUpdatedOn())
                .build();

        if (customerUser != null) {

            return customerUser;

        } else {

            System.out.println("" + null);

            return null;
        }
    }

    public static Customer mapToCustomer(Customer customer) {

        Customer customerUser = Customer.builder()
                .id(customer.getId())
                .full_name(customer.getFull_name())
                .ident_code(customer.getIdent_code())
                .address(customer.getAddress())
                .phone_number(customer.getPhone_number())
                .birthday_customer(customer.getBirthday_customer())
                .createdOn(customer.getCreatedOn())
                .updatedOn(customer.getUpdatedOn())
                .userAccount(customer.getUserAccount())
                .bookings(customer.getBookings())
                .build();

        if (customerUser != null) {

            return customerUser;

        } else {

            System.out.println("" + null);

            return null;
        }
    }
}
