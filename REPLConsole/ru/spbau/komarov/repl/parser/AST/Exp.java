package ru.spbau.komarov.repl.parser.AST;

import ru.spbau.komarov.repl.visitors.Evaluator;
import ru.spbau.komarov.repl.visitors.ExpressionVisitor;


public abstract class Exp {
    public abstract void accept(ExpressionVisitor visitor);
    public abstract Exp evaluate(Evaluator visitor);
}
