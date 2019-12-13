package com.example.user.calculator.expression.parser;

import java.util.Arrays;
import java.util.List;

public class Node {
    private String node;
    private List<Node> children;

    Node(String node, Node... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    String getNode() {
        return node;
    }

    List<Node> getChildren() {
        return children;
    }
}
