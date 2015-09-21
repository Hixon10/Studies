package ru.spbau.komarov.sd_01.commands;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class GrepCommand implements Command {
    private String info = "The grep command prints lines matching a pattern. This command accepts filename as argument.";

    @Override
    public void execute(String argsLine, InputStream in, PrintStream out) {
        String[] args = argsLine.split("\\s+");
        Boolean isFirstCommand = false;

        try {
            isFirstCommand = in.available() == 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filename = isFirstCommand ? args[args.length - 1] : null;

        try (BufferedReader reader = new BufferedReader(filename != null ?
                new FileReader(filename) :
                new InputStreamReader(in, "UTF-8"))) {
            String line = null;
            ArrayList<String> lines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            boolean hasWordReFlag = false;
            boolean hasIgnoreCaseFlag = false;

            boolean hasAfterContextFlag = false;
            int afterContextFlagValue = 0;

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-A")) {
                    hasAfterContextFlag = true;
                    afterContextFlagValue = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-w")) {
                    hasWordReFlag = true;
                } else if (args[i].equals("-i")) {
                    hasIgnoreCaseFlag = true;
                }
            }

            String result = grepHelper(lines, isFirstCommand, args, hasWordReFlag, hasIgnoreCaseFlag, hasAfterContextFlag, afterContextFlagValue);

            out.print(result);

        } catch (FileNotFoundException e) {
            System.out.println("No such file or directory");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String grepHelper(ArrayList<String> lines, Boolean isFirstCommand, String[] args, boolean hasWordReFlag, boolean hasIgnoreCaseFlag, boolean hasAfterContextFlag, int afterContextFlagValue) throws IOException {
        ArrayList<String> resultLines = new ArrayList<>();
        Set<Integer> addedLinesIndex = new HashSet<>();

        String query = isFirstCommand ? args[args.length - 2] : args[args.length - 1];
        Pattern re = null;

        if (hasIgnoreCaseFlag) {
            re = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        } else {
            re = Pattern.compile(query);
        }


        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            boolean find = false;

            if (hasWordReFlag) {
                String[] splited = line.split("\\s+");

                for (int i = 0; i < splited.length; i++) {
                    if (hasIgnoreCaseFlag) {
                        splited[i] = splited[i].toLowerCase();
                        query = query.toLowerCase();
                    }

                    if (query.equals(splited[i])) {
                        find = true;
                        break;
                    }
                }
            } else {
                if (re.matcher(line).find()) {
                    find = true;
                }
            }

            if (find) {
                if (hasAfterContextFlag) {
                    int addedLeft = afterContextFlagValue;

                    for (int d = j; d < lines.size() && addedLeft >= 0; d++) {
                        if (!addedLinesIndex.contains(d)) {
                            resultLines.add(lines.get(d));
                            addedLinesIndex.add(d);
                        }
                        addedLeft--;
                    }
                } else {
                    if (!addedLinesIndex.contains(j)) {
                        resultLines.add(line);
                        addedLinesIndex.add(j);
                    }
                }
            }
        }

        return String.join(System.lineSeparator(), resultLines);
    }

    @Override
    public String getInfo() {
        return info;
    }
}
