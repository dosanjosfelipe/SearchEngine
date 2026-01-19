package me.search.ranking;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ScoreCalculatorTest {

    @Test
    void testDefineScore_basicScenario() {

        ScoreCalculator calculator = new ScoreCalculator();

        List<String> rootArgs = List.of("java", "search");
        List<String> perfectArgs = List.of("java", "search");

        Map<String, List<String>> rootTextHash = new HashMap<>();
        rootTextHash.put("doc1", List.of("java", "search", "engine"));
        rootTextHash.put("doc2", List.of("python", "search"));

        Map<String, List<String>> perfectTextHash = new HashMap<>();
        perfectTextHash.put("doc1", List.of("java", "search"));
        perfectTextHash.put("doc2", List.of("search"));

        Map<String, List<Integer>> rootCounters = new HashMap<>();
        rootCounters.put("doc1", List.of(1, 1));
        rootCounters.put("doc2", List.of(0, 1));

        Map<String, List<Integer>> perfectCounters = new HashMap<>();
        perfectCounters.put("doc1", List.of(1, 1));
        perfectCounters.put("doc2", List.of(0, 1));

        Map<String, Double> result = calculator.defineScore(
                rootArgs,
                perfectArgs,
                rootTextHash,
                perfectTextHash,
                rootCounters,
                perfectCounters
        );

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.containsKey("doc1"));
        assertTrue(result.containsKey("doc2"));

        double score1 = result.get("doc1");
        double score2 = result.get("doc2");

        assertTrue(score1 > 0);
        assertTrue(score2 > 0);

        assertTrue(score1 > score2);
    }
}
