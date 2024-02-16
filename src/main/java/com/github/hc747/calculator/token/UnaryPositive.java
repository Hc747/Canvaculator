package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

public record UnaryPositive() implements UnaryOperator {

    public static final Operator OPERATOR = new UnaryPositive();

    @Override
    public int precedence() {
        return Precedence.PRECEDENCE_UNARY_OPERATOR;
    }

    @Nonnull
    @Override
    public Value evaluate(@Nonnull Value x) {
        return x;
    }
}
