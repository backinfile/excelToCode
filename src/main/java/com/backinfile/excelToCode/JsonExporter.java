package com.backinfile.excelToCode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JsonExporter {
    public static void exportToJson(SheetInfo sheetInfo) {
        exportToJson(sheetInfo, "Conf" + Utils2.capitalize(sheetInfo.name) + ".json");
    }

    public static void exportToJson(SheetInfo sheetInfo, String fileName) {
        JSONArray jsonArray = new JSONArray();
        for (ArrayList<Object> columnData : sheetInfo.parsedData) {
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < sheetInfo.fields.size(); i++) {
                SheetInfo.SheetField field = sheetInfo.fields.get(i);
                jsonObject.put(field.name, columnData.get(i));
            }
            jsonArray.add(jsonObject);
        }
        File file = new File(Config.ARG_OUTPUT, fileName);
        file.getParentFile().mkdirs();
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(jsonArray.toJSONString());
            writer.flush();
            Log.exporter.info("write success {}", file.getAbsolutePath());
        } catch (Exception e) {
            Log.exporter.error("write file error " + e.getMessage());
        }
    }
}
