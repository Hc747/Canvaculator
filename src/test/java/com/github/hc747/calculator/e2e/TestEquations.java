package com.github.hc747.calculator.e2e;

import com.github.hc747.calculator.evaluator.ExpressionEvaluator;
import com.github.hc747.calculator.evaluator.ShuntingYardExpressionEvaluator;
import com.github.hc747.calculator.tokeniser.ExpressionTokeniser;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestEquations {

    private ExpressionEvaluator evaluator;

    @BeforeEach
    public void setup() {
        evaluator = new ShuntingYardExpressionEvaluator(new ExpressionTokeniser());
    }

    @DisplayName("Verify that equations yield expected answers")
    @ParameterizedTest(name = "{0} = {1}")
    @MethodSource("equations")
    public void verify_equation_evaluates_correctly(@Nonnull final String expression, @Nonnull final Number answer) {
        final var result = Assertions.assertDoesNotThrow(() -> evaluator.evaluate(expression));
        Assertions.assertEquals(result, answer);
    }

    @Nonnull
    private static Stream<Arguments> equations() {
        return Stream.of(
            arguments("50 * 4 - 3 - 5 ^ 3", answer("72")),
            arguments("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3", answer("3.0001220703125")),
            arguments("3 + ((4 * 2) / ((1 - 5) ^ (2 ^ 3)))", answer("3.0001220703125")),
            arguments("10", answer("10")),
            arguments("1", answer("1")),
            arguments("1+1", answer("2")),
            arguments("3 % 2", answer("1")),
            arguments("-1", answer("-1")),
            arguments("- 1", answer("-1")),
            arguments("10 - 1", answer("9")),
            arguments("10 * -((10))", answer("-100")),
            arguments("10 * -(-(10))", answer("100")),
            arguments("---123", answer("-123")),
            arguments("1.5 * 2", answer("3.0")),
            arguments("2 * 1.5", answer("3.0")),
            arguments("1.5 * 3", answer("4.5")),
            arguments("3 * 1.5", answer("4.5")),
            arguments("+1 + -1", answer("0")),
            arguments("+++1 + ---1", answer("0"))
        );
    }

    @Nonnull
    private static Number answer(@Nonnull String value) {
        return new BigDecimal(value);
    }
}