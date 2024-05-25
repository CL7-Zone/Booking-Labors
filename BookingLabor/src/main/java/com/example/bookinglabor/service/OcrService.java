package com.example.bookinglabor.service;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface OcrService {

    String getImageString(MultipartFile multipartFile) throws TesseractException;

    BufferedImage preprocessImage(BufferedImage image);

    BufferedImage brightenImage(BufferedImage image);

    BufferedImage convertToGrayscale(BufferedImage image);
}
