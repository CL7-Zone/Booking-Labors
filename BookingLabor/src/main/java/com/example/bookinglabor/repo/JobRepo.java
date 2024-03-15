package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Job, Long> {




}
