package com.example.bookinglabor.model.test;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {

    private String fileName;
    String downloadUrl;
    String fileType;
    long fileSize;



}
