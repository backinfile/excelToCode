package com.backinfile.excelToCode;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Launcher {
    public static void main(String[] args) {
        try (InputStream inputStream = Launcher.class.getClassLoader().getResourceAsStream("test.xlsx")){
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            for(int index = 0; index < workbook.getNumberOfSheets(); index++) {
                XSSFSheet sheet = workbook.getSheetAt(index);
                Log.test.info("find sheet {}", sheet.getSheetName());
                int rowNum = sheet.getLastRowNum();
                for(int rowIndex = 1; rowIndex <= rowNum; rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);
                    int cellNum = row.getLastCellNum();
                    for(int cellIndex = 0; cellIndex < cellNum; cellIndex ++) {
                        XSSFCell cell = row.getCell(cellIndex);
                        String value = cell.toString();
                        Log.test.info("{}", value);
                    }
                    Log.test.info("next row");
                }
            }
        } catch (Exception e) {
            Log.core.error("", e);
        }
    }
}
