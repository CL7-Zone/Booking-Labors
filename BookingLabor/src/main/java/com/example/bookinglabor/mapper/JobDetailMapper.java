package com.example.bookinglabor.mapper;

import com.example.bookinglabor.dto.JobDetailDto;
import com.example.bookinglabor.model.JobDetail;

import java.util.stream.Collectors;

public class JobDetailMapper {

    public static JobDetail mapToJobDetailApi(JobDetail jobDetail) {

        JobDetail jopDetailService = JobDetail.builder()
                .id(jobDetail.getId())
                .official_work_address(jobDetail.getOfficial_work_address())
                .education(jobDetail.getEducation())
                .experience(jobDetail.getExperience())
                .commentSkills(jobDetail.getCommentSkills()
                .stream()
                .map(CommentSkillMapper::mapToCommentSkillApi)
                .toList())
                .bookings(jobDetail.getBookings()
                .stream().map(BookingMapper::mapToBooking)
                .collect(Collectors.toList()))
                .createdOn(jobDetail.getCreatedOn())
                .updatedOn(jobDetail.getUpdatedOn())
                .build();

        if (jopDetailService != null) {

            return jopDetailService;

        } else {

            System.out.println("" + null);
            return null;
        }
    }

    public static JobDetail mapToJobDetail(JobDetail jobDetail) {

        JobDetail jopDetailService = JobDetail.builder()
                .id(jobDetail.getId())
                .official_work_address(jobDetail.getOfficial_work_address())
                .education(jobDetail.getEducation())
                .experience(jobDetail.getExperience())
                .createdOn(jobDetail.getCreatedOn())
                .updatedOn(jobDetail.getUpdatedOn())
                .job(jobDetail.getJob())
                .labor(jobDetail.getLabor())
                .bookings(jobDetail.getBookings())
                .commentSkills(jobDetail.getCommentSkills())
                .build();

        if (jopDetailService != null) {

            return jopDetailService;

        } else {

            System.out.println("" + null);
            return null;
        }
    }


    public static JobDetailDto mapToJobDetailDto(JobDetail jobDetail){

        return JobDetailDto.builder()
                .bookings(jobDetail.getBookings()
                .stream().map(BookingMapper::mapToBookingDto)
                .collect(Collectors.toList())).build();
    }
}
