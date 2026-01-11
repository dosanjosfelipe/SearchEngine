package me.search.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.charset.CodingErrorAction;
import java.util.zip.ZipFile;

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

            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    /* ===================== DOCX ===================== */

    public static boolean isDocxFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] signature = new byte[4];
            if (fis.read(signature) != 4) return false;

            if (signature[0] != 0x50 || signature[1] != 0x4B)
                return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ZipFile zip = new ZipFile(file)) {

            return zip.getEntry("[Content_Types].xml") != null &&
                    zip.getEntry("word/document.xml") != null;
        }
    }
}
