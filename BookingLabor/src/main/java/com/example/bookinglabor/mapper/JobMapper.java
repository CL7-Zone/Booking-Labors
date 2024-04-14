package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Job;

import java.util.stream.Collectors;

public class JobMapper {

    public static Job mapToJobApi(Job job) {

        Job jopService = Job.builder()
                .id(job.getId())
                .nameJob(job.getNameJob())
                .imageJob(job.getImageJob())
                .description(job.getDescription())
                .price(job.getPrice())
                .createdOn(job.getCreatedOn())
                .updatedOn(job.getUpdatedOn())
                .jobDetails(job.getJobDetails().stream()
                .map(JobDetailMapper::mapToJobDetailApi)
                .toList())
                .posts(job.getPosts()
                .stream().map(PostMapper::mapToPostApi)
                .collect(Collectors.toList()))
                .build();

        if (jopService != null) {

            return jopService;

        } else {

            System.out.println("" + null);

            return null;
        }
    }


    public static Job mapToJob(Job job) {

        Job jopService = Job.builder()
                .id(job.getId())
                .nameJob(job.getNameJob())
                .imageJob(job.getImageJob())
                .description(job.getDescription())
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
