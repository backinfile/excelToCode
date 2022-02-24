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
    }

    @Test
    public void testFieldArray() {
        assert SheetInfo.SheetField.newField("cs", "int[]", "sn", "").isValidate("123");
        assert SheetInfo.SheetField.newField("cs", "int[]", "sn", "").isValidate("123,456");
        assert SheetInfo.SheetField.newField("cs", "int[]", "sn", "").isValidate("[123,456]");
        assert SheetInfo.SheetField.newField("cs", "int[][]", "sn", "").isValidate("[123,456]");
        assert SheetInfo.SheetField.newField("cs", "int[][]", "sn", "").isValidate("[123,456],[]");
        assert SheetInfo.SheetField.newField("cs", "str[][]", "sn", "").isValidate("[123,456],[]");
        assert SheetInfo.SheetField.newField("cs", "str[][]", "sn", "").isValidate("[as,456],[哈哈哈]");
    }
}
