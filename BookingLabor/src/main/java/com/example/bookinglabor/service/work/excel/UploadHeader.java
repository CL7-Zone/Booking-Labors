package com.example.bookinglabor.service.work.excel;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Header;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.SecurityUtil;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class UploadHeader {

    static UserRepo userRepo;


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
                                header.setType(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string");
                            }
                        }
                        case 1 -> {
                            if (cell.getCellType() == CellType.STRING) {
                                header.setName(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string");
                            }
                        }
                        case 2 -> {
                            if (cell.getCellType() == CellType.STRING) {
                                header.setContent(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string");
                            }
                        }
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                UserAccount userAccount = new UserAccount();
                userAccount.setId(29L);
                header.setUserAccount(userAccount);
                headers.add(header);
            }
        } catch (IOException e) {

            System.out.println("Error upload service!!!");

            e.getStackTrace();
        }
        return headers;
    }




}
