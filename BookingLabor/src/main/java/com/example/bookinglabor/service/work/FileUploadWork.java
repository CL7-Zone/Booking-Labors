package com.example.bookinglabor.service.work;

import com.example.bookinglabor.model.Apply;
import com.example.bookinglabor.model.FileUpload;
import com.example.bookinglabor.repo.FileUploadRepo;
import com.example.bookinglabor.service.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;


@AllArgsConstructor
@Service
public class FileUploadWork implements FileUploadService {


    private FileUploadRepo fileUploadRepo;


    @Override
    public FileUpload saveFile(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")){
                throw new Exception("The file name is invalid" + fileName);
            }
            FileUpload fileUpload = new FileUpload(fileName, file.getContentType(), file.getBytes());
            return fileUploadRepo.save(fileUpload);
        } catch (Exception e) {
            throw new RuntimeException("File could not be save");
        }
    }


    @Override
    public FileUpload saveFile(MultipartFile file, Apply apply) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")){
                throw new Exception("The file name is invalid" + fileName);
            }
            FileUpload fileUpload = new FileUpload(fileName, file.getContentType(), file.getBytes());
            fileUpload.setApply(apply);
            return fileUploadRepo.save(fileUpload);
        } catch (Exception e) {
            throw new RuntimeException("File could not be save!");
        }
    }

    @Override
    public FileUpload downloadFile(String fileId) throws Exception {

        return fileUploadRepo.findById(fileId).orElseThrow(
            ()->new Exception("A file with id: " + fileId + "could not be found!")
        );
    }

    @Transactional
    @Override
    public void deleteByApplyId(Long apply_id) {
        fileUploadRepo.deleteByApply_Id(apply_id);
    }


}
