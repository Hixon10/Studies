package ru.spbau.komarov.sd_01;

import ru.spbau.komarov.sd_01.commands.Command;
import ru.spbau.komarov.sd_01.commands.MetaCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Shell {

    private Map<String, Command> commandMap = new HashMap<String, Command>();

    public static Builder builder() {
        return new Shell().new Builder();
    }

    public void start() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            while(true) {
                System.out.print(">>> ");
                executeLine(br.readLine());
            }

        } catch (IOException e) {
            System.err.print(e.getMessage());
            return;
        }
    }

    private void executeLine(String line) {
        String[] words = line.split("\\s+");
        if(words.length == 0)
            return;

        String commandName = words[0];
        Command command = commandMap.get(commandName);
        if(command == null) {
            System.out.println(commandName + ": command not found");
            return;
        }

        String arg = null;
        if(words.length > 1)
            arg = words[1];

        command.execute(arg);
    }

    public class Builder {

        public Builder addCommand(String commandName, Command command) {
            commandMap.put(commandName, command);
            return this;
        }

        public Builder addMetaCommand(String commandName, MetaCommand command) {
            commandMap.put(commandName, command);
            command.setCommandMap(commandMap);
            return this;
        }

        public Shell build() {
            return Shell.this;
        }
    }
}
