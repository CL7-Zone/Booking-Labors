package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Job;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface JobService {

    List<Job> findAllJobs();

    List<Job> findJobsByNameJobContaining(String name_job);

    List<Double> findJobPriceDistinct();

    Job findById(Long id);
    void saveAllDataFromExcel(MultipartFile file);

}
