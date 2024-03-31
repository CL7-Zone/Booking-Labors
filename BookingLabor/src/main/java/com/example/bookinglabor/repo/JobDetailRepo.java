package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.JobDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface JobDetailRepo extends JpaRepository<JobDetail,Long> {

    @Query(value = "SELECT * FROM job_details WHERE job_details.labor_id = :labor_id",
            nativeQuery = true)
    List<JobDetail>  findJobDetailByLaborId(@Param("labor_id") Long labor_id);

    @Query(value = "SELECT COUNT(*) FROM job_details " +
                   "WHERE job_details.job_id = :job_id AND " +
                   "job_details.labor_id = :labor_id",
            nativeQuery = true)
    int  countJobDetailsByJobId(@Param("job_id") Long job_id, @Param("labor_id") long labor_id);

    @Query(value = "SELECT COUNT(*) FROM job_details WHERE job_details.labor_id = :labor_id",
            nativeQuery = true)
    int  countJobDetailsByLaborId(@Param("labor_id") long labor_id);

//    @Query(value = "SELECT labors.full_name, jobs.name_job, jobs.price, cities.city_name " +
//                    "FROM job_details , jobs , category_jobs , labors , cities " +
//                    "WHERE job_details.job_id = jobs.id " +
//                    "AND job_details.labor_id = labors.id " +
//                    "AND jobs.category_id = category_jobs.id " +
//                    "AND labors.city_id = cities.id " +
//                    "AND jobs.name_job LIKE CONCAT('%', :name_job, '%')",
//            nativeQuery = true)
    Page<JobDetail> findByJob_NameJobContaining(@Param("name_job") String name_job, Pageable pageable);


}
