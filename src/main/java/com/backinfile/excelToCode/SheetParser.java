package com.backinfile.excelToCode;

import com.backinfile.support.Utils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SheetParser {
    private static final Pattern SheetNamePattern = Pattern.compile("^[A-Za-z0-9_]+$");
    private XSSFSheet sheet;

    public static SheetInfo parse(XSSFSheet sheet) {
        SheetInfo sheetInfo = parseFiledInfo(sheet);
        if (sheetInfo != null) {
            parseConfValue(sheet, sheetInfo);
        }
        return sheetInfo;
    }

    private static SheetInfo parseFiledInfo(XSSFSheet sheet) {
        SheetInfo sheetInfo = new SheetInfo();

        String[] sheetNameSplit = sheet.getSheetName().split("\\|");
        if (sheetNameSplit.length == 2) {
            if (SheetNamePattern.matcher(sheetNameSplit[0]).find()) {
                sheetInfo.name = Utils2.capitalize(sheetNameSplit[0]);
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
        for (int i = 0; i < columnNum; i++) {
            String command = data.get(0).get(i);
            String typeString = data.get(1).get(i);
            String name = data.get(2).get(i);
            String comment = data.get(3).get(i);
            SheetInfo.SheetField field = SheetInfo.SheetField.newField(command, typeString, name, comment);
            if (field == null) {
                Log.parser.error("解析字段{}.{}失败", sheetInfo.name, name);
                return null;
            }
            sheetInfo.fields.add(field);
        }
        if (sheetInfo.fields.isEmpty()) {
            return null;
        }
        if (sheetInfo.fields.get(0).isArray) {
            Log.parser.error("解析失败 表{} 第一个字段不能是数组", sheet.getSheetName());
            return null;
        }
        return sheetInfo;
    }

    private static void parseConfValue(XSSFSheet sheet, SheetInfo sheetInfo) {
        int rowNum = sheet.getLastRowNum();
        int columnNum = sheetInfo.fields.size();
        ArrayList<ArrayList<String>> data = getStringData(sheet, 4, rowNum + 1, 0, columnNum);
        for (int i = 0; i < data.size(); i++) {
            ArrayList<String> columnData = data.get(i);
            boolean validate = true;
            for (int j = 0; j < columnNum; j++) {
                if (!sheetInfo.fields.get(j).isValidate(columnData.get(j))) {
                    validate = false;
                    Log.parser.warn("表{} 第{}行 值{} 数据类型不正确", sheet.getSheetName(), i + 4, columnData.get(j));
                }
            }
            if (validate) {
                sheetInfo.data.add(columnData);
            }
        }

    }

    // 左闭右开
    private static ArrayList<ArrayList<String>> getStringData(XSSFSheet sheet, int startRow, int endRow, int startColumn, int endColumn) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (int i = startRow; i < endRow; i++) {
            XSSFRow row = sheet.getRow(i);
            ArrayList<String> columnValues = new ArrayList<>();
            for (int j = startColumn; j < endColumn; j++) {
                XSSFCell cell = row.getCell(j);
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    columnValues.add(cell.getStringCellValue());
                } else {
                    columnValues.add("");
                }
            }
            data.add(columnValues);
        }
        return data;
    }
}
