package com.example.user.calculator.expression;

public class Number extends Expression {
    private String number;

    public Number(String number) {
        this.number = number;
    }

    @Override
    public Double evaluate() {
        return Double.valueOf(number);
    }

    @Override
    public String toString() {
        return number;
    }
}
