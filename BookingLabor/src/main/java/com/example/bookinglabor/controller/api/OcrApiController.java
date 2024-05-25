package com.example.bookinglabor.controller.api;


import com.example.bookinglabor.service.OcrService;
import net.minidev.json.JSONObject;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OcrApiController {

    private final OcrService ocrService;

    @Autowired
    public OcrApiController(OcrService ocrService) {
        this.ocrService = ocrService;
    }

    @GetMapping("/addImage")
    public ResponseEntity<JSONObject> getImageToString(@RequestParam MultipartFile multipartFile)
    throws TesseractException {

        JSONObject object = new JSONObject();
        object.put("data", ocrService.getImageString(multipartFile));

        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
