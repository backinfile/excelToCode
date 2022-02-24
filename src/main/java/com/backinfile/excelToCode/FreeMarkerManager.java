package com.backinfile.excelToCode;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FreeMarkerManager {
    /**
     * 读取工程外的模板文件生成文件
     */
    public static void formatFile(String filePath, String fileName, Map<String, Object> rootMap, String outPath,
                                  String outFileName) {
        try {
            Configuration config = new Configuration(Configuration.VERSION_2_3_22);
            config.setDefaultEncoding("UTF-8");
            config.setDirectoryForTemplateLoading(new File(filePath));
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            formatTemplate(config, fileName, rootMap, outPath, outFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取工程内的模板文件生成文件
     */
    public static void formatFileInProj(String templatePath, String fileName,
                                        Map<String, Object> rootMap, String outPath, String outFileName) {
        try {
            Configuration config = new Configuration(Configuration.VERSION_2_3_22);
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            config.setClassLoaderForTemplateLoading(FreeMarkerManager.class.getClassLoader(), templatePath);

            formatTemplate(config, fileName, rootMap, outPath, outFileName);
        } catch (Exception e) {
            Log.gen.error("", e);
        }
    }

    private static void formatTemplate(Configuration config, String fileName, Map<String, Object> rootMap,
                                       String outPath, String outFileName) throws Exception {
        File file = new File(outPath, outFileName);
        Log.gen.info("start gen {}", file.getPath());
        file.getParentFile().mkdirs();
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            Template template = config.getTemplate(fileName, "UTF-8");
            template.process(rootMap, writer);
        }
        Log.gen.info("gen {} success\n", file.getPath());
    }


    /**
     * 读取资源文件
     */
    public static String readResource(ClassLoader classLoader, String resourceFile) {
        InputStream in = classLoader.getResourceAsStream(resourceFile);
        if (in == null) {
            return "";
        }
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
                sb.append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
