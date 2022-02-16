package com.backinfile.excelToCode;

import java.util.ArrayList;
import java.util.Arrays;

public class SheetInfo {
    public String name;
    public String comment;
    public final ArrayList<SheetField> fields = new ArrayList<>();
    public final ArrayList<ArrayList<String>> datas = new ArrayList<>();

    public static class SheetField {
        public final String name;
        public final DataType dataType;
        public final int arrayCount;

        public SheetField(String name, DataType dataType, int arrayCount) {
            this.name = name;
            this.dataType = dataType;
            this.arrayCount = arrayCount;
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

