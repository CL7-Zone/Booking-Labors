package com.example.bookinglabor.mapper;


import com.example.bookinglabor.model.Labor;

public class LaborMapper {


    public static Labor mapToLabor(Labor labor) {

        Labor laborUser = Labor.builder()
                .id(labor.getId())
                .full_name(labor.getFull_name())
                .address(labor.getAddress())
                .phone_number(labor.getPhone_number())
                .birthday(labor.getBirthday())
                .number_experience(labor.getNumber_experience())
                .status(labor.getStatus())
                .address_work_official(labor.getAddress_work_official())
                .free_time_from(labor.getFree_time_from())
                .free_time_to(labor.getFree_time_to())
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
