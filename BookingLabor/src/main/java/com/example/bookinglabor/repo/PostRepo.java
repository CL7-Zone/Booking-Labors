package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo  extends JpaRepository<Post,Long> {


    List<Post> findPostByUserAccountId(Long user_id);

    int countPostByUserAccount_Email(String email);

    int countPostsByUserAccountId(Long id);


}
