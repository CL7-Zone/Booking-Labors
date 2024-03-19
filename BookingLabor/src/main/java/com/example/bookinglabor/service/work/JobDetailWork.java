package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.JobDetailMapper;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.service.JobDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobDetailWork implements JobDetailService {

    private JobDetailRepo jobDetailRepo;

    @Override
    public List<JobDetail> findAllJobDetails() {

        List<JobDetail> jobDetails = jobDetailRepo.findAll();

        return jobDetails.stream()
                .map(JobDetailMapper::mapToJobDetail)
                .collect(Collectors
                .toList());
    }

    @Override
    public void saveAllDataFromExcel(MultipartFile file) {

    }
}
