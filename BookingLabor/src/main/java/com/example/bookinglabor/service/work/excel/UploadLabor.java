package com.example.bookinglabor.service.work.excel;


import com.example.bookinglabor.model.CategoryJob;
import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.model.UserAccount;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UploadLabor {

    public static boolean isValidExcelFile(MultipartFile file){

        String content = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return Objects.equals(file.getContentType(), content);
    }


    public static List<Labor> getLaborFromExcel(InputStream inputStream){

        List<Labor> labors = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("labor");
            int rowIndex =0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Labor labor = new Labor();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 0:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                labor.setId((long) cell.getNumericCellValue());
                            } else {
                                System.out.println("Not number ID");
                            }
                            break;
                        case 1:
                            if (cell.getCellType() == CellType.STRING) {
                                labor.setFull_name(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string full name");
                            }
                            break;
                        case 2:
                            if (cell.getCellType() == CellType.STRING) {
                                labor.setAddress(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string address");
                            }
                            break;
                        case 3:
                            if (cell.getCellType() == CellType.STRING) {
                                labor.setPhone_number(cell.getStringCellValue());
                            } else {
                                System.out.println("Not string phone number");
                            }
                            break;
                        case 5:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                labor.setStatus((int) cell.getNumericCellValue());
                            } else {
                                System.out.println("Not number status");
                            }
                            break;
                        case 6 : {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                Date birthday = cell.getDateCellValue();
                                labor.setBirthday(birthday);
                            } else if (cell.getCellType() == CellType.STRING) {
                                try {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date birthday = dateFormat.parse(cell.getStringCellValue());
                                    labor.setBirthday(birthday);
                                } catch (ParseException e) {
                                    System.out.println("Not date birthday");
                                }
                            } else {
                                System.out.println("Not date birthday");
                            }
                        }
                        break;

                        case 7 : {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                int user_id = (int) cell.getNumericCellValue();
                                UserAccount user = new UserAccount();
                                user.setId((long) user_id);
                                labor.setUserAccount(user);
                            } else {
                                System.out.println("Not numeric");
                            }
                        }
                        break;

                        case 8 : {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                int city_id = (int) cell.getNumericCellValue();
                                City city = new City();
                                city.setId((long) city_id);
                                labor.setCity(city);
                            } else {
                                System.out.println("Not numeric");
                            }
                        }
                        break;

                        default :
                        break;
                    }
                    cellIndex++;
                }
                labors.add(labor);
            }
        } catch (IOException e) {

            System.out.println("Error upload service!!!");

            e.getStackTrace();

        }
        return labors;
    }
}
