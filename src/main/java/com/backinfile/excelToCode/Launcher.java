package com.backinfile.excelToCode;

import org.apache.commons.cli.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Launcher {
    public static void main(String[] args) {
        // 解析参数
        Options options = new Options();
        Option input = new Option("i", "input", true, "input xlsx file or path");
        input.setRequired(true);
        options.addOption(input);

        Option language = new Option("l", "inputLanguage", true, "output inputLanguage, such as json or java");
        language.setRequired(true);
        options.addOption(language);

        Option output = new Option("o", "output", true, "output path");
        output.setRequired(true);
        options.addOption(output);

        Option packagePath = new Option("p", "package", true, "java package path if choose java");
        packagePath.setRequired(false);
        options.addOption(packagePath);

        Option clear = new Option("c", "clear", false, "clear outputPath before output files");
        clear.setRequired(false);
        options.addOption(clear);

        Option read = new Option("r", "read", true, "read json path if choose java");
        read.setRequired(false);
        options.addOption(read);

        HelpFormatter helpFormatter = new HelpFormatter();
        if (args.length == 0) {
            helpFormatter.printHelp(Config.PROJECT_NAME, options);
            return;
        }

        CommandLine cmd;
        try {
            cmd = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp(Config.PROJECT_NAME, options);
            return;
        }

        // 检查复杂配置
        String inputLanguage = cmd.getOptionValue(language).toLowerCase();
        switch (inputLanguage) {
            case "java": {
                if (!cmd.hasOption(packagePath)) {
                    System.out.println("missing arg package");
                    helpFormatter.printHelp(Config.PROJECT_NAME, options);
                    return;
                }
                if (!cmd.hasOption(read)) {
                    System.out.println("missing arg read");
                    helpFormatter.printHelp(Config.PROJECT_NAME, options);
                    return;
                }
                break;
            }
            case "json": {
                break;
            }
            default: {
                System.out.println("unknown inputLanguage");
                helpFormatter.printHelp(Config.PROJECT_NAME, options);
                return;
            }
        }


        // 读取配置
        Config.ARG_LANGUAGE = inputLanguage;
        Config.ARG_INPUT = cmd.getOptionValue(input);
        Config.ARG_OUTPUT = cmd.getOptionValue(output);
        if (cmd.hasOption(packagePath)) {
            Config.ARG_PACKAGE = cmd.getOptionValue(packagePath);
        }
        Config.ARG_CLEAR = cmd.hasOption(clear);
        if (cmd.hasOption(read)) {
            Config.ARG_READ = cmd.getOptionValue(read);
        }

        if (Config.ARG_CLEAR) {
            File file = new File(Config.ARG_OUTPUT);
            if (file.exists()) {
                try {
                    Files.walk(Paths.get(Config.ARG_OUTPUT))
                            .sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(File::delete);
                    Log.core.info("clear output path {}", Config.ARG_OUTPUT);
                } catch (Exception e) {
                    Log.core.error("error on clear output path", e);
                }
            }
        }

        // 读取excel
        List<SheetInfo> sheetInfos = new ArrayList<>();
        File file = new File(Config.ARG_INPUT);
        findSheet(file, sheetInfos);


        // 开始导出
        switch (Config.ARG_LANGUAGE) {
            case "json": {
                for (SheetInfo sheetInfo : sheetInfos) {
                    try {
                        JsonExporter.exportToJson(sheetInfo);
                    } catch (Exception e) {
                        Log.exporter.error("", e);
                    }
                }
                break;
            }
            case "java": {
                JavaExporter.exportBase();
                List<SheetInfo> successList = new ArrayList<>();
                for (SheetInfo sheetInfo : sheetInfos) {
                    try {
                        JavaExporter.exportToJava(sheetInfo);
                        successList.add(sheetInfo);
                    } catch (Exception e) {
                        Log.exporter.error("", e);
                    }
                }
                JavaExporter.exportManager(successList);
                break;
            }
            default:
                System.out.println("unsupported language");
        }
    }

    private static void findSheet(File file, List<SheetInfo> sheetInfos) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            if (file.getName().endsWith(".xlsx")) {
                try (OPCPackage opcPackage = OPCPackage.open(file.getAbsolutePath(), PackageAccess.READ)) {
                    XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
                    for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
                        XSSFSheet sheet = workbook.getSheetAt(index);
                        SheetInfo sheetInfo = SheetParser.parse(sheet);
                        if (sheetInfo != null) {
                            sheetInfos.add(sheetInfo);
                        }
                    }
                } catch (Exception e) {
                    Log.core.error("", e);
                }
            }
            return;
        }

        File[] files = file.listFiles();
        if (files != null) {
            for (File subFile : files) {
                findSheet(subFile, sheetInfos);
            }
        }
    }
}
