package org.korz.log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CollapsedLoggerPatternConverterTest {

    @ParameterizedTest
    @CsvSource({
        "length-none,foo.Bar,foo.Bar",
        "length-0,foo.Bar,Bar",
        "length-1,foo.Bar,f.Bar",
    })
    public void test(String config, String logger, String expected) {
        String configFile = String.format("src/test/resources/%s.xml", config);
        try (LoggerContext context = Configurator.initialize(config, configFile)) {
            context.getLogger(logger).info("test");
        }

        String logFile = String.format("target/logs/%s.txt", config);
        try {
            List<String> lines = Files.readAllLines(Paths.get(logFile));
            Assertions.assertEquals(logger, lines.get(0), "Wrong %logger");
            Assertions.assertEquals(expected, lines.get(1), "Wrong %clogger");
        } catch (IOException e) {
            Assertions.fail("Could not read log file", e);
        }
    }
}
