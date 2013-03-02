package edu.syr.bytecast.amd64.impl.parser;

import java.io.ByteArrayOutputStream;

/**
 *
 * @author sheng
 */
public class ParserTestUtils {

    /**
     * Convert a byte string like "48 89 e5" to a byte array.
     *
     * @param str
     * @return
     */
    public static byte[] stringToByteArray(String str) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int i = 0;
        final int length = str.length();
        if (length == 0) {
            throw new RuntimeException("Empty string.");
        }
        while (i < length) {
            char c = str.charAt(i);
            if (c == ' ') {
                i++;
                continue;
            }
            byte value = 0;
            // Get the first number or abc
            if ('0' <= c && c <= '9') {
                value = (byte) (c - '0');
            } else if ('a' <= c && c <= 'f') {
                value += (byte) (c - 'a') + 10;
            } else if ('A' <= c && c <= 'F') {
                value += (byte) (c - 'A') + 10;
            } else {
                throw new RuntimeException("Unknown chars.");
            }
            // Check length
            i++;
            if (i == length) {
                throw new RuntimeException("Except another num or abc.");
            }
            c = str.charAt(i);
            // Get the second number or abc
            value = (byte) (value << 4);
            if ('0' <= c && c <= '9') {
                value += (byte) (c - '0');
            } else if ('a' <= c && c <= 'f') {
                value += (byte) (c - 'a') + 10;
            } else if ('A' <= c && c <= 'F') {
                value += (byte) (c - 'A') + 10;
            } else {
                throw new RuntimeException("Unknown chars.");
            }
            // Output the value
            out.write(value);
            // Get a blank
            i++;
            if (i != length) {
                c = str.charAt(i);
                if (c == ' ') {
                    i++;
                } else {
                    throw new RuntimeException("Except a blank or the end of the string.");
                }
            } else {
                break;
            }
        }
        return out.toByteArray();
    }
}
