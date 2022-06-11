package com.backinfile.excelToCode;

import java.util.*;

public class JavaExporter {

    public static void exportToJava(SheetInfo sheetInfo) {
        String className = "Conf" + Utils2.capitalize(sheetInfo.name);

        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("packageName", Config.ARG_PACKAGE);
        rootMap.put("jsonPath", Config.ARG_READ);
        rootMap.put("className", className);

        rootMap.put("keyType", sheetInfo.fields.get(0).getTypeString(false));
        rootMap.put("keyGenType", sheetInfo.fields.get(0).getTypeString(true));
        rootMap.put("keyName", sheetInfo.fields.get(0).name);

        List<Map<String, Object>> fields = new ArrayList<>();
        rootMap.put("fields", fields);


        for (int index = 0; index < sheetInfo.fields.size(); index++) {
            SheetInfo.SheetField sheetField = sheetInfo.fields.get(index);
            Map<String, Object> fieldValueMap = new HashMap<>();
            fields.add(fieldValueMap);

            fieldValueMap.put("name", sheetField.name);
            fieldValueMap.put("upperName", Utils2.capitalize(sheetField.name));
            fieldValueMap.put("type", sheetField.getTypeString(false));
            fieldValueMap.put("dot", index == sheetInfo.fields.size() - 1 ? "" : ", ");
            fieldValueMap.put("array", sheetField.isArray);
            fieldValueMap.put("innerType", sheetField.dataType.getTypeString(true));
        }
        FreeMarkerManager.formatFileInProj(JavaExporter.class.getClassLoader(),
                "templates", "Conf.ftl", rootMap,
                Config.ARG_OUTPUT, className + ".java");
    }

    public static void exportBase() {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("packageName", Config.ARG_PACKAGE);
        rootMap.put("jsonPath", Config.ARG_READ);
        FreeMarkerManager.formatFileInProj(JavaExporter.class.getClassLoader(),
                "templates", "ConfigBase.ftl", rootMap,
                Config.ARG_OUTPUT, "ConfigBase.java");
    }

    public static void exportManager(Collection<SheetInfo> sheetInfoCollection) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("packageName", Config.ARG_PACKAGE);
        rootMap.put("jsonPath", Config.ARG_READ);
        ArrayList<SheetInfo> sheetInfos = new ArrayList<>(sheetInfoCollection);
        sheetInfos.sort(Comparator.comparing(s -> s.name));

        List<Map<String, Object>> classList = new ArrayList<>();
        rootMap.put("classList", classList);
        for (SheetInfo sheetInfo : sheetInfos) {
            Map<String, Object> classInfo = new HashMap<>();
            classList.add(classInfo);
            classInfo.put("name", "Conf" + Utils2.capitalize(sheetInfo.name));
        }

        FreeMarkerManager.formatFileInProj(JavaExporter.class.getClassLoader(),
                "templates", "ConfigManager.ftl", rootMap,
                Config.ARG_OUTPUT, "ConfigManager.java");
    }
}
