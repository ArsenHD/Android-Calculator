package com.example.user.calculator.expression.parser;

import com.example.user.calculator.expression.Add;
import com.example.user.calculator.expression.Divide;
import com.example.user.calculator.expression.Expression;
import com.example.user.calculator.expression.Multiply;
import com.example.user.calculator.expression.Negate;
import com.example.user.calculator.expression.Number;
import com.example.user.calculator.expression.Subtract;

import java.text.ParseException;

public class Parser {

    private LexicalAnalyzer lex;

    public Expression parse(String input) throws ParseException {
        lex = new LexicalAnalyzer(input);
        lex.nextToken();

        return addSub(false);
    }

    private Expression addSub(boolean get) throws ParseException {
        Expression left = multiplyDivide(get);

        while (true) {
            switch (lex.getCurToken()) {
                case ADD:
                    left = new Add(left, multiplyDivide(true));
                    break;
                case SUB:
                    left = new Subtract(left, multiplyDivide(true));
                    break;
                default:
                    return left;
            }
        }
    }

    private Expression multiplyDivide(boolean get) throws ParseException {
        Expression left = primaryExpression(get);

        while (true) {
            switch (lex.getCurToken()) {
                case MUL:
                    left = new Multiply(left, primaryExpression(true));
                    break;
                case DIV:
                    left = new Divide(left, primaryExpression(true));
                    break;
                default:
                    return left;
            }
        }
    }

    private Expression primaryExpression(boolean get) throws ParseException {
        if (get) {
            lex.nextToken();
        }

        int pos = lex.getCurPos();

        switch (lex.getCurToken()) {
            case NUM:
                Double v = lex.getCurToken().value;
                lex.nextToken();
                return new Number(String.valueOf(v));
            case SUB:
                return new Negate(primaryExpression(true));
            case LBRACKET:
                Expression e = addSub(true);
                lex.nextToken();
                return e;
        }

        throw new ParseException("Unexpected character", pos - 1);
    }
}
