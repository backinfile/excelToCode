package com.backinfile.excelToCode;

import com.backinfile.support.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

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

        public boolean isValidate(String value) {
            return DataValidate.Instance.isValidate(dataType, arrayCount, value);
        }
    }

    private static class DataValidate {
        public static DataValidate Instance = new DataValidate();
        private String value;
        private int index = 0;
        private DataType dataType;

        public boolean isValidate(DataType dataType, int arrayCount, String value) {
            this.index = 0;
            this.dataType = dataType;
            this.value = value;

            if (Utils.isNullOrEmpty(value)) {
                return true;
            }

            if (arrayCount > 0) {
                int leftCount = 0;
                while (value.charAt(index) == '[') {
                    index++;
                    leftCount++;
                }
                index = 0;

                if (arrayCount == leftCount) {
                    matchArray(arrayCount - 1, true);
                } else if (arrayCount == leftCount + 1) {
                    matchArray(arrayCount - 1, false);
                } else {
                    return false;
                }
            } else {
                if (!matchData()) {
                    return false;
                }
            }
            return index == value.length();
        }

        private boolean matchArray(int arrayCount, boolean warp) {
            if (warp) {
                if (!match('[')) {
                    return false;
                }
            }
            if (arrayCount > 0) {
                while (matchArray(arrayCount - 1, true)) {
                    if (!match(',')) {
                        break;
                    }
                }
            } else {
                while (matchData()) {
                    if (!match(',')) {
                        break;
                    }
                }
            }
            if (warp) {
                if (!match(']')) {
                    return false;
                }
            }
            return true;
        }


        private boolean matchData() {
            switch (dataType) {
                case Int:
                case Long: {
                    if (!match(Character::isDigit)) {
                        return false;
                    }
                    while (match(Character::isDigit)) ;
                    return true;
                }
                case Float:
                case Double: {
                    if (!match(Character::isDigit)) {
                        return false;
                    }
                    while (match(Character::isDigit)) ;
                    if (match('.')) {
                        while (match(Character::isDigit)) ;
                    }
                    return true;
                }
                case String: {
                    if (!match(DataValidate::isStringChar)) {
                        return false;
                    }
                    while (match(DataValidate::isStringChar)) ;
                    return true;
                }
            }
            return false;
        }

        private static final String stringChars = ",[]";

        private static boolean isStringChar(char ch) {
            return stringChars.indexOf(ch) < 0;
        }

        private boolean match(char ch) {
            if (index >= value.length()) {
                return false;
            }
            if (value.charAt(index) == ch) {
                index++;
                return true;
            }
            return false;
        }

        private boolean match(Predicate<Character> predicate) {
            if (index >= value.length()) {
                return false;
            }
            if (predicate.test(value.charAt(index))) {
                index++;
                return true;
            }
            return false;
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

