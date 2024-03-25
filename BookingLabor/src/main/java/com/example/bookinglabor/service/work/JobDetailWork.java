package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.JobDetailMapper;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.model.sessionObject.JobDetailObject;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.repo.JobRepo;
import com.example.bookinglabor.repo.LaborRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.JobDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public JobDetail findById(Long id) {

        Optional<JobDetail> optionalJobDetail = jobDetailRepo.findById(id);

        return optionalJobDetail
                .map(JobDetailMapper::mapToJobDetail)
                .orElse(null);
    }

    @Override
    public List<JobDetail> findJobDetailByLaborId(Long labor_id) {

        return jobDetailRepo.findJobDetailByLaborId(labor_id);
    }

    @Override
    public int countJobDetail() {

        return (int) jobDetailRepo.count();
    }

    @Override
    public int countJobDetailByLaborId(long labor_id) {

        return jobDetailRepo.countJobDetailsByLaborId(labor_id);
    }

    @Override
    public int saveDataToSessionStore(List<JobDetailObject> jobDetailObjects,
                                          HttpServletRequest request,
                                          HttpSession session, Job job, Long id) {

        Long user_id = userRepo.findByEmail(SecurityUtil.getSessionUser()).getId();
        Long labor_id = laborRepo.findLaborByUserId(user_id).getId();
        List<JobDetail> jobDetailList = jobDetailRepo.findJobDetailByLaborId(labor_id);
        System.out.println("size: "+jobDetailList.size());

        if (jobDetailObjects == null) {
            jobDetailObjects = new ArrayList<>();
            request.getSession().setAttribute("jobObjects", jobDetailObjects);
        }
        JobDetailObject jobDetailObject = new JobDetailObject(
                job.getId(), job.getNameJob(), job.getPrice(),
                job.getImageJob(), job.getDescription(),
                job.getCategoryJob().getCategoryName(), id
        );
        List<JobDetailObject> jobDetails = (List<JobDetailObject>) session.getAttribute("jobObjects");

        for(JobDetailObject jobCart : jobDetails){
            if(Objects.equals(jobCart.getId(), job.getId())){
                return 0;
            }
        }
        if(!jobDetails.isEmpty()){
            if(jobDetails.size() > 4){
                System.out.println("Save to cart failed!!!");
                return 1;
            }
        }
        if(jobDetailRepo.countJobDetailsByJobId(job.getId(), labor_id) > 0){
            return 2;
        }
        if(jobDetailList.size() >= 5){
            return 3;
        }
        jobDetailObjects.add(jobDetailObject);
        request.getSession().setAttribute("jobObjects", jobDetailObjects);

        return 4;
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
    public boolean updateById(long id, long job_id, long labor_id, JobDetail jobDetail) {

        Optional<Job> jobOptional = jobRepo.findById(job_id);
        Optional<Labor> laborOptional = laborRepo.findById(labor_id);
        if(jobOptional.isPresent()&& laborOptional.isPresent()){
            Job job = jobOptional.get();
            Labor labor = laborOptional.get();
            JobDetail jobDetail_update = JobDetailMapper.mapToJobDetail(jobDetail);
            assert jobDetail_update != null;
            jobDetail_update.setJob(job);
            jobDetail_update.setLabor(labor);
            jobDetailRepo.save(jobDetail_update);

            return true;
        }

        return false;
    }

    @Override
    public void deleteById(long id) {

        jobDetailRepo.deleteById(id);
    }

    @Override
    public void saveAllDataFromExcel(MultipartFile file) {

    }


}
