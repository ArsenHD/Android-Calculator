package com.example.user.calculator.expression;

import android.util.Log;

public class Subtract extends BinaryOperation {
    public Subtract(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperation() {
        return "-";
    }

    @Override
    public Double evaluate() {
        Log.e("Tag1234", String.valueOf(left.evaluate()));
        Log.e("Tag1234", String.valueOf(right.evaluate()));
        Log.e("Tag1234", String.valueOf(left.evaluate() - right.evaluate()));
        return left.evaluate() - right.evaluate();
    }
}
