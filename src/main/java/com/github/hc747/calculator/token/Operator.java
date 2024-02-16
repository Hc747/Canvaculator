package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

import java.util.Queue;

public sealed interface Operator extends Token
    permits UnaryOperator, BinaryOperator {

    int precedence();

    @Nonnull
    default Associativity associativity() {
        return Associativity.LEFT;
    }

    @Nonnull
    Value evaluate(@Nonnull Queue<Value> args);

    @Nonnull
    static Operator negative() {
        return UnaryNegative.OPERATOR;
    }

    @Nonnull
    static Operator positive() {
        return UnaryPositive.OPERATOR;
    }

    @Nonnull
    static Operator addition() {
        return Addition.OPERATOR;
    }

    @Nonnull
    static Operator subtraction() {
        return Subtraction.OPERATOR;
    }

    @Nonnull
    static Operator multiplication() {
        return Multiplication.OPERATOR;
    }

    @Nonnull
    static Operator division() {
        return Division.OPERATOR;
    }

    @Nonnull
    static Operator modulo() {
        return Modulo.OPERATOR;
    }

    @Nonnull
    static Operator exponentiation() {
        return Exponentiation.OPERATOR;
    }

    @Nonnull
    static Operator unary(boolean positive) {
        return positive ? positive() : negative();
    }

    @Nonnull
    static Operator binary(boolean addition) {
        return addition ? addition() : subtraction();
    }
}
