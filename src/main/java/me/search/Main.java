package me.search;

import me.search.cli.ArgumentParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ArgumentParser parser = new ArgumentParser();

        parser.parse(args);
    }
}
