package ru.spbau.komarov.repl.parser.AST;

import ru.spbau.komarov.repl.visitors.Evaluator;
import ru.spbau.komarov.repl.visitors.ExpressionVisitor;


public class Assignment extends Exp {
    public Var variable;
    public Exp value;

    public Assignment(Var variable, Exp value) {
        this.variable = variable;
        this.value = value;
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
