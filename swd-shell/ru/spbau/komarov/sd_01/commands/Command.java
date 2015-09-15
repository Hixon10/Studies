package ru.spbau.komarov.sd_01.commands;

public interface Command {
    public void execute(String arg);
    public String getInfo();
}
