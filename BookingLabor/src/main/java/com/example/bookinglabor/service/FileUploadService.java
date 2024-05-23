package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Apply;
import com.example.bookinglabor.model.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {


    public FileUpload saveFile(MultipartFile file) throws IOException;

    FileUpload saveFile(MultipartFile file, Apply apply) throws IOException;

    public FileUpload downloadFile(String fileId) throws Exception;

    void deleteByApplyId(Long apply_id);



}
