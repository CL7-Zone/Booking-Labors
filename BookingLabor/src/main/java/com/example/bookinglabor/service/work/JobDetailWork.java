package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.JobDetailMapper;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.model.object.JobDetailObject;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.repo.JobRepo;
import com.example.bookinglabor.repo.LaborRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.service.JobDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobDetailWork implements JobDetailService {

    private JobDetailRepo jobDetailRepo;
    private LaborRepo laborRepo;
    private UserRepo userRepo;
    private JobRepo jobRepo;



    @Override
    public List<JobDetail> findAllJobDetails() {

        List<JobDetail> jobDetails = jobDetailRepo.findAll();

        return jobDetails.stream()
                .map(JobDetailMapper::mapToJobDetail)
                .collect(Collectors
                .toList());
    }

    @Override
    public boolean saveDataToSessionStore(List<JobDetailObject> jobDetailObjects,HttpServletRequest request, HttpSession session, Job job, Long id) {

        if (jobDetailObjects == null) {
            jobDetailObjects = new ArrayList<>();
            request.getSession().setAttribute("jobObjects", jobDetailObjects);
        }

        JobDetailObject jobDetailObject = new JobDetailObject(
                job.getId(), job.getNameJob(), job.getPrice(),
                job.getImageJob(), job.getDescription(),
                job.getCategoryJob().getCategoryName(), id
        );

        List<JobDetailObject> jobDetail = (List<JobDetailObject>) session.getAttribute("jobObjects");

        if(!jobDetail.isEmpty()){
            if(jobDetail.size() > 4){
                System.out.println("Save to cart failed!!!");
                return false;
            }
        }

        jobDetailObjects.add(jobDetailObject);
        request.getSession().setAttribute("jobObjects", jobDetailObjects);

        return true;
    }

    @Override
    public void saveData(HttpSession session) {

        List<JobDetailObject> jobDetailObjs = (List<JobDetailObject>) session.getAttribute("jobObjects");

        for(JobDetailObject item : jobDetailObjs){

            Optional<Job> job = jobRepo.findById(item.getId());
            Optional<Labor> labor = laborRepo.findById(item.getLabor_id());
            if (job.isPresent() && labor.isPresent()) {
                JobDetail jobDetail_ = new JobDetail();
                jobDetail_.setJob(job.get());
                jobDetail_.setLabor(labor.get());
                jobDetailRepo.save(jobDetail_);
            }
        }


    }

    @Override
    public void saveAllDataFromExcel(MultipartFile file) {

    }


}
