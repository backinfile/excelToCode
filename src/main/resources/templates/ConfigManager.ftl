package ${packageName};

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

/**
 * 此文件是自动生成的 不要手动修改
 */
public class ConfigManager {
    private static final char[] HEX = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    static volatile Logger logger = Logger.EMPTY_INSTANCE;
    static volatile ConfigFileReader fileReader = null;

    public interface ConfigFileReader {
        String readConfigFile(String configPath, String configName);
    }

    public static class Logger {
        public static final Logger EMPTY_INSTANCE = new Logger();

        public void info(String message) {
        }

        public void error(String message) {
        }

        public void error(String message, Exception e) {
        }

        public void logConfigMissing(String configClassName, Object id) {

        }
    }

    public static void setLogger(Logger logger) {
        ConfigManager.logger = logger;
    }

    public static void setConfigFileReader(ConfigFileReader fileReader) {
        ConfigManager.fileReader = fileReader;
    }

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

    public static String md5(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("get MD5 instance error", e);
        }
        md.update(str.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        char[] result = new char[digest.length * 2];
        for (int i = 0; i < digest.length; i++) {
            byte b = digest[i];
            result[i * 2] = HEX[b & 0xF];
            result[i * 2 + 1] = HEX[(b >>> 4) & 0xF];
        }
        return String.valueOf(result);
    }
}
