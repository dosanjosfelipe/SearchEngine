package me.search.cli;

public class ArgumentParser {

    public void parse(String[] args) {

        if (args.length == 0) {
            System.out.print("Use: search <args>");
            return;
        }

        StringBuilder query = new StringBuilder();

        for (String arg : args) {
            query.append(arg).append(" ");
        }

        String searchTerms = query.toString().trim();

        System.out.print("You're searching by: " + searchTerms);
    }
}
