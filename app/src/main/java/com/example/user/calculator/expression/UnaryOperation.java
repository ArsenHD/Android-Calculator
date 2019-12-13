package com.example.user.calculator.expression;

import com.example.user.calculator.expression.Expression;

public abstract class UnaryOperation extends Expression {
    protected Expression expression;

    public UnaryOperation(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "-" + expression.toString();
    }
}
