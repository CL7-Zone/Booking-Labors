package com.example.bookinglabor.service.work;


import com.example.bookinglabor.mapper.HeaderMapper;
import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Header;
import com.example.bookinglabor.repo.HeaderRepo;
import com.example.bookinglabor.service.HeaderService;
import com.example.bookinglabor.service.work.excel.UploadCategoryJob;
import com.example.bookinglabor.service.work.excel.UploadHeader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HeaderWork implements HeaderService {

    HeaderRepo headerRepo;


    @Override
    public List<Header> findAllHeaders() {

        List<Header> headers = headerRepo.findAll();

        return headers.stream()
                .map(HeaderMapper::mapToHeader)
                .collect(Collectors.toList());
    }

    @Override
    public List<Header> findHeadersByType(String type) {

        List<Header> headers = headerRepo.findHeadersByType(type);

        return headers.stream()
                .map(HeaderMapper::mapToHeader)
                .collect(Collectors.toList());
    }

    @Override
    public void saveAllDataFromExcel(MultipartFile file) {
        if(UploadHeader.isValidExcelFile(file)){
            try {

                List<Header> headers = UploadHeader.getHeaderFromExcel(file.getInputStream());
                this.headerRepo.saveAll(headers);
                System.out.println("Insert data successfully");

            } catch (IOException e) {

                System.out.println("Error save !");
                throw new IllegalArgumentException(e);
            }
        }
    }
}
