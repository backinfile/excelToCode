package com.backinfile.excelToCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfUtils {
    public enum OrderBy {
        ASC,
        DESC,
    }

    public static String readFileString(String path, String fileName) {
        File file = new File(path, fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                } else {
                    break;
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> parseStringList(String str) {
        return Arrays.asList(str.split(","));
    }

    public static List<Integer> parseIntegerList(String str) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String s : str.split(",")) {
            list.add(Integer.parseInt(s));
        }
        return Collections.unmodifiableList(list);
    }

    public static List<Long> parseLongList(String str) {
        ArrayList<Long> list = new ArrayList<>();
        for (String s : str.split(",")) {
            list.add(Long.parseLong(s));
        }
        return Collections.unmodifiableList(list);
    }

    public static List<Float> parseFloatList(String str) {
        ArrayList<Float> list = new ArrayList<>();
        for (String s : str.split(",")) {
            list.add(Float.parseFloat(s));
        }
        return Collections.unmodifiableList(list);
    }

    public static List<Double> parseDoubleList(String str) {
        ArrayList<Double> list = new ArrayList<>();
        for (String s : str.split(",")) {
            list.add(Double.parseDouble(s));
        }
        return Collections.unmodifiableList(list);
    }

    public static List<Boolean> parseBooleanList(String str) {
        ArrayList<Boolean> list = new ArrayList<>();
        for (String s : str.split(",")) {
            list.add(Boolean.parseBoolean(s));
        }
        return Collections.unmodifiableList(list);
    }

    /**
     * 标记本类是自动生成的，不要修改
     */
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.TYPE)
    public @interface AutoGen {

    }
}
