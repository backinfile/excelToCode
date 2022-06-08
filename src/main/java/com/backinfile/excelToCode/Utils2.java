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
        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
