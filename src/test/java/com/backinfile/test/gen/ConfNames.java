package com.backinfile.test.gen;

import com.alibaba.fastjson.*;
import java.util.*;


/**
 * 此文件是自动生成的 不要手动修改
 */
public class ConfNames extends ConfigBase {
    private static volatile Data _instance = null;

    public static ConfNames get(int sn) {
        return getData().get(sn);
    }

    public static Collection<ConfNames> getAll() {
        return getData().getAll();
    }

    private final int sn;
    private final String name;

    private ConfNames(int sn, String name) {
        this.sn = sn;
        this.name = name;
    }

    public int getSn() {
        return sn;
    }

    public String getName() {
        return name;
    }

    static Data getData() {
        Data current = _instance;
        if (_instance == null) {
            synchronized (Data.class) {
                if (_instance == null) {
                    _instance = new Data();
                    logger.info("ConfNames load!");
                }
                current = _instance;
            }
        }
        return current;
    }

    static boolean clearIfChanged() {
        if (_instance != null) {
            synchronized (Data.class) {
                if (_instance != null) {
                    String data = readFileString("ConfNames.json");
                    String newHash = md5(data);
                    if (!newHash.equals(_instance.contentHash)) {
                        _instance = null;
                        logger.info("ConfNames changed!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static class Data {
        private final Map<Integer, ConfNames> confMap = new HashMap<>();
        private final String contentHash;

        private Data() {
            String data = readFileString("ConfNames.json");
            this.contentHash = md5(data);

            JSONArray jsonArray = JSON.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ConfNames _conf = new ConfNames(
                        jsonObject.getInteger("sn"), 
                        jsonObject.getString("name")
                );
                this.confMap.put(_conf.sn, _conf);
            }
        }

        public ConfNames get(int sn) {
            return confMap.get(sn);
        }

        public Collection<ConfNames> getAll() {
            return confMap.values();
        }
    }
}
