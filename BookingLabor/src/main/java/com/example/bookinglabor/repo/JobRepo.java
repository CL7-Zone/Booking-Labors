package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Booking;
import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepo extends JpaRepository<Job, Long> {


    @Query(value ="SELECT DISTINCT price FROM jobs", nativeQuery = true)
    List<Double> findJobPriceDistinct();


}
