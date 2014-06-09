package ru.spbau.komarov.repl;


public class SyntaxErrorException extends Exception {
    public SyntaxErrorException(String str) {
        super(str);
    }
}
