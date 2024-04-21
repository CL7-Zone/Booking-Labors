package com.example.bookinglabor.service.work.excel;

import com.example.bookinglabor.model.CategoryJob;
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

public class UploadCategoryJob {

    public static boolean isValidExcelFile(MultipartFile file){

        String content = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return Objects.equals(file.getContentType(), content);
    }

    public static List<CategoryJob> getCategoryJobFromExcel(InputStream inputStream){

        List<CategoryJob> categoryJobs = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("categoryJob");
            int rowIndex =0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                CategoryJob category_job = new CategoryJob();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 0 -> {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                category_job.setId((long) cell.getNumericCellValue());
                            } else {
                                System.out.println("Not number");
                            }
                        }
                        case 1 -> {
                            category_job.setCategoryName(cell.getStringCellValue());
                        }
                        case 2 -> {
                            category_job.setCategoryImage(cell.getStringCellValue());
                        }
                        default -> {

                        }
                    }
                    cellIndex++;
                }
                categoryJobs.add(category_job);
            }
        } catch (IOException e) {

            System.out.println("Error upload service!!!");

            e.getStackTrace();
        }
        return categoryJobs;
    }



}
