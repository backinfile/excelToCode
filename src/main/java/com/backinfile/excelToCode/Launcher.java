package com.backinfile.excelToCode;

import org.apache.commons.cli.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Launcher {
    public static void main(String[] args) {

        // 解析参数
        Options options = new Options();
        {

            Option input = new Option("i", "input", true, "input xlsx file or path");
            input.setRequired(false);
            options.addOption(input);

            Option language = new Option("l", "language", true, "output language, such as json or java");
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

            Option read = new Option("r", "read", false, "read json path if choose java");
            read.setRequired(false);
            options.addOption(read);
        }


        if (args.length == 0) {
            new HelpFormatter().printHelp(Config.PROJECT_NAME, options);
            return;
        }

        CommandLine cmd = null;
        try {
            cmd = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp(Config.PROJECT_NAME, options);
            return;
        }

        // 检查复杂参数
        boolean toExit = false;
        if (cmd.getOptionValue("language").equalsIgnoreCase("java")) {
            if (!cmd.hasOption("package")) {
                toExit = true;
                System.out.println("missing arg package");
            }
            if (!cmd.hasOption("read")) {
                toExit = true;
                System.out.println("missing arg read");
            }
        }
        if (toExit) {
            return;
        }


        try (OPCPackage opcPackage = OPCPackage.open("test.xlsx", PackageAccess.READ)) {
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
                XSSFSheet sheet = workbook.getSheetAt(index);
                SheetInfo sheetInfo = SheetParser.parse(sheet);
                if (sheetInfo != null) {
                    JsonExporter.exportToJson(sheetInfo);
                }
            }
        } catch (Exception e) {
            Log.core.error("", e);
        }
    }
}
