package com.example.bookinglabor.dto;
import com.example.bookinglabor.model.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryJobDto {

    private Long id;
    private String categoryName;
    private String categoryImage;
    private Job job;
}
