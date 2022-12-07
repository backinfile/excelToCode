package ${packageName};

import com.alibaba.fastjson.*;
import java.io.*;
import java.util.*;

/**
 * 此文件是自动生成的 不要手动修改
 */
public abstract class ConfigBase {
    private static final String JSON_PATH = "${jsonPath}";

    @FunctionalInterface
    public interface ReloadListener {
        void invoke();
    }

    protected static ConfigManager.Logger getLogger() {
        return ConfigManager.logger;
    }

    protected static String readFileString(String fileName) {
        ConfigManager.ConfigFileReader fileReader = ConfigManager.fileReader;
        if (fileReader != null) {
            return fileReader.readConfigFile(JSON_PATH, fileName);
        }

        File file = new File(JSON_PATH, fileName);
        if (!file.exists()) {
            getLogger().error("file " + file.getAbsolutePath() + " not found");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            getLogger().error("error in readFileString", e);
        }
        return sb.toString();
    }


    protected static List<String> parseStringList(String str) {
        return Collections.unmodifiableList(JSON.parseObject(str, new TypeReference<>() {
        }));
    }

    protected static List<Integer> parseIntegerList(String str) {
        return Collections.unmodifiableList(JSON.parseObject(str, new TypeReference<>() {
        }));
    }

    protected static List<Long> parseLongList(String str) {
        return Collections.unmodifiableList(JSON.parseObject(str, new TypeReference<>() {
        }));
    }

    protected static List<Float> parseFloatList(String str) {
        return Collections.unmodifiableList(JSON.parseObject(str, new TypeReference<>() {
        }));
    }

    protected static List<Double> parseDoubleList(String str) {
        return Collections.unmodifiableList(JSON.parseObject(str, new TypeReference<>() {
        }));
    }

    protected static List<Boolean> parseBooleanList(String str) {
        return Collections.unmodifiableList(JSON.parseObject(str, new TypeReference<>() {
        }));
    }

}
