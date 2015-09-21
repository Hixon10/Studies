package ru.spbau.komarov.sd_01.commands;

import java.io.InputStream;
import java.io.PrintStream;

public interface Command {
    public void execute(String arg, InputStream in, PrintStream out);
    public String getInfo();
}
