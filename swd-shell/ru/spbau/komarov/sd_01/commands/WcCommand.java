package ru.spbau.komarov.sd_01.commands;

import java.io.*;

public class WcCommand implements Command {

    private String info = "Print newline, word, and byte counts for file";

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void execute(String filename, InputStream in, PrintStream out) {
//        if (filename == null) {
//            System.out.println("not filename");
//            return;
//        }

        int bytes = 0;
        int lines = 0;
        int words = 0;

        try {
            try (BufferedReader reader = new BufferedReader(filename != null ?
                    new FileReader(filename) :
                    new InputStreamReader(in, "UTF-8"))) {
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
            out.format("%d %d %d\n", lines, words, bytes);
        } catch (IOException e) {
            System.err.print(e.getMessage());
            return;
        }
    }
}
