package com.backinfile.test;

import java.util.HashSet;
import java.util.Set;

/**
 * 此文件是自动生成的 不要手动修改
 */
public class ConfManager {

    // 加载全部配置
    public static void loadAll() {
        ConfItem.getData();
    }

    // 热更新
    public static Set<String> reloadAll() {
        Set<String> changed = new HashSet<>();
        if (ConfItem.clearIfChanged()) {
            changed.add("ConfItem");
        }
        return changed;
    }
}
