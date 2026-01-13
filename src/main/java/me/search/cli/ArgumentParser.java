package me.search.cli;

import me.search.indexing.FileScanner;
import me.search.ranking.Search;
import me.search.text.PipelineFormatter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentParser {

    public void parse(String[] args) throws IOException {
        PipelineFormatter formatterApplier = new PipelineFormatter();
        FileScanner fileScanner = new FileScanner();
        Search search = new Search();

        // -------------------- ARGS ---------------------
        if (args.length == 0) {
            System.out.print("Use: search <args>");
            return;
        }

        List<String> formattedArgs = formatterApplier.apply(args);

        if (formattedArgs.isEmpty()) {
            System.out.print("Use: search <args> with valid args");
            return;
        }

        StringBuilder query = new StringBuilder();

        for (String arg : args) {
            query.append(arg).append(" ");
        }

        String searchTerms = query.toString().trim();

        // -------------------- TEXTS ---------------------
        Map<String, String> fileHash = fileScanner.readFilesBase();

        Map<String, String> textHash = new HashMap<>();

        for (Map.Entry<String, String> entry : fileHash.entrySet()) {
            List<String> formattedText = formatterApplier.apply(entry.getValue().split("\\s+"));

            textHash.put(entry.getKey(), formattedText.toString());
        }

        // -------------------- TERMINAL ---------------------
        if (!fileHash.isEmpty()) {
            System.out.println(" ");
            System.out.println("You're searching by: " + searchTerms);
            System.out.println(" ");
            System.out.println("This is the count of args: ");

            Map<String, List<Integer>> counterMap = search.argsCounter(formattedArgs, textHash);

            for (Map.Entry<String, List<Integer>> entry : counterMap.entrySet()) {
                String FileName = Paths.get(entry.getKey()).getFileName().toString();

                System.out.println("Has " + entry.getValue() + " words like that in " + FileName);
            }
            System.out.println(" ");
        } else {
            System.out.println(" ");
            System.out.println("There are no files to read here.");
            System.out.println(" ");
        }
    }
}
