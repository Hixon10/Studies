package ru.spbau.komarov.sd_01.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WcCommand implements Command {

    private String info = "Print newline, word, and byte counts for file";

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void execute(String filename) {
        if (filename == null) {
            System.out.println("not filename");
            return;
        }

        int bytes = 0;
        int lines = 0;
        int words = 0;

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                String ls = System.getProperty("line.separator");

                while ((line = reader.readLine()) != null) {
                    lines++;
                    bytes += line.getBytes().length;
                    words += line.split("\\s+").length;
                }

            } catch (FileNotFoundException e) {
                System.out.println("No such file or directory");
            }
            System.out.format("%d %d %d\n", lines, words, bytes);
        } catch (IOException e) {
            System.err.print(e.getMessage());
            return;
        }
    }
}
