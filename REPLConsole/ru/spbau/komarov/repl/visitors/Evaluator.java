package ru.spbau.komarov.repl.visitors;

import ru.spbau.komarov.repl.parser.AST.*;

import java.util.HashMap;
import java.util.Map;


public class Evaluator implements ExpressionVisitor {


    Exp answer;
    public String maybeErrorMessage = "";
    private Map<String, Exp> context = new HashMap<>();

    public Exp getAnswer(){
        return answer;
    }

    @Override
    public void visit(Num num) {
        answer = num;
    }

    @Override
    public void visit(Sub sub) {
        Exp leftResult = sub.left.evaluate(this);
        Exp rightResult = sub.right.evaluate(this);
        if (leftResult instanceof Num && rightResult instanceof Num) {
            answer = new Num(((Num) leftResult).number.doubleValue() - ((Num) rightResult).number.doubleValue());
        } else {
            answer = new Sub(leftResult, rightResult);
        }
    }

    @Override
    public void visit(Sum sum) {
        Exp leftResult = sum.left.evaluate(this);
        Exp rightResult = sum.right.evaluate(this);
        if (leftResult instanceof Num && rightResult instanceof Num) {
            answer = new Num(((Num) leftResult).number.doubleValue() + ((Num) rightResult).number.doubleValue());
        } else {
            answer = new Sum(leftResult, rightResult);
        }
    }

    @Override
    public void visit(Mul mul) {
        Exp leftResult = mul.left.evaluate(this);
        Exp rightResult = mul.right.evaluate(this);
        if (leftResult instanceof Num && rightResult instanceof Num) {
            answer = new Num(((Num) leftResult).number.doubleValue() * ((Num) rightResult).number.doubleValue());
        } else {
            answer = new Mul(leftResult, rightResult);
        }
    }

    @Override
    public void visit(Div div) {
        Exp leftResult = div.left.evaluate(this);
        Exp rightResult = div.right.evaluate(this);
        if (leftResult instanceof Num && rightResult instanceof Num) {
            answer = new Num(((Num) leftResult).number.doubleValue() / ((Num) rightResult).number.doubleValue());
        } else {
            answer = new Div(leftResult, rightResult);
        }
    }

    @Override
    public void visit(Var var) {
        if (context.containsKey(var.name)) {
            Exp val = context.get(var.name);
            val.accept(this);
            answer = val;
        } else {
            maybeErrorMessage += ("error: '" + var.name + "' is undefined ");
            answer = var;
        }
    }

    @Override
    public void visit(Assignment assign) {
        Exp res = assign.value.evaluate(this);
        context.put(assign.variable.name, res);
        answer = new Assignment(assign.variable, res);
    }

    public void setContext(Map<String, Exp> context) {
        this.context = context;
    }

    public Map<String, Exp> getContext() {
        return context;
    }
}