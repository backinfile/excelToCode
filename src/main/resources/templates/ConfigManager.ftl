package ${packageName};

import java.util.*;

/**
 * 此文件是自动生成的 不要手动修改
 */
public class ConfigManager {

    // 加载全部配置
    public static void loadAll() {
<#list classList as class>
        ${class.name}.getData();
</#list>
    }

    // 热更新
    public static Set<Class<?>> reloadAll() {
        Set<Class<?>> changed = new HashSet<>();
<#list classList as class>
        if (${class.name}.clearIfChanged()) {
            changed.add(${class.name}.class);
        }
</#list>
        return changed;
    }
}
