package com.backinfile.excelToCode;

import com.backinfile.support.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    public static final Logger core = LoggerFactory.getLogger("CORE");
    public static final Logger test = LoggerFactory.getLogger("TEST");
    public static final Logger parser = LoggerFactory.getLogger("PARSER");
    public static final Logger gen = LoggerFactory.getLogger("GEN");
    public static final Logger exporter = LoggerFactory.getLogger("EXPORTER");

//    private static class LoggerFactory {
//        public static Logger getLogger(String name) {
//            return new Logger(name);
//        }
//    }
//
//    public static class Logger {
//        private final String name;
//
//        public Logger(String name) {
//            this.name = name;
//        }
//
//        public void info(String s, Object... args) {
//            System.out.println("[INFO] [" + name + "] " + Utils.format(s, args));
//        }
//
//        public void warn(String s, Object... args) {
//            System.out.println("[WARN] [" + name + "] " + Utils.format(s, args));
//        }
//
//        public void error(String s) {
//            System.out.println("[ERROR] [" + name + "] " + s);
//        }
//
//        public void error(String s, Throwable e) {
//            System.out.println("[ERROR] [" + name + "] " + s + "\n" + Utils.getStackTraceAsString(e));
//        }
//
//        public void error(String s, Object... args) {
//            System.out.println("[ERROR][" + name + "] " + Utils.format(s, args));
//        }
//    }
}
