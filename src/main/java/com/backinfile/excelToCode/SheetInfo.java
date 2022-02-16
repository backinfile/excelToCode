package com.backinfile.excelToCode;

import java.util.ArrayList;
import java.util.Arrays;

public class SheetInfo {
    public String name;
    public String comment;
    public final ArrayList<SheetField> fields = new ArrayList<>();
    public final ArrayList<ArrayList<String>> data = new ArrayList<>();

    public static class SheetField {
        public String name;
        public String command;
        public DataType dataType;
        public int arrayCount;
        public String comment;

        public static SheetField newField(String command, String type, String name, String comment) {
            SheetField field = new SheetField();
            field.command = command;
            field.name = name;
            field.comment = comment;
            while (true) {
                if (type.endsWith("[]")) {
                    field.arrayCount++;
                    type = type.substring(0, type.length() - 2);
                } else {
                    break;
                }
            }
            type = type.toLowerCase();
            for (DataType dataType : DataType.values()) {
                if (dataType.getNames().contains(type)) {
                    field.dataType = dataType;
                }
            }
            if (field.dataType == null) {
                return null;
            }
            return field;
        }
    }

    public enum DataType {
        Int("int", "integer"),
        Long("long"),
        Float("float"),
        Double("double"),
        String("string", "str"),

        ;

        private final ArrayList<String> names = new ArrayList<>();

        DataType(String... names) {
            this.names.addAll(Arrays.asList(names));
        }

        public ArrayList<String> getNames() {
            return names;
        }
    }
}

