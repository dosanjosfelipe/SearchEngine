package me.search.ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {

    public Map<String, List<Integer>> argsCounter(List<String> args, Map<String, String> texts) {

        Map<String, List<Integer>> countMap = new HashMap<>();

        for (Map.Entry<String, String> entry : texts.entrySet()) {
            List<Integer> countersList = new ArrayList<>();

            for (String arg : args) {
                Pattern pattern = Pattern.compile("\\b" + Pattern.quote(arg) + "\\b");
                Matcher matcher = pattern.matcher(entry.getValue());

                Integer count = 0;
                while (matcher.find()) {
                    count++;
                }

                countersList.add(count);
            }

            countMap.put(entry.getKey(), countersList);
        }
        return countMap;
    }
}
