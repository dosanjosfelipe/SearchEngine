package me.search;

import me.search.cli.ArgumentParser;

public class Main {
    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser();

        parser.parse(args);
    }

}
