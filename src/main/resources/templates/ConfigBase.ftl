package ${packageName};

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

/**
 * 此文件是自动生成的 不要手动修改
 */
public abstract class ConfigBase {
    private static final String JSON_PATH = "${jsonPath}";
    private static final char[] HEX = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static Logger logger = Logger.EMPTY_INSTANCE;

    public static class Logger {
        public static final Logger EMPTY_INSTANCE = new Logger();

        public void info(String message) {
        }

        public void error(String message) {
        }

        public void error(String message, Exception e) {
        }
    }

    @FunctionalInterface
    public interface ReloadListener {
        void invoke();
    }

    public static String readFileString(String fileName) {
        File file = new File(JSON_PATH, fileName);
        if (!file.exists()) {
            logger.error("file " + file.getAbsolutePath() + " not found");
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
            logger.error("error in readFileString", e);
        }
        return sb.toString();
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


    public static List<String> parseStringList(String str) {
        return Arrays.asList(str.substring(1, str.length() - 1).split(","));
    }

    public static List<Integer> parseIntegerList(String str) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String s : str.substring(1, str.length() - 1).split(",")) {
            list.add(Integer.parseInt(s));
        }
        return Collections.unmodifiableList(list);
    }

    public static List<Long> parseLongList(String str) {
        ArrayList<Long> list = new ArrayList<>();
        for (String s : str.substring(1, str.length() - 1).split(",")) {
            list.add(Long.parseLong(s));
        }
        return Collections.unmodifiableList(list);
    }

    public static List<Float> parseFloatList(String str) {
        ArrayList<Float> list = new ArrayList<>();
        for (String s : str.substring(1, str.length() - 1).split(",")) {
            list.add(Float.parseFloat(s));
        }
        return Collections.unmodifiableList(list);
    }

    public static List<Double> parseDoubleList(String str) {
        ArrayList<Double> list = new ArrayList<>();
        for (String s : str.substring(1, str.length() - 1).split(",")) {
            list.add(Double.parseDouble(s));
        }
        return Collections.unmodifiableList(list);
    }

    public static List<Boolean> parseBooleanList(String str) {
        ArrayList<Boolean> list = new ArrayList<>();
        for (String s : str.substring(1, str.length() - 1).split(",")) {
            list.add(Boolean.parseBoolean(s));
        }
        return Collections.unmodifiableList(list);
    }

}
