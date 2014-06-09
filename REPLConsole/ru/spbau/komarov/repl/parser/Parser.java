package ru.spbau.komarov.repl.parser;

import ru.spbau.komarov.repl.SyntaxErrorException;
import ru.spbau.komarov.repl.lexer.Token;
import ru.spbau.komarov.repl.parser.AST.*;

import java.util.ArrayList;


public class Parser {

    private ArrayList<Token> tokens;
    private int curNumberToken;
    private Token curToken;


    public Token getNextToken() throws SyntaxErrorException{
        curNumberToken++;
        if(curNumberToken >= tokens.size()) {
            throw new SyntaxErrorException("error: invalid syntax");
        }
        return tokens.get(curNumberToken);
    }


    public Exp getRootAST(ArrayList<Token> tokens) throws SyntaxErrorException {
        curNumberToken = 0;
        this.tokens = tokens;

        if(tokens.size() > 0)
            curToken = tokens.get(curNumberToken);
        else
            throw new SyntaxErrorException("error: invalid syntax");

        if(curToken.type.equals(Token.Type.END))
            return null;

        Exp root;
        if (tokens.size() > 1 && tokens.get(1).type.equals(Token.Type.ASSIGN)) {

            if(!curToken.type.equals(Token.Type.VARIABLE)) {
                throw new SyntaxErrorException("error: invalid syntax");
            }
            Var var = new Var(curToken.name);
            curToken = getNextToken();
            if(!curToken.type.equals(Token.Type.ASSIGN)) {
                throw new SyntaxErrorException("error: invalid syntax");
            }
            curToken = getNextToken();
            root = new Assignment(var, parseExp());

        } else {
            root = parseExp();
        }

        if (!curToken.type.equals(Token.Type.END)) {
            throw new SyntaxErrorException("error: invalid syntax");
        }
        return root;
    }


    private Exp parseVar() throws SyntaxErrorException {
        String name = curToken.name;
        curToken = getNextToken();
        return new Var(name);
    }

    private Exp parseNum() throws SyntaxErrorException {
        try {
            int value = Integer.parseInt(curToken.name);
            curToken = getNextToken();
            return new Num(value);
        } catch (NumberFormatException e) {
            throw new SyntaxErrorException("error: length number too long");
        }
    }

    private Exp parseFactor() throws SyntaxErrorException {

        if(curToken.type.equals(Token.Type.NUMBER)) {
            return parseNum();
        } else if(curToken.type.equals(Token.Type.VARIABLE)) {
            return parseVar();
        } else if(curToken.type.equals(Token.Type.LEFT_BRACKET) ) {

            curToken = getNextToken();
            Exp resExpr = parseExp();
            if(!curToken.type.equals(Token.Type.RIGHT_BRACKET)) {
                throw new SyntaxErrorException("error: invalid syntax");
            }
            curToken = getNextToken();
            return resExpr;

        } else {
            throw new SyntaxErrorException("error: invalid syntax");
        }
    }

    private Exp parseAddendum() throws SyntaxErrorException {

        Exp left = parseFactor();
        while ( curToken.type.equals(Token.Type.MUL) || curToken.type.equals(Token.Type.DIV) ) {
            Token.Type op = curToken.type;
            curToken = getNextToken();
            Exp right = parseFactor();
            if (op.equals(Token.Type.MUL)) {
                left = new Mul(left, right);
            } else {
                left = new Div(left, right);
            }
        }
        return left;
    }


    private Exp parseExp() throws SyntaxErrorException {

        Exp left;
        if(curToken.type.equals(Token.Type.SUB)){
            left = new Num(0);
        } else {
            left = parseAddendum();
        }
        while ( curToken.type.equals(Token.Type.SUB) || curToken.type.equals(Token.Type.SUM) ) {
            Token.Type operation = curToken.type;
            curToken = getNextToken();
            Exp right = parseAddendum();
            if (operation.equals(Token.Type.SUM)) {
                left = new Sum(left, right);
            } else {
                left = new Sub(left, right);
            }
        }
        return left;
    }


}
