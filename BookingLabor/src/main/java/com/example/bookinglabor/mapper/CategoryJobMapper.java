package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.CategoryJob;

import java.util.stream.Collectors;

public class CategoryJobMapper {


    public static CategoryJob mapToCategoryJob(CategoryJob category) {

        CategoryJob categoryJob = CategoryJob.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .categoryImage(category.getCategoryImage())
                .createdOn(category.getCreatedOn())
                .updatedOn(category.getUpdatedOn())
                .jobs(category.getJobs().stream()
                .map(JobMapper::mapToJobApi)
                .collect(Collectors.toList()))
                .posts(category.getPosts().stream()
                .map(PostMapper::mapToPostApi)
                .collect(Collectors.toList()))
                .build();

        if (categoryJob != null) {

            return categoryJob;

        } else {

            System.out.println("" + null);

            return null;
        }
    }

}
