package com.github.hc747.calculator.evaluator;

import jakarta.annotation.Nonnull;

@FunctionalInterface
public interface ExpressionEvaluator {

    @Nonnull
    Number evaluate(@Nonnull String expression);
}
