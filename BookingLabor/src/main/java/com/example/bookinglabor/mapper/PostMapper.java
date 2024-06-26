package com.example.bookinglabor.mapper;

import com.example.bookinglabor.dto.PostDto;
import com.example.bookinglabor.model.Post;

import java.util.stream.Collectors;

public class PostMapper {


    public static Post mapToPostApi(Post post){

        return Post.builder()
                .id(post.getId())
                .business_name(post.getBusiness_name())
                .phone_number(post.getPhone_number())
                .description(post.getDescription())
                .image_workplace(post.getImage_workplace())
                .experience(post.getExperience())
                .education(post.getEducation())
                .pay_form(post.getPay_form())
                .official_address(post.getOfficial_address())
                .interview_time(post.getInterview_time())
                .title(post.getTitle())
                .quantity(post.getQuantity())
                .requirement(post.getRequirement())
                .max_age(post.getMax_age())
                .min_age(post.getMin_age())
                .you_are(post.getYou_are())
                .re_image(post.getRe_image())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .max_payroll(post.getMax_payroll())
                .min_payroll(post.getMin_payroll())
                .applies(post.getApplies().stream()
                .map(ApplyMapper::mapToApplyApi)
                .collect(Collectors.toList()))
                .build();
    }



    public static Post mapToPost(Post post){

        return Post.builder()
                .id(post.getId())
                .business_name(post.getBusiness_name())
                .phone_number(post.getPhone_number())
                .description(post.getDescription())
                .image_workplace(post.getImage_workplace())
                .experience(post.getExperience())
                .education(post.getEducation())
                .pay_form(post.getPay_form())
                .official_address(post.getOfficial_address())
                .interview_time(post.getInterview_time())
                .title(post.getTitle())
                .quantity(post.getQuantity())
                .requirement(post.getRequirement())
                .max_age(post.getMax_age())
                .min_age(post.getMin_age())
                .you_are(post.getYou_are())
                .re_image(post.getRe_image())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .max_payroll(post.getMax_payroll())
                .min_payroll(post.getMin_payroll())
                .city(post.getCity())
                .categoryJob(post.getCategoryJob())
                .job(post.getJob())
                .userAccount(post.getUserAccount())
                .applies(post.getApplies())
                .build();
    }
}
