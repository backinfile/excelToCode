package com.backinfile.excelToCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Exporter {
    public static void exportToJson(SheetInfo sheetInfo) {
        exportToJson(sheetInfo, "Conf" + Utils2.capitalize(sheetInfo.name) + ".json");
    }

    public static void exportToJson(SheetInfo sheetInfo, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (ArrayList<String> columnData : sheetInfo.data) {
            sb.append("{");
            for (int i = 0; i < sheetInfo.fields.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                SheetInfo.SheetField field = sheetInfo.fields.get(i);
                sb.append("\"");
                sb.append(field.name);
                sb.append("\": ");
                sb.append(columnData.get(i));
            }
            sb.append("}, ");
        }
        sb.append("]");
        File file = new File(fileName);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file))) {
            writer.write(sb.toString());
        } catch (Exception e) {
            Log.exporter.error("", e);
        }
    }
}
