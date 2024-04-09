package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepo extends JpaRepository<Apply, Long> {

    List<Apply> findAppliesByUserAccountId(Long user_id);

    List<Apply> findAppliesByPostId(Long POST_ID);

    int countAppliesByPostId(Long postId);

    int countAppliesByUserAccountIdAndPostId(Long userId, Long postId);


}
