package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestOperator {

    @DisplayName("Operator factory methods return singleton instances")
    @ParameterizedTest(name = "Operator::{0} returns {1}::OPERATOR singleton")
    @MethodSource("operators")
    public void operator_factory_methods_return_singleton_instances(final String method, final String type, final Operator expected, final Operator value) {
        assertSame(expected, value);
    }

    @Nonnull
    private static Stream<Arguments> operators() {
        return Stream.of(
            arguments("negative", "UnaryNegative", UnaryNegative.OPERATOR, Operator.negative()),
            arguments("positive", "UnaryPositive", UnaryPositive.OPERATOR, Operator.positive()),
            arguments("addition", "Addition", Addition.OPERATOR, Operator.addition()),
            arguments("subtraction", "Subtraction", Subtraction.OPERATOR, Operator.subtraction()),
            arguments("multiplication", "Multiplication", Multiplication.OPERATOR, Operator.multiplication()),
            arguments("division", "Division", Division.OPERATOR, Operator.division()),
            arguments("modulo", "Modulo", Modulo.OPERATOR, Operator.modulo()),
            arguments("exponentiation", "Exponentiation", Exponentiation.OPERATOR, Operator.exponentiation()),
            arguments("unary(false)", "UnaryNegative", UnaryNegative.OPERATOR, Operator.unary(false)),
            arguments("unary(true)", "UnaryPositive", UnaryPositive.OPERATOR, Operator.unary(true)),
            arguments("binary(false)", "Subtraction", Subtraction.OPERATOR, Operator.binary(false)),
            arguments("binary(true)", "Addition", Addition.OPERATOR, Operator.binary(true))
        );
    }
}