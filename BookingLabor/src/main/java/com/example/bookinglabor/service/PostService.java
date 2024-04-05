package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PostService {


    List<Post> findAllPosts();

    Optional<Post> findByPostId(Long id);

    List<Post> findPostByUserId(Long user_id);

    void saveData(Post post, Long city_id, Long category_id, Long job_id);


}
