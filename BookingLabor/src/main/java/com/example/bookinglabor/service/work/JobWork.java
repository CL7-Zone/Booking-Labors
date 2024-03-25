package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.JobMapper;
import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.repo.JobRepo;
import com.example.bookinglabor.service.JobService;
import com.example.bookinglabor.service.work.excel.UploadJob;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobWork implements JobService {

    private final JobRepo jobRepo;

    @Override
    public List<Job> findAllJobs() {

        List<Job> jobs = jobRepo.findAll();

        return jobs.stream()
                .map(JobMapper::mapToJob)
                .collect(Collectors
                .toList());

    }

    @Override
    public Job findById(Long id) {

        Optional<Job> optionalJob = jobRepo.findById(id);
        return optionalJob.map(JobMapper::mapToJob).orElse(null);
    }

    @Override
    public void saveAllDataFromExcel(MultipartFile file) {

        if(UploadJob.isValidExcelFile(file)){
            try {

                List<Job> jobs = UploadJob.getJobFromExcel(file.getInputStream());
                this.jobRepo.saveAll(jobs);
                System.out.println("Insert data successfully");

            } catch (IOException e) {

                System.out.println("Error save !");
                throw new IllegalArgumentException(e);
            }
        }
    }

}
