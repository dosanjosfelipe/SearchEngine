package me.search.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.util.*;
import me.search.utils.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

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

        for (String path : filesPath) {

            File file = new File(path);

            if (FileUtils.isPdfFile(file)) {
                String text = pdfReader(file);
                filesHash.put(path, text);

            } else if (FileUtils.isJsonFile(file)) {
                String text = jsonReader(file);
                filesHash.put(path, text);

            } else if (FileUtils.isHtmlFile(file)) {
                String text = htmlReader(file);
                filesHash.put(path, text);

            } else if (FileUtils.isTextFile(file)){
                String text = txtReader(file);
                filesHash.put(path, text);

            }
        }
        return filesHash;
    }

    public String pdfReader(File file) {
        try (PDDocument pdf = PDDocument.load(file)) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(pdf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String txtReader(File file) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());

            }
            return sb.toString();
        }
    }

    public String htmlReader(File file) throws IOException {
        Document doc = Jsoup.parse(file, "UTF-8");

        doc.select("script, style, noscript").remove();

        return doc.text();
    }

    public String jsonReader(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> json = mapper.readValue(file, new TypeReference<Map<String, Object>>() {});

        return mapper.writeValueAsString(json);
    }
}
