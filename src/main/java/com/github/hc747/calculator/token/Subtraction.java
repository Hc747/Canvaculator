package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

public record Subtraction() implements BinaryOperator {

    public static final Operator OPERATOR = new Subtraction();

    @Override
    public int precedence() {
        return Precedence.PRECEDENCE_ADDITION_SUBTRACTION;
    }

    @Nonnull
    @Override
    public Value evaluate(@Nonnull Value left, @Nonnull Value right) {
        return left.subtract(right);
    }
}
