package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.JobDetail;

public class JobDetailMapper {

    public static JobDetail mapToJobDetail(JobDetail jobDetail) {

        JobDetail jopDetailService = JobDetail.builder()
                .id(jobDetail.getId())
                .createdOn(jobDetail.getCreatedOn())
                .updatedOn(jobDetail.getUpdatedOn())
                .job(jobDetail.getJob())
                .labor(jobDetail.getLabor())
                .build();

        if (jopDetailService != null) {

            return jopDetailService;

        } else {

            System.out.println("" + null);
            return null;
        }
    }
}
