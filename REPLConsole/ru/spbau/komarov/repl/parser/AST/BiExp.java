package ru.spbau.komarov.repl.parser.AST;



public abstract class BiExp extends Exp {
    public final Exp left;
    public final Exp right;

    public BiExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }
}
