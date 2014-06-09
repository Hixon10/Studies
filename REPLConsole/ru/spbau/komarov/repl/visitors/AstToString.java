package ru.spbau.komarov.repl.visitors;

import ru.spbau.komarov.repl.parser.AST.*;


public class AstToString implements ExpressionVisitor {

    private String answer = "";

    public void clear(){
        answer = "";
    }

    public String getAnswer(){
        return answer;
    }

    @Override
    public void visit(Num exp) {
        answer += exp.number.toString();
    }

    @Override
    public void visit(Div div) {
        div.left.accept(this);
        answer += " / ";
        div.right.accept(this);
    }

    @Override
    public void visit(Mul mul) {
        mul.left.accept(this);
        answer += " * ";
        mul.right.accept(this);
    }

    @Override
    public void visit(Sum sum) {
        answer += "(";
        sum.left.accept(this);
        answer += " + ";
        sum.right.accept(this);
        answer += ")";
    }

    @Override
    public void visit(Var var) {
        answer += var.name;
    }

    @Override
    public void visit(Assignment assign) {
        assign.value.accept(this);
    }

    @Override
    public void visit(Sub sub) {
        answer += "(";
        sub.left.accept(this);
        answer += " - ";
        sub.right.accept(this);
        answer += ")";
    }
}
