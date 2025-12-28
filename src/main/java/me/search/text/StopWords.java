package me.search.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopWords {

    public List<String> delStopWords(List<String> text) {
        Set<String> stopWords = new HashSet<>(List.of(
                "a", "ao", "aos", "as", "ate",
                "com", "como", "da", "das", "de", "do", "dos", "e", "em", "entre", "era", "eram",
                "essa", "essas", "esse", "esses", "esta", "está", "estão", "estas", "este", "estes", "eu",
                "foi", "for", "foram", "ha", "isso", "isto", "já", "lhe", "lhes", "mais", "mas", "me",
                "mesmo", "meu", "meus", "minha", "minhas", "na", "nas", "não", "nem", "no", "nos", "nós", "o",
                "os", "ou", "para", "pela", "pelas", "pelo", "pelos", "por", "qual", "que", "quem", "se", "seu",
                "seus", "sua", "suas", "somente", "sou", "tambem", "te", "tem", "tenho", "teu", "teus", "tu",
                "uma", "umas", "um", "uns", "vos"
        ));

        text.removeIf(stopWords::contains);

        return text;
    }
}
