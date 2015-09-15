package ru.spbau.komarov.sd_01;

/**
 * atolmachev
 */

import ru.spbau.komarov.sd_01.commands.*;

/**
 * Homework.
 *
 * cat [file]
 * wc [file]
 * exit
 * pwd
 *
 * man [other_command_name]
 *
 * доп задание
 * pipe
 *
 */

public class Main {
    public static void main(String[] args) {
        Shell.builder().addCommand("cat", new CatCommand())
                .addCommand("exit", new ExitCommand())
                .addCommand("pwd", new PwdCommand())
                .addCommand("wc", new WcCommand())
                .addMetaCommand("man", new ManCommand())
                .build().start();
    }
}
