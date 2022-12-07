package ${packageName};

import com.alibaba.fastjson.*;
import java.util.*;


/**
 * 此文件是自动生成的 不要手动修改
 */
public class ${className} extends ConfigBase {
    private static volatile Data _instance = null;

    public static ${className} get(${keyType} ${keyName}) {
        return getData().get(${keyName});
    }

    public static Collection<${className}> getAll() {
        return getData().getAll();
    }

<#list fields as field>
    private final ${field.type} ${field.name};
</#list>

    private ${className}(<#list fields as field>${field.type} ${field.name}${field.dot}</#list>) {
<#list fields as field>
        this.${field.name} = ${field.name};
</#list>
    }

<#list fields as field>
    public ${field.type} get${field.upperName}() {
        return ${field.name};
    }

</#list>
    static Data getData() {
        Data current = _instance;
        if (_instance == null) {
            synchronized (Data.class) {
                if (_instance == null) {
                    _instance = new Data();
                    getLogger().info("${className} load!");
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
                    String data = readFileString("${className}.json");
                    String newHash = ConfigManager.md5(data);
                    if (!newHash.equals(_instance.contentHash)) {
                        _instance = null;
                        getLogger().info("${className} changed!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static class Data {
        private final Map<${keyGenType}, ${className}> confMap = new HashMap<>();
        private final String contentHash;

        private Data() {
            String data = readFileString("${className}.json");
            this.contentHash = ConfigManager.md5(data);

            JSONArray jsonArray = JSON.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ${className} _conf = new ${className}(
<#list fields as field>
    <#if !field.array>
                        jsonObject.get${field.innerType}("${field.name}")${field.dot}
    <#else>
                        parse${field.innerType}List(jsonObject.getString("${field.name}"))${field.dot}
    </#if>
</#list>
                );
                this.confMap.put(_conf.${keyName}, _conf);
            }
        }

        public ${className} get(${keyType} ${keyName}) {
            ${className} config = confMap.get(${keyName});
            if (config == null) {
                getLogger().logConfigMissing(${className}.class.getSimpleName(), ${keyName});
            }
            return config;
        }

        public Collection<${className}> getAll() {
            return confMap.values();
        }
    }
}
