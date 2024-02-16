package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

import java.util.Queue;

public sealed interface BinaryOperator extends Operator
    permits Subtraction, Addition, Multiplication, Division, Modulo, Exponentiation {

    @Nonnull
    Value evaluate(@Nonnull Value x, @Nonnull Value y);

    @Nonnull
    @Override
    default Value evaluate(@Nonnull Queue<Value> args) {
        if (args.size() < 2) {
            throw new IllegalArgumentException("Binary operator requires at least two arguments");
        }
        final var y = args.poll();
        final var x = args.poll();
        return evaluate(x, y);
    }
}
