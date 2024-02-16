package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

import java.util.Queue;

public sealed interface BinaryOperator extends Operator
    permits Subtraction, Addition, Multiplication, Division, Modulo, Exponentiation {

    @Nonnull
    Value evaluate(@Nonnull Value left, @Nonnull Value right);

    @Nonnull
    @Override
    default Value evaluate(@Nonnull Queue<Value> args) {
        if (args.size() < 2) {
            throw new IllegalArgumentException("Binary operator requires at least two arguments");
        }
        final var right = args.poll();
        final var left = args.poll();
        return evaluate(left, right);
    }
}
