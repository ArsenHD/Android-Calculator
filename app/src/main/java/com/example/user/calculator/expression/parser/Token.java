package com.example.user.calculator.expression.parser;

public enum Token {
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    LBRACKET("("),
    RBRACKET(")"),
    NUM("num"),
    END("end");

    String name;
    Double value;

    Token(String name) {
        this.name = name;
    }

    void setValue(Double value) {
        this.value = value;
    }
}