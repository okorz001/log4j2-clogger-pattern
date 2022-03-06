package org.korz.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.NameAbbreviator;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name = "CollapsedLoggerPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "lo", "clogger", "collapsedlogger" })
@PerformanceSensitive("allocation")
public class CollapsedLoggerPatternConverter extends LogEventPatternConverter {

    private final NameAbbreviator abbreviator;

    /**
     * Obtains an instance of this pattern converter.
     * @param options Configuration options, may be null.
     * @return An instance of this pattern converter.
     * @throws IllegalArgumentException If the first option is not an integer.
     */
    @SuppressWarnings("unused") // called by reflection
    public static CollapsedLoggerPatternConverter newInstance(String[] options) {
        return new CollapsedLoggerPatternConverter(options);
    }

    private CollapsedLoggerPatternConverter(String[] options) {
        // TODO: not sure what these arguments are used for
        super("Logger", "logger");
        if (options != null && options.length > 0) {
            int length;
            try {
                length = Integer.parseInt(options[0].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("length is not an integer", e);
            }
            abbreviator = new CollapsedNameAbbreviator(length);
        } else {
            // the default "abbreviator" doesn't abbreviate anything
            abbreviator = NameAbbreviator.getDefaultAbbreviator();
        }
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        abbreviator.abbreviate(event.getLoggerName(), toAppendTo);
    }
}
