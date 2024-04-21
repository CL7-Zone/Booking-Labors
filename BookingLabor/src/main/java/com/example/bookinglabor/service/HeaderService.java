package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Header;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HeaderService {

    List<Header> findAllHeaders();

    List<Header> findHeadersByType(String type);

    void saveAllDataFromExcel(MultipartFile file);


}
