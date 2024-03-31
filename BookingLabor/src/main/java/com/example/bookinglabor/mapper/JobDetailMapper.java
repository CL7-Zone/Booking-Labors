package com.example.bookinglabor.mapper;

import com.example.bookinglabor.dto.JobDetailDto;
import com.example.bookinglabor.model.JobDetail;

import java.util.stream.Collectors;

public class JobDetailMapper {

    public static JobDetail mapToJobDetail(JobDetail jobDetail) {

        JobDetail jopDetailService = JobDetail.builder()
                .id(jobDetail.getId())
                .createdOn(jobDetail.getCreatedOn())
                .updatedOn(jobDetail.getUpdatedOn())
                .job(jobDetail.getJob())
                .labor(jobDetail.getLabor())
                .bookings(jobDetail.getBookings())
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
