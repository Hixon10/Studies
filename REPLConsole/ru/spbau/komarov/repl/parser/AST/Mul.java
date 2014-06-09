package ru.spbau.komarov.repl.parser.AST;

import ru.spbau.komarov.repl.visitors.Evaluator;
import ru.spbau.komarov.repl.visitors.ExpressionVisitor;

public class Mul extends BiExp {
    public Mul(Exp left, Exp right) {
        super(left, right);
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
