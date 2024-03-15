package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<UserAccount, Long>{


    UserAccount findByEmail(String email);

}
