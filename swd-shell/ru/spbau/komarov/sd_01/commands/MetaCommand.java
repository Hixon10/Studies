package ru.spbau.komarov.sd_01.commands;

import java.util.Map;

abstract public class MetaCommand implements Command {
    abstract public void setCommandMap(Map<String, Command> map);
}
