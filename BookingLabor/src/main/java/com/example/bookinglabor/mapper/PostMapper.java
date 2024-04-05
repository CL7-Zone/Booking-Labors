package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.Post;

public class PostMapper {


    public static Post mapToPost(Post post){

        return Post.builder()
                .id(post.getId())
                .business_name(post.getBusiness_name())
                .description(post.getDescription())
                .image_workplace(post.getImage_workplace())
                .experience(post.getExperience())
                .education(post.getEducation())
                .pay_form(post.getPay_form())
                .official_address(post.getOfficial_address())
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
                .build();
    }
}
