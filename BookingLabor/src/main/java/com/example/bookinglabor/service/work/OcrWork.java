package com.example.bookinglabor.service.work;

import com.example.bookinglabor.service.OcrService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class OcrWork implements OcrService {


    private final Tesseract tesseract;

    @Autowired
    public OcrWork(Tesseract tesseract) {
        this.tesseract = tesseract;
    }


    @Override
    public String getImageString(MultipartFile multipartFile) throws TesseractException {

        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
            multipartFile.transferTo(convFile);
            BufferedImage image = ImageIO.read(convFile);
            BufferedImage brightenedImage = preprocessImage(image);
            File preprocessedFile = new File(System
            .getProperty("java.io.tmpdir") + "/preprocessed_" + multipartFile.getOriginalFilename());
            ImageIO.write(brightenedImage, "png", preprocessedFile);

            System.out.println("data: "+tesseract.doOCR(preprocessedFile));

            return tesseract.doOCR(preprocessedFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while processing file";
        }
    }

    @Override
    public BufferedImage preprocessImage(BufferedImage image) {
        BufferedImage brightenedImage = brightenImage(image);
        return convertToGrayscale(brightenedImage);
    }

    @Override
    public BufferedImage brightenImage(BufferedImage image) {
        BufferedImage brightenedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = brightenedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return brightenedImage;
    }

    @Override
    public BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = grayscaleImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return grayscaleImage;
    }


}
