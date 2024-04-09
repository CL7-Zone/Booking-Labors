package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Header;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeaderRepo extends JpaRepository<Header, Long> {

    List<Header> findHeadersByType(String type);


}
