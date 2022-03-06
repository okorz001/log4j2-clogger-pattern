package org.korz.log4j2;

import org.apache.logging.log4j.core.pattern.NameAbbreviator;
import org.apache.logging.log4j.util.PerformanceSensitive;

/**
 * A NameAbbreviator that abbreviates packages until the name is short enough.
 */
@PerformanceSensitive("allocation")
public class CollapsedNameAbbreviator extends NameAbbreviator {

    private static final char DEFAULT_DELIMITER = '.';

    private final int max;
    private final char delimiter;

    /**
     * Creates a new abbreviator using the default delimiter.
     * @param max The maximum name length.
     */
    public CollapsedNameAbbreviator(int max) {
        this(max, DEFAULT_DELIMITER);
    }

    /**
     * Creates a new abbreviator using the default delimiter.
     * @param max The maximum name length.
     * @param delimiter The character that separates words.
     */
    public CollapsedNameAbbreviator(int max, char delimiter) {
        this.max = max;
        this.delimiter = delimiter;
    }

    @Override
    public void abbreviate(String original, StringBuilder destination) {
        if (max == 0) {
            // if not found: -1 + 1 = 0, so the entire string is used
            destination.append(original.substring(original.lastIndexOf(delimiter) + 1));
        } else {
            int length = original.length();
            if (length <= max) {
                destination.append(original);
            } else {
                // never abbreviate the last word
                int lastDelimiterIndex = original.lastIndexOf(delimiter);

                int index = 0;
                int overflow = length - max;
                boolean wordBegin = true;
                for (; index < lastDelimiterIndex; index++) {
                    char c = original.charAt(index);
                    if (c == delimiter) {
                        if (overflow <= 0) {
                            // we have removed enough
                            break;
                        }
                        // write delimiter
                        destination.append(c);
                        wordBegin = true;
                    } else if (wordBegin) {
                        // write first letter of abbreviated word
                        destination.append(c);
                        wordBegin = false;
                    } else {
                        // remove remaining letters
                        overflow--;
                    }
                }
                destination.append(original.substring(index));
            }
        }
    }
}
