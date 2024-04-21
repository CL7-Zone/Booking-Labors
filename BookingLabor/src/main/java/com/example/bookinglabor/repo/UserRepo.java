package com.example.bookinglabor.repo;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo  extends JpaRepository<UserAccount, Long>{

    UserAccount findByEmail(String email);

    UserAccount findByEmailAndProvider(String email, EnumComponent provider);

    @Modifying
    @Query(value ="DELETE FROM users_roles WHERE " +
                  "users_roles.user_id IN (SELECT id FROM users WHERE email = :email) " +
                  "AND users_roles.role_id IN (SELECT id FROM roles WHERE name = :role_name)",
            nativeQuery = true)
    void deleteUserRole(@Param("role_name") String role_name, @Param("email") String email);


}
