package ru.spbau.komarov.repl.parser.AST;

import ru.spbau.komarov.repl.visitors.Evaluator;
import ru.spbau.komarov.repl.visitors.ExpressionVisitor;


public class Num extends Exp {
    public final Number number;
    public Num(Number number) {
        this.number = number;
    }
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
    @Override
    public Exp evaluate(Evaluator visitor){
        visitor.visit(this);
        return visitor.getAnswer();
    }
}