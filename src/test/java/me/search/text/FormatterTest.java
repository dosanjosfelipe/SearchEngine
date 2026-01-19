package me.search.text;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatterTest {

    private final Formatter formatter = new Formatter();

    @Test
    void normalizer() {

        List<String> input = List.of("Árvore!", "Café", "João123", "Olá@#");
        List<String> expected = List.of(
                "arvore",
                "cafe",
                "joao123",
                "ola"
        );

        List<String> result = formatter.normalizer(input);

        assertEquals(expected, result);
    }

    @Test
    void stem() {

        List<String> input = List.of(
                "correndo",
                "corridas",
                "correu",
                "correr"
        );

        List<String> result = formatter.stem(input);

        /* stems aproximados (PortugueseStemmer) */
        assertEquals("corr", result.get(0));
        assertEquals("corr", result.get(1));
        assertEquals("corr", result.get(2));
        assertEquals("corr", result.get(3));
    }

    @Test
    void stopWords() {

        List<String> input = List.of(
                "eu", "gosto", "de", "programacao", "e", "java"
        );

        List<String> expected = List.of(
                "gosto",
                "programacao",
                "java"
        );

        List<String> result = formatter.stopWords(input);

        assertEquals(expected, result);
    }
}
