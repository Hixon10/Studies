package ru.spbau.komarov.repl.lexer;

import java.util.ArrayList;

public class Lexer {

    public static ArrayList<Token> getTokens(String input) {

        ArrayList<Token> tokens = new ArrayList<Token>();
        StringBuffer stringBuffer = new StringBuffer();
        Token.Type lastTokenType = Token.Type.END;

        for (int i=0; i < input.length(); i++) {
            Character ch = input.charAt(i);
            if (Character.isAlphabetic(ch) && lastTokenType != Token.Type.NUMBER
                    || Character.isDigit(ch) && lastTokenType == Token.Type.VARIABLE) {
                if(stringBuffer.length() > 0 && lastTokenType == Token.Type.NUMBER) {
                    String str = stringBuffer.toString();
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                stringBuffer.append(ch);
                lastTokenType = Token.Type.VARIABLE;
            } else if (Character.isDigit(ch)) {
                if(stringBuffer.length() > 0 && lastTokenType == Token.Type.VARIABLE) {
                    String str = stringBuffer.toString();
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                stringBuffer.append(ch);
                lastTokenType = Token.Type.NUMBER;
            } else if (ch.equals('-')) {
                String str = stringBuffer.toString();
                if(str.length() > 0) {
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                lastTokenType = Token.Type.SUB;
                tokens.add(new Token(lastTokenType, "-", i));
            } else if (ch.equals('+')) {
                String str = stringBuffer.toString();
                if(str.length() > 0) {
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                lastTokenType = Token.Type.SUM;
                tokens.add(new Token(lastTokenType, "+", i));
            } else if (ch.equals('/')) {
                String str = stringBuffer.toString();
                if(str.length() > 0) {
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                lastTokenType = Token.Type.DIV;
                tokens.add(new Token(lastTokenType, "/", i));
            } else if (ch.equals('*')) {
                String str = stringBuffer.toString();
                if(str.length() > 0) {
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                lastTokenType = Token.Type.MUL;
                tokens.add(new Token(lastTokenType, "*", i));
            } else if (Character.isWhitespace(ch)) {
                String str = stringBuffer.toString();
                if(str.length() > 0) {
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                lastTokenType = Token.Type.SPACE;
            } else if (ch.equals('=')) {
                String str = stringBuffer.toString();
                if(str.length() > 0) {
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                lastTokenType = Token.Type.ASSIGN;
                tokens.add(new Token(lastTokenType, "=", i));
            } else if (ch.equals('(')) {
                String str = stringBuffer.toString();
                if(str.length() > 0) {
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                lastTokenType = Token.Type.LEFT_BRACKET;
                tokens.add(new Token(lastTokenType, "(", i));
            } else if (ch.equals(')')) {
                String str = stringBuffer.toString();
                if(str.length() > 0) {
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                lastTokenType = Token.Type.RIGHT_BRACKET;
                tokens.add(new Token(lastTokenType, ")", i));
            } else {
                String str = stringBuffer.toString();
                if(str.length() > 0) {
                    tokens.add(new Token(lastTokenType,str,i-str.length()));
                    stringBuffer.delete(0, stringBuffer.length());
                }
                lastTokenType = Token.Type.END;
                tokens.add(new Token(lastTokenType, "", -1));
            }
        }

        String str = stringBuffer.toString();
        if(str.length() > 0) {
            tokens.add(new Token(lastTokenType,str,input.length()-str.length()));
            stringBuffer.delete(0, stringBuffer.length());
        }
        tokens.add(new Token(Token.Type.END, "", -1));
        return tokens;
    }

}