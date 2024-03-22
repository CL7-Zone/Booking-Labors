package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.JobDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobDetailRepo extends JpaRepository<JobDetail,Long> {

    @Query(value = "SELECT * FROM job_details WHERE job_details.labor_id = ?1", nativeQuery = true)
    List<JobDetail>  findJobDetailByLaborId(Long labor_id);

}
