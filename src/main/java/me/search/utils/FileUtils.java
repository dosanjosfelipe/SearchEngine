package me.search.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.charset.CodingErrorAction;

public final class FileUtils {

    private static final int PDF_HEADER_SIZE = 5;
    private static final int TEXT_MAX_BYTES = 8192;
    private static final int HTML_MAX_BYTES = 2048;

    private FileUtils() {
        throw new AssertionError("Utility class");
    }

    /* ===================== PDF ===================== */

    public static boolean isPdfFile(File file) {
        try (InputStream in = new FileInputStream(file)) {

            byte[] header = new byte[PDF_HEADER_SIZE];
            int read = in.read(header);

            if (read != PDF_HEADER_SIZE) {
                return false;
            }

            return "%PDF-".equals(
                    new String(header, StandardCharsets.US_ASCII)
            );

        } catch (IOException e) {
            return false;
        }
    }

    /* ===================== TEXT ===================== */

    public static boolean isTextFile(File file) {
        try (InputStream in = new FileInputStream(file)) {

            byte[] buffer = new byte[TEXT_MAX_BYTES];
            int read = in.read(buffer);

            if (read <= 0) {
                return true;
            }

            for (int i = 0; i < read; i++) {
                if (buffer[i] == 0x00) {
                    return false;
                }
            }

            StandardCharsets.UTF_8
                    .newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(buffer, 0, read));

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /* ===================== HTML ===================== */

    public static boolean isHtmlFile(File file) {
        try (InputStream in = new FileInputStream(file)) {

            byte[] buffer = new byte[HTML_MAX_BYTES];
            int read = in.read(buffer);

            if (read <= 0) {
                return false;
            }

            String start = new String(buffer, 0, read, StandardCharsets.UTF_8)
                    .toLowerCase();

            return start.contains("<!doctype html")
                    || start.contains("<html");

        } catch (IOException e) {
            return false;
        }
    }

    /* ===================== JSON ===================== */

    public static boolean isJsonFile(File file) {
        try (JsonParser parser = new JsonFactory().createParser(file)) {

            while (parser.nextToken() != null) {
                // consumo total do stream
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }
}
