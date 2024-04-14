package com.example.bookinglabor.service;

import com.example.bookinglabor.model.CategoryJob;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface CategoryJobService {

    Long countJob();
    List<CategoryJob> findAllCategoryJobs();

    List<CategoryJob> findAllCategoryJobsApi();

    void saveAllDataFromExcel(MultipartFile file);

    Long countJobsByCategoryJob(Long id);

    CategoryJob findCategoryJobById(Long id);

}
