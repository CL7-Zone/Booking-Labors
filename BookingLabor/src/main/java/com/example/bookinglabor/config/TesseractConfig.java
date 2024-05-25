package com.example.bookinglabor.config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {

    public static final int OEM_TESSERACT_ONLY = 1;
    public static final int PSM_AUTO = 3;

    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        tesseract.setDatapath("D:\\download\\tesseract-ocr\\tessdata");
        tesseract.setOcrEngineMode(OEM_TESSERACT_ONLY);
        tesseract.setPageSegMode(PSM_AUTO);
        tesseract.setTessVariable("user_defined_dpi", "300");

        return tesseract;
    }
}
