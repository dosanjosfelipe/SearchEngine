package me.search.core;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SearcherTest {

    private final Searcher searcher = new Searcher();

    @Test
    void countTermFrequency() {
        List<String> argsInput = List.of("sistemas", "solares");
        Map<String, List<String>> filesInput = new HashMap<>();
        filesInput.put("file1", List.of("sistemas", "embarcados", "API", "monolito"));
        filesInput.put("file2", List.of("planetas", "sistemas", "solares", "terra", "universo"));

        Map<String, List<Integer>> expected = new HashMap<>();

        expected.put("file1", List.of(1,0));
        expected.put("file2", List.of(1,1));

       Map<String, List<Integer>> result =
                searcher.countTermFrequency(argsInput, filesInput);

        assertEquals(expected, result);
    }

    @Test
    void getDocFrequencies() {

        List<String> args = List.of("sistemas", "solares");
        Map<String, List<String>> fileHash = new LinkedHashMap<>();

        fileHash.put("file1", List.of("sistemas", "sistemas", "api"));
        fileHash.put("file2", List.of("planetas", "sistemas", "solares"));
        fileHash.put("file3", List.of("solares", "terra", "universo"));

        Map<String, Integer> expected = new HashMap<>();
        expected.put("sistemas", 2);
        expected.put("solares", 2);

        Map<String, Integer> result =
                searcher.getDocFrequencies(args, fileHash);

        assertEquals(expected, result);
    }
}