package com.example.bookinglabor.controller.api;


import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.model.Apply;
import com.example.bookinglabor.model.FileUpload;
import com.example.bookinglabor.model.Post;
import com.example.bookinglabor.model.test.FileUploadResponse;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.ApplyService;
import com.example.bookinglabor.service.FileUploadService;
import com.example.bookinglabor.service.PostService;
import com.example.bookinglabor.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.ByteArrayResource;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;



@RestController
public class FileUploadApiController {

    private final FileUploadService fileUploadService;
    private final UserService userService;
    private final PostService postService;
    private final ApplyService applyService;
    private static final Logger logger = LoggerFactory.getLogger(FileUploadApiController.class);


    @Autowired
    public FileUploadApiController(FileUploadService fileUploadService, UserService userService, PostService postService, ApplyService applyService) {
        this.fileUploadService = fileUploadService;
        this.userService = userService;
        this.postService = postService;
        this.applyService = applyService;
    }

    @PostMapping("/upload")
    private FileUploadResponse upload(@RequestParam("file") MultipartFile file) throws IOException {

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

    @GetMapping("/download/{fileId}/{apply_id}")
    private ResponseEntity<Resource> download(
            @PathVariable("fileId")String fileId,
            @PathVariable("apply_id") Long apply_id,
            RedirectAttributes flashMessage) throws Exception {

        Long userId = userService.findByEmailAndProvider(SecurityUtil.getSessionUser(), EnumComponent.SIMPLE).getId();
        Optional<Apply> applyOptional = applyService.findById(apply_id);
        try{
            if(applyOptional.isPresent()){

                Apply apply = applyOptional.get();
                Post post = apply.getPost();
                List<Post> postOwners = postService.findPostByUserAccountId(userId);
                boolean isOwner = postOwners.stream().anyMatch(p -> p.getId().equals(post.getId()));
                logger.info("isOwner: "+ isOwner);
                if(isOwner){

                    FileUpload fileUpload = fileUploadService.downloadFile(fileId);
                    return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileUpload.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
         "fileUpload; filename=\""+ fileUpload.getFileName() +"\"")
                    .body(new ByteArrayResource(fileUpload.getData()));
                }else{
                    flashMessage.addFlashAttribute("failed", "KHÔNG THỂ TẢI XUỐNG!");
                    return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/post-manager?Unauthorized")
                    .build();
                }
            }else{
                flashMessage.addFlashAttribute("failed", "KHÔNG TÌM THẤY TÀI NGUYÊN!");
                return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/post-manager?NotFound")
                .build();
            }
        }catch (Exception exception){

            flashMessage.addFlashAttribute("failed", exception.getMessage());
            return ResponseEntity.status(HttpStatus.FOUND)
            .header(HttpHeaders.LOCATION, "/post-manager")
            .build();
        }
    }


}
