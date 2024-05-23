package com.example.bookinglabor.mapper;

import com.example.bookinglabor.dto.BookingDto;
import com.example.bookinglabor.model.Apply;
import com.example.bookinglabor.model.Booking;

public class ApplyMapper {


    public static Apply mapToApplyApi(Apply apply){

        return Apply.builder()
                .id(apply.getId())
                .education(apply.getEducation())
                .experience(apply.getExperience())
                .about(apply.getAbout())
                .image_apply(apply.getImage_apply())
                .build();
    }

    public static Apply mapToApply(Apply apply){

        return Apply.builder()
                .id(apply.getId())
                .education(apply.getEducation())
                .experience(apply.getExperience())
                .about(apply.getAbout())
                .image_apply(apply.getImage_apply())
                .post(apply.getPost())
                .userAccount(apply.getUserAccount())
                .fileUploads(apply.getFileUploads())
                .build();
    }
}
