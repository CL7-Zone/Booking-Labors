package com.example.bookinglabor.service;
import com.example.bookinglabor.model.Labor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface LaborService {

    List<Labor> findAllLabors();

    Labor findJobById(Long id);

    Labor findById(Long id);
    Labor findByUserId(Long user_id);

    void saveAllDataFromExcel(MultipartFile file);

    void createLaborByUserIdAndCityId(Long user_id,  Long city_id, Labor labor);

    void update(Long user_id, Long city_id, Labor labor);

}
