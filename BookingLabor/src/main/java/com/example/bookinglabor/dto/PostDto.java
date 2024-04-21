package com.example.bookinglabor.dto;

import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String you_are;
    private String business_name;
    private String phone_number;
    private String official_address;
    private String image_workplace;
    private String title;
    private int quantity;
    private String pay_form;
    private double max_payroll;
    private double min_payroll;
    private String description;
    private String max_age;
    private String min_age;
    private String education;
    private String experience;
    private String requirement;
    private int re_image;

    private City city;
    private CategoryJob categoryJob;
    private Job job;
    private UserAccount userAccount;
}
