package me.search.cli;

import me.search.text.Normalizer;
import me.search.text.StopWords;

import java.util.List;

public class ArgumentParser {

    public void parse(String[] args) {
        final Normalizer normalizer = new Normalizer();
        final StopWords stopWords = new StopWords();

        if (args.length == 0) {
            System.out.print("Use: search <args>");
            return;
        }

        List<String> normalizedArgs = normalizer.normalizeText(args);
        List<String> deletedStopWordsArgs = stopWords.delStopWords(normalizedArgs);

        StringBuilder query = new StringBuilder();

        for (String arg : deletedStopWordsArgs) {
            query.append(arg).append(" ");
        }

        String searchTerms = query.toString().trim();


        System.out.print("You're searching by: " + searchTerms);
    }
}
