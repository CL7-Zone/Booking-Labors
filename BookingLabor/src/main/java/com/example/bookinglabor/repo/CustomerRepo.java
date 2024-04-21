package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customers WHERE customers.user_id = :user_id",
            nativeQuery = true)
    List<Customer> findByUserId(@Param("user_id") Long user_id);

}
