package ru.spbau.komarov.repl.visitors;

import ru.spbau.komarov.repl.parser.AST.*;

public interface ExpressionVisitor {
    void visit(Num num);
    void visit(Sum sum);
    void visit(Mul mul);
    void visit(Div div);
    void visit(Sub sub);
    void visit(Var var);
    void visit(Assignment assign);
}