package com.example.user.calculator.expression.parser;

import java.text.ParseException;

class LexicalAnalyzer {
    private String input;
    private char curChar;
    private int curPos;
    private Token curToken;

    Token getCurToken() {
        return curToken;
    }

    int getCurPos() {
        return curPos;
    }

    int getCurChar() {
        return curChar;
    }

    LexicalAnalyzer(String input) {
        this.input = input;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private void nextChar() {
        if (curPos == input.length()) {
            curToken = Token.END;
            return;
        }
        curChar = input.charAt(curPos);
        curPos++;
    }

    void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }

        if (curToken == Token.END) {
            return;
        }

        switch (curChar) {
            case '(':
                curToken = Token.LBRACKET;
                nextChar();
                break;
            case ')':
                curToken = Token.RBRACKET;
                nextChar();
                break;
            case '+':
                curToken = Token.ADD;
                nextChar();
                break;
            case '-':
                curToken = Token.SUB;
                nextChar();
                break;
            case '*':
                curToken = Token.MUL;
                nextChar();
                break;
            case '/':
                curToken = Token.DIV;
                nextChar();
                break;
            default:
                if (Character.isDigit(curChar)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(curChar);
                    nextChar();
                    while (!(curToken == Token.END) && (Character.isDigit(curChar) || curChar == '.')) {
                        sb.append(curChar);
                        nextChar();
                    }
                    curToken = Token.NUM;
                    curToken.setValue(Double.valueOf(sb.toString()));
                } else {
                    throw new ParseException("Illegal character '" + (char) curChar + "'", (curPos - 1));
                }
        }
    }
}
