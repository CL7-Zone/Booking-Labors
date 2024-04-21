package com.example.bookinglabor.mapper;


import com.example.bookinglabor.model.Labor;

public class LaborMapper {

    public static Labor mapToLaborApi(Labor labor) {

        Labor laborUser = Labor.builder()
                .id(labor.getId())
                .full_name(labor.getFull_name())
                .address(labor.getAddress())
                .phone_number(labor.getPhone_number())
                .birthday(labor.getBirthday())
                .status(labor.getStatus())
                .createdOn(labor.getCreatedOn())
                .updatedOn(labor.getUpdatedOn())
                .jobDetails(labor.getJobDetails().stream()
                .map(JobDetailMapper::mapToJobDetailApi).toList())
                .build();

        if (laborUser != null) {

            return laborUser;

        } else {

            System.out.println("" + null);

            return null;
        }
    }


    public static Labor mapToLabor(Labor labor) {

        Labor laborUser = Labor.builder()
                .id(labor.getId())
                .full_name(labor.getFull_name())
                .address(labor.getAddress())
                .phone_number(labor.getPhone_number())
                .birthday(labor.getBirthday())
                .status(labor.getStatus())
                .createdOn(labor.getCreatedOn())
                .updatedOn(labor.getUpdatedOn())
                .city(labor.getCity())
                .userAccount(labor.getUserAccount())
                .build();

        if (laborUser != null) {

            return laborUser;

        } else {

            System.out.println("" + null);

            return null;
        }
    }
}
