package com.example.user.calculator.expression;

public class Divide extends BinaryOperation {
    public Divide(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperation() {
        return "/";
    }

    @Override
    public Double evaluate() {
        return left.evaluate() / right.evaluate();
    }
}
