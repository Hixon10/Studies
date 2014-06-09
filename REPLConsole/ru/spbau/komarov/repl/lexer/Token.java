package ru.spbau.komarov.repl.lexer;


public class Token {

    public static enum Type {
        NUMBER,
        VARIABLE,

        SUM,
        SUB,
        MUL,
        DIV,

        SPACE,

        LEFT_BRACKET,
        RIGHT_BRACKET,

        ASSIGN,

        END
    }

    public int offset;
    public int length;
    public Type type;
    public String name;

    public Token(Type type, String name, int offset) {
        this.type = type;
        this.name = name;
        this.offset = offset;
        this.length = name.length();
    }
}
