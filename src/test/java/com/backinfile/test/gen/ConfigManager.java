package com.backinfile.test.gen;

import java.util.*;

/**
 * 此文件是自动生成的 不要手动修改
 */
public class ConfigManager {

    // 加载全部配置
    public static void loadAll() {
        ConfNames.getData();
    }

    // 热更新
    public static Set<Class<?>> reloadAll() {
        Set<Class<?>> changed = new HashSet<>();
        if (ConfNames.clearIfChanged()) {
            changed.add(ConfNames.class);
        }
        return changed;
    }
}
