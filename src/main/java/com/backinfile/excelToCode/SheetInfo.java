package com.backinfile.excelToCode;

import com.backinfile.support.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SheetInfo {
    public String name;
    public String comment;
    public final ArrayList<SheetField> fields = new ArrayList<>();
    public final ArrayList<ArrayList<String>> data = new ArrayList<>();

    public static class SheetField {
        public String name;
        public String command;
        public DataType dataType;
        public boolean isArray;
        public String comment;

        public static SheetField newField(String command, String type, String name, String comment) {
            SheetField field = new SheetField();
            field.command = command;
            field.name = name;
            field.comment = comment;

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

        public boolean isValidate(String value) {
            if (Utils.isNullOrEmpty(value)) {
                return true;
            }
            if (isArray) {
                for (String v : value.split(",")) {
                    if (!isValidateData(v)) {
                        return false;
                    }
                }
            } else {
                return isValidateData(value);
            }
            return true;
        }

        private static final Pattern PATTERN_INT = Pattern.compile("^\\d+$");
        private static final Pattern PATTERN_FLOAT = Pattern.compile("^\\d+(\\.\\d*)?$");
        private static final Pattern PATTERN_STR = Pattern.compile("^[^,]+$");

        public boolean isValidateData(String value) {
            switch (dataType) {
                case Int:
                case Long:
                    return PATTERN_INT.matcher(value).find();
                case Float:
                case Double:
                    return PATTERN_FLOAT.matcher(value).find();
                case String:
                    return PATTERN_STR.matcher(value).find();
                case Boolean:
                    return "false".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
            }
            return false;
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
    }
}

