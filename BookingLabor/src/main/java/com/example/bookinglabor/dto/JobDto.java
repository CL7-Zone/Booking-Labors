package com.example.bookinglabor.dto;

import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JobDto {

    private Long id;
    private String nameJob;
    private String imageJob;
    private String description;
    private double price;
    private List<CategoryJobDto> categories;
    private List<PostDto> posts;
}
