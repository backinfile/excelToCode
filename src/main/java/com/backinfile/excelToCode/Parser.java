package com.backinfile.excelToCode;

import com.backinfile.support.Utils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern SheetNamePattern = Pattern.compile("^[A-Za-z0-9_]+$");

    public static SheetInfo parse(XSSFSheet sheet) {
        SheetInfo sheetInfo = parseFiledInfo(sheet);
        if (sheetInfo == null) {
            return null;
        }


        // TODO
        return null;
    }

    private static SheetInfo parseFiledInfo(XSSFSheet sheet) {
        SheetInfo sheetInfo = new SheetInfo();

        String[] sheetNameSplit = sheet.getSheetName().split("\\|");
        if (sheetNameSplit.length == 2) {
            if (SheetNamePattern.matcher(sheetNameSplit[0]).find()) {
                sheetInfo.name = sheetNameSplit[0];
                sheetInfo.comment = sheetNameSplit[1];
            }
        }


        if (Utils.isNullOrEmpty(sheetInfo.name)) {
            Log.parser.info("表\"{}\"不符合命名标准(英文名字+|+中文名字)，已忽略", sheet.getSheetName());
            return null;
        }

        int rowNum = sheet.getLastRowNum();

        if (rowNum < 4) {
            Log.parser.info("表\"{}\"表结构不正确，已忽略", sheet.getSheetName());
            return null;
        }

        int columnNum = Integer.MAX_VALUE;
        for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
            columnNum = Math.min(columnNum, sheet.getRow(rowIndex).getLastCellNum());
        }

        ArrayList<ArrayList<String>> data = getStringData(sheet, 0, 4, 0, columnNum);
        Log.test.info("get data:{}", data.toString());
    }

    // 左闭右开
    private static ArrayList<ArrayList<String>> getStringData(XSSFSheet sheet, int startRow, int endRow, int startColumn, int endColumn) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (int i = startRow; i < endRow; i++) {
            XSSFRow row = sheet.getRow(i);
            ArrayList<String> columnValues = new ArrayList<>();
            for (int j = startColumn; j < endColumn; j++) {
                XSSFCell cell = row.getCell(startColumn);
                columnValues.add(cell.toString());
            }
            data.add(columnValues);
        }
        return data;
    }
}
