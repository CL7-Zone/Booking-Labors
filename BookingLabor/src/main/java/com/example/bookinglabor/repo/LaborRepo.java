package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Labor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LaborRepo extends JpaRepository<Labor, Long> {

    @Query(value = "SELECT * FROM labors WHERE labors.user_id=?1", nativeQuery = true)
    Labor findLaborByUserId(Long user_id);

}
