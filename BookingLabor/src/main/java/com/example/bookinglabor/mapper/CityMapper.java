package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.City;

import java.util.stream.Collectors;


public class CityMapper {

    public static City mapToCity(City c) {

        City city = City.builder()
                .id(c.getId())
                .city_name(c.getCity_name())
                .createdOn(c.getCreatedOn())
                .updatedOn(c.getUpdatedOn())
                .labors(c.getLabors()
                .stream()
                .map(LaborMapper::mapToLabor)
                .collect(Collectors
                .toList()))
                .build();

        if (city != null) {

            return city;

        } else {

            System.out.println("" + null);

            return null;
        }
    }
}
