package ru.spbau.komarov.sd_01;

import ru.spbau.komarov.sd_01.commands.Command;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Shell {

    private Map<String, Command> commandMap = new HashMap<String, Command>();

    public static Builder builder() {
        return new Shell().new Builder();
    }

    public Shell() {
        commandMap.put("man", new ManCommand());
    }

    public void start() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            while(true) {
                System.out.print(">>> ");
                executeLineWithPipes(br.readLine());
            }

        } catch (IOException e) {
            System.err.print(e.getMessage());
            return;
        }
    }

    private void executeLineWithPipes(String line) {
        String[] commands = line.split("\\|");
        InputStream in = System.in;
        PipedInputStream nextIn = null;
        PrintStream out = null;
        for (int i = 0; i < commands.length; i++) {
            if (out != null) out.close();
            if (i == commands.length - 1) {
                out = System.out;
            } else {
                nextIn = new PipedInputStream();
                try {
                    out = new PrintStream(new PipedOutputStream(nextIn));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            executeCommandLine(commands[i].trim(), in, out);
            in = nextIn;
        }
    }

    private void executeCommandLine(String line, InputStream in, PrintStream out) {
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
        if(words.length > 1 && !words[0].equals("grep")) {
            arg = words[1];
        } else if (words[0].equals("grep")) {
            arg = line;
        }

        command.execute(arg, in, out);
    }

    public class Builder {

        public Builder addCommand(String commandName, Command command) {
            commandMap.put(commandName, command);
            return this;
        }

        public Shell build() {
            return Shell.this;
        }
    }

    private class ManCommand implements Command {

        private String info = "Print information about command";

        @Override
        public String getInfo() {
            return info;
        }

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
                    out.println(command.getInfo());
                }
            }
        }
    }
}
