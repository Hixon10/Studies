package ru.spbau.komarov.sd_01.commands;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

public class ManCommand extends MetaCommand {

    private String info = "Print information about command";
    private Map<String, Command> commandMap;

    @Override
    public void execute(String arg, InputStream in, PrintStream out) {
        if (arg == null) {
            System.out.println("not command name");
            return;
        }

        if(commandMap != null) {
            Command command = commandMap.get(arg);
            if(command == null || command.getInfo() == null)
                System.out.println("No manual entry for " + arg);
            else {
                System.out.println(command.getInfo());
            }
        }
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setCommandMap(Map<String, Command> map) {
        this.commandMap = map;
    }
}
