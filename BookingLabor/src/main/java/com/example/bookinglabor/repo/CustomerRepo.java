package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {



}
