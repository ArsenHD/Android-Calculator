package com.example.user.calculator.expression;

public class Multiply extends BinaryOperation {
    public Multiply(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperation() {
        return "*";
    }

    @Override
    public Double evaluate() {
        return left.evaluate() * right.evaluate();
    }
}
