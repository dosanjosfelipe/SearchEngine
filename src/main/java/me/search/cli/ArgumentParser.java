package me.search.cli;

import me.search.indexing.FileScanner;
import me.search.text.Formatter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentParser {

    public void parse(String[] args) throws IOException {
        final Formatter formatter = new Formatter();
        final FileScanner fileScanner = new FileScanner();

        if (args.length == 0) {
            System.out.print("Use: search <args>");
            return;
        }

        List<String> normalizedArgs = formatter.normalizer(args);
        List<String> deletedStopWordsArgs = formatter.stopWords(normalizedArgs);

        if (deletedStopWordsArgs.isEmpty()) {
            System.out.print("Use: search <args> with valid args");
            return;
        }

        StringBuilder query = new StringBuilder();

        for (String arg : deletedStopWordsArgs) {
            query.append(arg).append(" ");
        }

        String searchTerms = query.toString().trim();

        Map<String, String> fileHash = fileScanner.readFilesBase();
        Map<String, String> textHash = new HashMap<>();

        for (Map.Entry<String, String> entry : fileHash.entrySet()) {
            List<String> normalizedText = formatter.normalizer(entry.getValue().split("\\s+"));
            List<String> formattedText = formatter.stopWords(normalizedText);

            textHash.put(entry.getKey(), formattedText.toString());
        }

        System.out.println("You're searching by: " + searchTerms);
        System.out.println("Isso é o que está escrito:");
        for (String values : fileHash.values()) {
            System.out.println(values);
        }
        for (String values : textHash.values()) {
            System.out.println(values);
        }
    }
}
