package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {

    Role findByName(String name);

}
