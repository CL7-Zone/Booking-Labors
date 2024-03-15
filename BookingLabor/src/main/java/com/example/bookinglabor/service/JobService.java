package com.example.bookinglabor.service;

import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Job;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JobService {

    List<Job> findAllJobs();
    void saveAllDataFromExcel(MultipartFile file);

}
