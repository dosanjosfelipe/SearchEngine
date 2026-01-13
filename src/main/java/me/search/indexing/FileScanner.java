package me.search.indexing;

import java.io.*;
import java.util.*;

public class FileScanner {

    public List<String> listFiles() {
        File folder = new File(System.getProperty("user.dir"));
        List<String> filesPath = new ArrayList<>();
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    filesPath.add(file.getAbsolutePath());
                }
            }
        } else {
            System.out.print("There aren't files in this folder for search");
            return filesPath;
        }
        return filesPath;
    }

    public Map<String, String> readFilesBase() throws IOException {
        List<String> filesPath = listFiles();
        Map<String, String> filesHash = new HashMap<>();
        Readers readers = new Readers();

        for (String path : filesPath) {

            File file = new File(path);

            if (FileVerification.isPdfFile(file)) {
                String text = readers.pdfReader(file);
                filesHash.put(path, text);

            } else if (FileVerification.isJsonFile(file)) {
                String text = readers.jsonReader(file);

                String formattedText = text.replace("{", "").replace("}", "")
                        .replace(":", " ");

                filesHash.put(path, formattedText);

            } else if (FileVerification.isDocxFile(file)) {
                String text = readers.docxReader(file);
                filesHash.put(path, text);

            } else if (FileVerification.isHtmlFile(file)) {
                String text = readers.htmlReader(file);
                filesHash.put(path, text);

            } else if (FileVerification.isTextFile(file)){
                String text = readers.txtReader(file);
                filesHash.put(path, text);

            }
        }
        return filesHash;
    }
}
