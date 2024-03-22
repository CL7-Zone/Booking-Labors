package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.model.object.JobDetailObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public interface JobDetailService {

    List<JobDetail> findAllJobDetails();

    List<JobDetail> findJobDetailByLaborId(Long id);

    boolean saveDataToSessionStore(List<JobDetailObject> jobDetailObjects, HttpServletRequest request, HttpSession session, Job job, Long id);

    void saveAllDataFromExcel(MultipartFile file);

    void saveData(HttpSession session);

    void deleteById(long id);

    boolean updateById(long id, long job_id, long labor_id, JobDetail jobDetail);

}
