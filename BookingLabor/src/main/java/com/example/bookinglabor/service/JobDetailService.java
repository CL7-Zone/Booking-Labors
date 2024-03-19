package com.example.bookinglabor.service;

import com.example.bookinglabor.model.JobDetail;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface JobDetailService {

    List<JobDetail> findAllJobDetails();

    void saveAllDataFromExcel(MultipartFile file);

}
