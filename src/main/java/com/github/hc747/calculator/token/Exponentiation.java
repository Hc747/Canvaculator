package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

public record Exponentiation() implements BinaryOperator {

    public static final Operator OPERATOR = new Exponentiation();

    @Override
    public int precedence() {
        return Precedence.PRECEDENCE_EXPONENTIATION;
    }

    @Nonnull
    @Override
    public Associativity associativity() {
        return Associativity.RIGHT;
    }

    @Nonnull
    public Value evaluate(@Nonnull Value left, @Nonnull Value right) {
        return left.pow(right);
    }
}
