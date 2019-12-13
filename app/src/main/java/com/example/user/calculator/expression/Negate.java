package com.example.user.calculator.expression;

public class Negate extends UnaryOperation {
    public Negate(Expression expression) {
        super(expression);
    }

    @Override
    public Double evaluate() {
        return - expression.evaluate();
    }
}
