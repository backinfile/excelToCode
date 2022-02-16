package com.backinfile.excelToCode;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Launcher {
    public static void main(String[] args) {
        // , PackageAccess.READ
        try (OPCPackage opcPackage = OPCPackage.open(Launcher.class.getClassLoader().getResourceAsStream("test.xlsx"))) {
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
                XSSFSheet sheet = workbook.getSheetAt(index);
                Parser.parse(sheet);
            }
        } catch (Exception e) {
            Log.core.error("", e);
        }
    }
}
