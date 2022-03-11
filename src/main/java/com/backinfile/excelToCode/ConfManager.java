package com.backinfile.excelToCode;

@ConfUtils.AutoGen
public class ConfManager {
    public static void loadConf(String path) {
        ConfItem.Data.getInstance().reload(path);
    }
}
