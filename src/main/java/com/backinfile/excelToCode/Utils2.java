package com.backinfile.excelToCode;

import com.backinfile.support.Utils;

public class Utils2 {

    /**
     * 首字母大写
     */
    public static String capitalize(String str) {
        if (Utils.isNullOrEmpty(str)) {
            return "";
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return String.valueOf(chars);
    }
}
