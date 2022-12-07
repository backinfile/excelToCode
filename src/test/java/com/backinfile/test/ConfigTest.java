package com.backinfile.test;

import com.backinfile.excelToCode.SheetInfo;
import com.backinfile.test.gen.ConfigManager;
import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void testField() {
        assert SheetInfo.SheetField.newField("cs", "int", "sn", "", 0).isValidate("");
        assert SheetInfo.SheetField.newField("cs", "int", "sn", "", 0).isValidate("1243");
        assert SheetInfo.SheetField.newField("cs", "str", "sn", "", 0).isValidate("asf");
        assert SheetInfo.SheetField.newField("cs", "float", "sn", "", 0).isValidate("1243");
        assert SheetInfo.SheetField.newField("cs", "float", "sn", "", 0).isValidate("123.99");
        assert SheetInfo.SheetField.newField("cs", "bool", "sn", "", 0).isValidate("false");
        assert SheetInfo.SheetField.newField("cs", "bool", "sn", "", 0).isValidate("true");
        assert !SheetInfo.SheetField.newField("cs", "bool", "sn", "", 0).isValidate("we");
    }

    @Test
    public void testFieldArray() {
        assert SheetInfo.SheetField.newField("cs", "int[]", "sn", "", 0).isValidate("123");
        assert SheetInfo.SheetField.newField("cs", "int[]", "sn", "", 0).isValidate("123,456");
        assert SheetInfo.SheetField.newField("cs", "int[]", "sn", "", 0).isValidate("123,456");
        assert SheetInfo.SheetField.newField("cs", "str[]", "sn", "", 0).isValidate("123,456");
        assert SheetInfo.SheetField.newField("cs", "str[]", "sn", "", 0).isValidate("哈哈是,wear");
        assert SheetInfo.SheetField.newField("cs", "boolean[]", "sn", "", 0).isValidate("true");
    }

    @Test
    public void testClass() {
        ConfigManager.loadAll();
        System.out.println();
    }
}
