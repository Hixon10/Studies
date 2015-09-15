package ru.spbau.komarov.sd_01.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CatCommand implements Command {

    private String info = "Print file on the standard output";

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

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                String ls = System.getProperty("line.separator");

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append( line );
                    stringBuilder.append( ls );
                }
                System.out.println(stringBuilder.toString());

            } catch (FileNotFoundException e) {
                System.out.println("No such file or directory");
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
            return;
        }
    }
}
