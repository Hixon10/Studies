package ru.spbau.komarov.sd_01.commands;

public class ExitCommand implements Command {
    private String info = "Cause normal process termination";

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void execute(String arg) {
        System.exit(0);
    }
}
