package org.korz.log4j2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CollapsedNameAbbreviatorTest {

    @ParameterizedTest
    @CsvSource({
        "0,Bar",
        "5,m.s.s.Bar",
        "10,m.s.s.Bar",
        "15,m.s.sample.Bar",
        "16,m.sub.sample.Bar",
        "26,mainPackage.sub.sample.Bar"
    })
    public void test(int length, String expected) {
        // see https://logback.qos.ch/manual/layouts.html#conversionWord
        String logger = "mainPackage.sub.sample.Bar";
        StringBuilder output = new StringBuilder();
        new CollapsedNameAbbreviator(length).abbreviate(logger, output);
        Assertions.assertEquals(expected, output.toString(), "Incorrect abbreviation");
    }
}
