package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

public record Multiplication() implements BinaryOperator {

    public static final Multiplication OPERATOR = new Multiplication();

    @Override
    public int precedence() {
        return Precedence.PRECEDENCE_MULTIPLICATION_DIVISION_MODULO;
    }

    @Nonnull
    public Value evaluate(@Nonnull Value left, @Nonnull Value right) {
        return left.multiply(right);
    }
}
