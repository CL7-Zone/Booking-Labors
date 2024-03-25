package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.Customer;

public class CustomerMapper {


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
