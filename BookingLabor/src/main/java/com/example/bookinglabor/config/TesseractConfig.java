package com.example.bookinglabor.config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {

    //Hằng số cho OCR Engine Mode (OEM)
    public static final int OEM_TESSERACT_ONLY = 1;

    //Hằng số cho OCR Page Segmentation Mode (PSM)
    public static final int PSM_AUTO = 3;

    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        tesseract.setDatapath("D:\\download\\tesseract-ocr\\tessdata");
        tesseract.setOcrEngineMode(OEM_TESSERACT_ONLY);
        tesseract.setPageSegMode(PSM_AUTO);
        // Đặt độ phân giải cho Tesseract
        tesseract.setTessVariable("user_defined_dpi", "300");

        return tesseract;
    }
}
