package com.example.bookinglabor.service.work.excel;

import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Role;
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

public class UploadRole {

    public static boolean isValidExcelFile(MultipartFile file){

        String content = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return Objects.equals(file.getContentType(), content);
    }

    public static List<Role> getRoleFromExcel(InputStream inputStream){

        List<Role> roles = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("role");
            int rowIndex =0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Role role = new Role();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    if (cellIndex == 0) {
                        if (cell.getCellType() == CellType.STRING) {
                            role.setName(cell.getStringCellValue());
                        } else {
                            System.out.println("Not string");
                        }
                    }
                    cellIndex++;
                }
                roles.add(role);
            }
        } catch (IOException e) {

            System.out.println("Error upload service!!!");

            e.getStackTrace();
        }
        return roles;
    }



}
