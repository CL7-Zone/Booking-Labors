package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.CategoryJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryJobRepo extends JpaRepository<CategoryJob, Long> {

    @Query(value = "SELECT COUNT(*) FROM jobs WHERE jobs.category_id IS NOT NULL", nativeQuery = true)
    Long countJobsWithCategoryId();

    @Query(value = "SELECT COUNT(*) FROM jobs WHERE jobs.category_id = ?1", nativeQuery = true)
    Long countJobsByCategoryId(Long id);

}
