package com.backinfile.excelToCode;

import com.backinfile.support.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SheetInfo {
    public String name;
    public String comment;
    public int dataColumnSize; // column size in excel file
    public final ArrayList<SheetField> fields = new ArrayList<>(); // size may less than excel file
    public final ArrayList<ArrayList<Object>> parsedData = new ArrayList<>(); // size may less than excel file

    public static class SheetField {
        public String name;
        public String command;
        public DataType dataType;
        public boolean isArray;
        public String comment;
        public int columnIndex;

        public static SheetField newField(String command, String type, String name, String comment, int columnIndex) {
            SheetField field = new SheetField();
            field.command = command;
            field.name = name;
            field.comment = comment;
            field.columnIndex = columnIndex;

            if (type.endsWith("[]")) {
                field.isArray = true;
                field.dataType = DataType.getType(type.substring(0, type.length() - 2));
            } else {
                field.isArray = false;
                field.dataType = DataType.getType(type);
            }
            if (field.dataType == null) {
                return null;
            }
            return field;
        }

        public Object parseValue(String value) {
            if (isArray) {
                if (Utils.isNullOrEmpty(value)) {
                    return Collections.emptyList();
                }
                if (!value.contains(",")) {
                    return Collections.singletonList(dataType.parseValue(value));
                }
                List<Object> valueList = new ArrayList<>();
                for (String s : value.split(",")) {
                    valueList.add(dataType.parseValue(s));
                }
                return valueList;
            }
            return dataType.parseValue(value);
        }

        public boolean isValidate(String value) {
            if (Utils.isNullOrEmpty(value)) {
                return true;
            }
            return parseValue(value) != null;
        }

        public String getTypeString(boolean gen) {
            if (isArray) {
                return "List<" + dataType.getTypeString(true) + ">";
            }
            return dataType.getTypeString(gen);
        }
    }

    public enum DataType {
        Boolean("bool", "boolean"),
        Int("int", "integer"),
        Long("long"),
        Float("float"),
        Double("double"),
        String("string", "str"),
        ;

        private final List<String> names;

        DataType(String... names) {
            this.names = Arrays.asList(names);
        }

        public static DataType getType(String typeString) {
            for (DataType dataType : values()) {
                for (String name : dataType.names) {
                    if (name.equalsIgnoreCase(typeString)) {
                        return dataType;
                    }
                }
            }
            return null;
        }

        public Object getDefaultValue() {
            switch (this) {
                case Boolean:
                    return false;
                case Int:
                    return 0;
                case Long:
                    return 0L;
                case Float:
                    return 0f;
                case Double:
                    return 0d;
                case String:
                    return "";
            }
            return null;
        }

        public Object parseValue(String data) {
            if (data == null || data.isEmpty()) {
                return getDefaultValue();
            }
            switch (this) {
                case Boolean: {
                    if ("true".equalsIgnoreCase(data)) {
                        return true;
                    } else if ("false".equalsIgnoreCase(data)) {
                        return false;
                    } else if ("1".equalsIgnoreCase(data)) {
                        return true;
                    } else if ("0".equalsIgnoreCase(data)) {
                        return false;
                    }
                    return null;
                }
                case Int:
                    return java.lang.Integer.parseInt(data);
                case Long:
                    return java.lang.Long.parseLong(data);
                case Float:
                    return java.lang.Float.parseFloat(data);
                case Double:
                    return java.lang.Double.parseDouble(data);
                case String:
                    return data;
            }
            return null;
        }

        public String getTypeString(boolean gen) {
            switch (this) {
                case Boolean:
                    return gen ? "Boolean" : "boolean";
                case Int:
                    return gen ? "Integer" : "int";
                case Long:
                    return gen ? "Long" : "long";
                case Float:
                    return gen ? "Float" : "float";
                case Double:
                    return gen ? "Double" : "double";
                case String:
                    return "String";
            }
            return "Void";
        }
    }
}

