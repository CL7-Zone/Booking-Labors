package com.example.bookinglabor.service;



import com.example.bookinglabor.model.Labor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LaborService {

    List<Labor> findAllLabors();

    void saveAllDataFromExcel(MultipartFile file);
}
