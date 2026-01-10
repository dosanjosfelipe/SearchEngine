package me.search.text;

import java.util.ArrayList;
import java.util.List;

public class Normalizer {

    public List<String> normalizeText(String[] text) {

        List<String> normalizedWords = new ArrayList<>();
        for (String word : text) {

            String normalized = java.text.Normalizer.normalize(word.toLowerCase(),
                            java.text.Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

            normalized = normalized.replaceAll("[^a-z0-9]", "");

            normalizedWords.add(normalized);
        }
        return normalizedWords;
    }
}
