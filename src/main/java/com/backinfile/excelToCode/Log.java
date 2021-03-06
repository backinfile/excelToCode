package com.backinfile.excelToCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    public static final Logger core = LoggerFactory.getLogger("CORE");
    public static final Logger test = LoggerFactory.getLogger("TEST");
    public static final Logger parser = LoggerFactory.getLogger("PARSER");
    public static final Logger gen = LoggerFactory.getLogger("GEN");
    public static final Logger exporter = LoggerFactory.getLogger("EXPORTER");
}
