package com.example.bookinglabor.service.work.excel;

import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Header;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class UploadHeader {

    public static boolean isValidExcelFile(MultipartFile file){

        String content = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return Objects.equals(file.getContentType(), content);
    }

    public static List<Header> getHeaderFromExcel(InputStream inputStream){

        List<Header> headers = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("header");
            int rowIndex =0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Header header = new Header();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 0 -> {
                            if (cell.getCellType() == CellType.STRING) {
                                header.setId(String.valueOf((cell.getNumericCellValue())));
                            } else {
                                System.out.println("Not number");
                            }
                        }
                        case 1 -> {
                            if (cell.getCellType() == CellType.STRING) {
                                header.setName(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string");
                            }
                        }

                        default -> {

                        }
                    }
                    cellIndex++;
                }
                headers.add(header);
            }
        } catch (IOException e) {

            System.out.println("Error upload service!!!");

            e.getStackTrace();
        }
        return headers;
    }




}
