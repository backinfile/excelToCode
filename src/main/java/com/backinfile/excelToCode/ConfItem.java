package com.backinfile.excelToCode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此文件是自动生成的 不要手动修改
 */
public class ConfItem extends ConfigBase {
    private static volatile Data _instance = null;

    public static ConfItem get(int id) {
        return getData().get(id);
    }

    public static Collection<ConfItem> getAll() {
        return getData().getAll();
    }

    private final int sn;
    private final String name;
    private final List<Integer> types;

    private ConfItem(int sn, String name, List<Integer> types) {
        this.sn = sn;
        this.name = name;
        this.types = types;
    }

    public int getSn() {
        return sn;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getTypes() {
        return types;
    }

    static Data getData() {
        Data current = _instance;
        if (_instance == null) {
            synchronized (Data.class) {
                if (_instance == null) {
                    _instance = new Data();
                    current = _instance;
                    getLogger().info("ConfItem load!");
                }
            }
        }
        return current;
    }

    static boolean clearIfChanged() {
        if (_instance != null) {
            synchronized (Data.class) {
                if (_instance != null) {
                    String data = readFileString("ConfItem.json");
                    String newHash = md5(data);
                    if (!newHash.equals(_instance.contentHash)) {
                        _instance = null;
                        getLogger().info("ConfItem changed!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static class Data {
        private final Map<Integer, ConfItem> confMap = new HashMap<>();
        private final String contentHash;

        private Data() {
            String data = readFileString("ConfItem.json");
            this.contentHash = md5(data);

            JSONArray jsonArray = JSON.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ConfItem confItem = new ConfItem(
                        jsonObject.getInteger("sn"),
                        jsonObject.getString("name"),
                        parseIntegerList(jsonObject.getString("types"))
                );
                this.confMap.put(confItem.sn, confItem);
            }
        }

        public ConfItem get(int id) {
            return confMap.get(id);
        }

        public Collection<ConfItem> getAll() {
            return confMap.values();
        }
    }
}
