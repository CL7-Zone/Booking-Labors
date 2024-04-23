package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.model.sessionObject.JobDetailObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public interface JobDetailService {

    List<JobDetail> getAllJobDetailsApi();

    List<JobDetail> findAllJobDetails();

    Page<JobDetail> findJobDetailsByNameJob(String nameJob, Pageable pageable);

    Page<JobDetail> findAllByOrderByJobPriceAsc(Pageable pageable);

    Page<JobDetail> findAllByOrderByJobPriceDesc(Pageable pageable);

    JobDetail findById(Long id);

    List<JobDetail> findJobDetailByLaborId(Long id);

    int countJobDetail();

    int countJobDetailByLaborId(long labor_id);

    int countJobDetailsByIdAndLaborId(Long id, Long laborId);


    int saveDataToSessionStore(List<JobDetailObject> jobDetailObjects, HttpServletRequest request, HttpSession session, Job job, Long id);

    void saveAllDataFromExcel(MultipartFile file);

    void saveData(HttpSession session, JobDetail jobDetail);

    boolean deleteById(long id);

    boolean updateById(long job_id, long labor_id, JobDetail jobDetail);

}
