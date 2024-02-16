package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

import java.util.Queue;

public sealed interface UnaryOperator extends Operator
    permits UnaryNegative, UnaryPositive {

    @Nonnull
    Value evaluate(@Nonnull Value value);

    @Nonnull
    @Override
    default Value evaluate(@Nonnull Queue<Value> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("Unary operator requires at least one argument");
        }
        return evaluate(args.poll());
    }
}
