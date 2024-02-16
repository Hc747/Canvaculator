package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

public record Division() implements BinaryOperator {

    public static final Operator OPERATOR = new Division();

    @Override
    public int precedence() {
        return Precedence.PRECEDENCE_MULTIPLICATION_DIVISION_MODULO;
    }

    @Nonnull
    public Value evaluate(@Nonnull Value x, @Nonnull Value y) {
        return x.divide(y);
    }
}
