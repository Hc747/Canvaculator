package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.Objects;

public record Value(
    @Nonnull BigDecimal value
) implements Token {

    public Value {
        Objects.requireNonNull(value, "value");
    }

    @Nonnull
    public Value negate() {
        return new Value(value.negate());
    }

    @Nonnull
    public Value add(@Nonnull Value other) {
        return new Value(value.add(other.value));
    }

    @Nonnull
    public Value subtract(@Nonnull Value other) {
        return new Value(value.subtract(other.value));
    }

    @Nonnull
    public Value multiply(@Nonnull Value other) {
        return new Value(value.multiply(other.value));
    }

    @Nonnull
    public Value divide(@Nonnull Value other) {
        return new Value(value.divide(other.value));
    }

    @Nonnull
    public Value mod(@Nonnull Value other) {
        return new Value(value.remainder(other.value));
    }

    @Nonnull
    public Value pow(@Nonnull Value other) {
        return new Value(value.pow(other.value.intValue()));
    }
}
