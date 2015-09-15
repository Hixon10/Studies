package ru.spbau.komarov.sd_01.commands;

public class PwdCommand implements Command {

    private String info = "Print name of current/working directory";

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void execute(String arg) {
        System.out.println(System.getProperty("user.dir"));
    }
}
