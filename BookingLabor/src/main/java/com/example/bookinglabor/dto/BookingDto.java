package com.example.bookinglabor.dto;


import com.example.bookinglabor.model.Customer;
import com.example.bookinglabor.model.JobDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {


    private Long id;
    private int accept;
    private double total_price;
    @NotEmpty(message = "Total price not be empty!!!")
    private String book_address;
    private String message;
    private String city_name;
    @NotEmpty(message = "Checkin not be empty!!!")
    private Date checkin;
    @NotEmpty(message = "Checkout not be empty!!!")
    private Date checkout;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private JobDetail jobDetail;
    private Customer customer;

}
