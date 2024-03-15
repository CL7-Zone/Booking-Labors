package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Job;

public class JobMapper {


    public static Job mapToJob(Job job) {

        Job jopService = Job.builder()
                .id(job.getId())
                .nameJob(job.getNameJob())
                .imageJob(job.getImageJob())
                .price(job.getPrice())
                .createdOn(job.getCreatedOn())
                .updatedOn(job.getUpdatedOn())
                .categoryJob(job.getCategoryJob())
                .build();

        if (jopService != null) {

            return jopService;

        } else {

            System.out.println("" + null);

            return null;
        }
    }

}
