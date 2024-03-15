package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.CategoryJobMapper;
import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.repo.CategoryJobRepo;
import com.example.bookinglabor.service.CategoryJobService;
import com.example.bookinglabor.service.work.excel.UploadCategoryJob;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.bookinglabor.mapper.CategoryJobMapper.mapToCategoryJob;

@Service
@AllArgsConstructor
public class CategoryJobWork implements CategoryJobService {

    private final CategoryJobRepo categoryJobRepo;

    @Override
    public List<CategoryJob> findAllCategoryJobs() {

        List<CategoryJob> categoryJobs = categoryJobRepo.findAll();

        return categoryJobs.stream()
                .map(CategoryJobMapper::mapToCategoryJob)
                .collect(Collectors
                .toList());

    }

    @Override
    public void saveAllDataFromExcel(MultipartFile file) {

        if(UploadCategoryJob.isValidExcelFile(file)){
            try {

                List<CategoryJob> categoryJobs = UploadCategoryJob.getCategoryJobFromExcel(file.getInputStream());
                this.categoryJobRepo.saveAll(categoryJobs);
                System.out.println("Insert data successfully");

            } catch (IOException e) {

                System.out.println("Error save !");
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public CategoryJob findCategoryJobById(Long id) {

        CategoryJob categoryJob = categoryJobRepo.findById(id).get();

        return mapToCategoryJob(categoryJob);
    }

    @Override
    public Long countJob() {

        return categoryJobRepo.countJobsWithCategoryId();
    }

    @Override
    public Long countJobsByCategoryJob(Long id) {

        return categoryJobRepo.countJobsByCategoryId(id);
    }

}
