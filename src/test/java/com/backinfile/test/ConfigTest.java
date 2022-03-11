package com.backinfile.test;

import com.backinfile.excelToCode.SheetInfo;
import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void testField() {
        assert SheetInfo.SheetField.newField("cs", "int", "sn", "").isValidate("");
        assert SheetInfo.SheetField.newField("cs", "int", "sn", "").isValidate("1243");
        assert SheetInfo.SheetField.newField("cs", "str", "sn", "").isValidate("asf");
        assert SheetInfo.SheetField.newField("cs", "float", "sn", "").isValidate("1243");
        assert SheetInfo.SheetField.newField("cs", "float", "sn", "").isValidate("123.99");
        assert SheetInfo.SheetField.newField("cs", "bool", "sn", "").isValidate("false");
        assert SheetInfo.SheetField.newField("cs", "bool", "sn", "").isValidate("true");
        assert !SheetInfo.SheetField.newField("cs", "bool", "sn", "").isValidate("we");
    }

    @Test
    public void testFieldArray() {
        assert SheetInfo.SheetField.newField("cs", "int[]", "sn", "").isValidate("123");
        assert SheetInfo.SheetField.newField("cs", "int[]", "sn", "").isValidate("123,456");
        assert SheetInfo.SheetField.newField("cs", "int[]", "sn", "").isValidate("123,456");
        assert SheetInfo.SheetField.newField("cs", "str[]", "sn", "").isValidate("123,456");
        assert SheetInfo.SheetField.newField("cs", "str[]", "sn", "").isValidate("哈哈是,wear");
        assert SheetInfo.SheetField.newField("cs", "boolean[]", "sn", "").isValidate("true");
    }
}
