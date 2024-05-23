package com.example.bookinglabor.controller.api;


import com.example.bookinglabor.model.FileUpload;
import com.example.bookinglabor.model.test.FileUploadResponse;
import com.example.bookinglabor.service.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.ByteArrayResource;
import java.io.IOException;
import org.springframework.core.io.Resource;



@RestController
@AllArgsConstructor
public class FileUploadController {


    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        String downloadUrl = "";
        FileUpload attchment = fileUploadService.saveFile(file);
        downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/download/").path(attchment.getId()).toUriString();

        return new FileUploadResponse(
                    attchment.getFileName(),
                    downloadUrl,
                    file.getContentType(),
                    file.getSize()
        );
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId")String fileId) throws Exception {

        FileUpload fileUpload = fileUploadService.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileUpload.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
    "fileUpload; filename=\""+ fileUpload.getFileName() +"\"")
                .body(new ByteArrayResource(fileUpload.getData()));
    }


}
