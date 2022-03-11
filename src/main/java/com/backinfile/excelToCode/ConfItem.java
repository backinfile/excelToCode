package com.backinfile.excelToCode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backinfile.support.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfUtils.AutoGen
public class ConfItem {
    public final int sn;
    public final String name;
    public final List<Integer> types;


    private static final String FILE_NAME = "ConfItem.json";

    public static class K {
        public static final String sn = "sn";
        public static final String name = "name";
        public static final String types = "types";
    }

    public ConfItem(int sn, String name, List<Integer> types) {
        this.sn = sn;
        this.name = name;
        this.types = types;
    }

    public static class Data {
        private static Data instance = null;

        public static Data getInstance() {
            if (instance == null) {
                instance = new Data();
            }
            return instance;
        }

        private Map<Integer, ConfItem> confMap = new HashMap<>();
        private int contentHash;

        public void reload(String path) {
            String data = ConfUtils.readFileString(path, FILE_NAME);
            int newHash = Utils.getHashCode(data);
            if (newHash != contentHash) {
                contentHash = newHash;
                confMap = new HashMap<>();
                loadData(data);
            }
        }

        private void loadData(String data) {
            JSONArray jsonArray = JSON.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ConfItem confItem = new ConfItem(jsonObject.getInteger(K.sn), jsonObject.getString(K.name),
                        ConfUtils.parseIntegerList(jsonObject.getString(K.types)));
                confMap.put(confItem.sn, confItem);
            }
        }

        private Data() {

        }
    }

    public static ConfItem get(int sn) {
        return Data.getInstance().confMap.get(sn);
    }


    public Object getFieldValue(String key) {
        switch (key) {
            case K.sn:
                return sn;
            case K.name:
                return name;
            case K.types:
                return types;
        }
        return null;
    }
}
