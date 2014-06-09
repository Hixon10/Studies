package ru.spbau.komarov.repl;

public class OutputErrorException extends Exception {
    public OutputErrorException(String str) {
        super(str);
    }
}