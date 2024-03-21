package com.example.bookinglabor.service;
import com.example.bookinglabor.model.Labor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface LaborService {

    List<Labor> findAllLabors();

    Labor findJobById(Long id);
    Labor findJobByUserId(Long user_id);

    void saveAllDataFromExcel(MultipartFile file);

    void createLaborByUserIdAndCityId(Long user_id,  Long city_id, Labor labor);


}
