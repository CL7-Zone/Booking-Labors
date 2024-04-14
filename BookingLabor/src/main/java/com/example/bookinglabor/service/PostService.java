package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PostService {


    List<Post> findAllPosts();

    List<Post> getApiPosts();

    Optional<Post> findById(Long id);

    List<Post> findPostByUserAccountId(Long user_id);

    int countPostByUserAccount_Email(String email);

    int countPostsByUserAccountId(Long id);

    void deleteById(Long id);

    void saveData(Post post, Long city_id, Long category_id, Long job_id);


}
