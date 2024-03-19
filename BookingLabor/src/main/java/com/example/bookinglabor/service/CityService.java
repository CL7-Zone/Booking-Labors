package com.example.bookinglabor.service;

import com.example.bookinglabor.model.City;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CityService {


    List<City> findAllCities();
    void saveAllDataFromExcel(MultipartFile file);

}
