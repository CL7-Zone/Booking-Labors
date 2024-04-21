package com.example.bookinglabor.service.work.excel;

import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.Job;
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

public class UploadJob {

    public static boolean isValidExcelFile(MultipartFile file){

        String content = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return Objects.equals(file.getContentType(), content);
    }

    public static List<Job> getJobFromExcel(InputStream inputStream){

        List<Job> jobs = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("job");
            int rowIndex =0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Job job = new Job();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
//                        case 0 -> {
//                            if (cell.getCellType() == CellType.NUMERIC) {
//                                job.setId((long) cell.getNumericCellValue());
//                            } else {
//                                System.out.println("Not number");
//                            }
//                        }
                        case 0 -> {
                            if (cell.getCellType() == CellType.STRING) {
                                job.setNameJob(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string");
                            }
                        }

                        case 1 -> {
                            if (cell.getCellType() == CellType.STRING) {
                                job.setImageJob(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string");
                            }
                        }
                        case 2 -> {
                            if (cell.getCellType() == CellType.STRING) {
                                job.setDescription(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string");
                            }
                        }
                        case 3 -> {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                job.setPrice(cell.getNumericCellValue());
                            } else {
                                System.out.println("Not numeric");
                            }
                        }
                        case 4 -> {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                int categoryId = (int) cell.getNumericCellValue();
                                CategoryJob categoryJob = new CategoryJob();
                                categoryJob.setId((long) categoryId);
                                job.setCategoryJob(categoryJob);
                            } else {
                                System.out.println("Not numeric");
                            }
                        }
                        default -> {

                        }
                    }
                    cellIndex++;
                }
                jobs.add(job);
            }
        } catch (IOException e) {

            System.out.println("Error upload service!!!");

            e.getStackTrace();
        }
        return jobs;
    }

}
