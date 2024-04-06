package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.PostMapper;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.repo.*;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.PostService;
import com.example.bookinglabor.service.work.excel.UploadCategoryJob;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostWork implements PostService {

    PostRepo postRepo;
    JobRepo jobRepo;
    CityRepo cityRepo;
    CategoryJobRepo categoryJobRepo;
    UserRepo userRepo;

    @Override
    public List<Post> findAllPosts() {

        List<Post> posts = postRepo.findAll();

        return posts.stream()
                .map(PostMapper::mapToPost)
                .collect(Collectors
                .toList());
    }

    @Override
    public Optional<Post> findById(Long id) {

        Optional<Post> posts = postRepo.findById(id);

        return posts.map(PostMapper::mapToPost);
    }

    @Override
    public List<Post> findPostByUserAccountId(Long user_id) {

        List<Post> posts = postRepo.findPostByUserAccountId(user_id);

        return posts.stream()
                .map(PostMapper::mapToPost)
                .collect(Collectors
                .toList());
    }

    @Override
    public void saveData(Post post, Long city_id, Long category_id, Long job_id) {

        Optional<City> cityOptional = cityRepo.findById(city_id);
        Optional<CategoryJob> categoryJobOptional = categoryJobRepo.findById(category_id);
        Optional<Job> jobOptional = jobRepo.findById(job_id);
        UserAccount user = userRepo.findByEmail(SecurityUtil.getSessionUser());

        cityOptional.ifPresent(post::setCity);
        categoryJobOptional.ifPresent(post::setCategoryJob);
        jobOptional.ifPresent(post::setJob);
        post.setUserAccount(user);
        postRepo.save(post);
        System.out.println(post.getUserAccount().getEmail());

    }


}













