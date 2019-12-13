package com.example.user.calculator.expression;

public abstract class BinaryOperation extends Expression {
    protected Expression left;
    protected Expression right;

    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + " " + getOperation() + " " + right.toString();
    }

    protected abstract String getOperation();
}
