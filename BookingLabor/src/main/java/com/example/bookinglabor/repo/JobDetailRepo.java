package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.JobDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobDetailRepo extends JpaRepository<JobDetail,Long> {

    @Query(value = "SELECT * FROM job_details WHERE job_details.labor_id = ?1",
            nativeQuery = true)
    List<JobDetail>  findJobDetailByLaborId(Long labor_id);

    @Query(value = "SELECT COUNT(*) FROM job_details " +
                   "WHERE job_details.job_id = :job_id AND job_details.labor_id = :labor_id",
            nativeQuery = true)
    int  countJobDetailsByJobId(@Param("job_id") Long job_id, @Param("labor_id") long labor_id);

    @Query(value = "SELECT COUNT(*) FROM job_details WHERE job_details.labor_id = :labor_id",
            nativeQuery = true)
    int  countJobDetailsByLaborId(@Param("labor_id") long labor_id);

}
