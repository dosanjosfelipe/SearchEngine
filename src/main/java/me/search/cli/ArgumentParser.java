package me.search.cli;

import me.search.core.FileScanner;
import me.search.text.Normalizer;
import me.search.text.StopWords;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ArgumentParser {

    public void parse(String[] args) throws IOException {
        final Normalizer normalizer = new Normalizer();
        final StopWords stopWords = new StopWords();
        final FileScanner fileScanner = new FileScanner();

        if (args.length == 0) {
            System.out.print("Use: search <args>");
            return;
        }

        List<String> normalizedArgs = normalizer.normalizeText(args);
        List<String> deletedStopWordsArgs = stopWords.delStopWords(normalizedArgs);

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

        System.out.println("You're searching by: " + searchTerms);
        System.out.println("Isso é o que esta escrito:");
        System.out.println(fileHash);
    }
}
