package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

public record Addition() implements BinaryOperator {

    public static final Operator OPERATOR = new Addition();

    @Override
    public int precedence() {
        return Precedence.PRECEDENCE_ADDITION_SUBTRACTION;
    }

    @Nonnull
    @Override
    public Value evaluate(@Nonnull Value x, @Nonnull Value y) {
        return x.add(y);
    }
}
