package me.search.text;


import java.util.List;

public class PipelineFormatter {

    public List<String> apply(String[] text) {
        Formatter formatter = new Formatter();

        List<String> normalizedText = formatter.normalizer(text);

        List<String> noStopWordsText = formatter.stopWords(normalizedText);

        return formatter.stem(noStopWordsText);
    }
}
