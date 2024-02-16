package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestUnaryNegative {

    private UnaryOperator operator;

    @BeforeEach
    public void setup() {
        operator = new UnaryNegative();
    }

    @Test
    @DisplayName("UnaryNegative::associativity is `Associativity::LEFT`")
    public void operator_is_left_associative() {
        assertSame(Associativity.LEFT, operator.associativity());
    }

    @Test
    @DisplayName("UnaryNegative::precedence is `Precedence::PRECEDENCE_UNARY_OPERATOR`")
    public void operator_precedence_is_equal_to_precedence_unary_negative_positive() {
        assertEquals(Precedence.PRECEDENCE_UNARY_OPERATOR, operator.precedence());
    }

    @ParameterizedTest
    @MethodSource("values")
    @DisplayName("UnaryNegative::evaluate invokes Value::negate")
    public void operator_evaluate_invokes_value_negate(final BigDecimal input, final BigDecimal expected) {
        final var value = Mockito.spy(new Value(input));
        final var result = Assertions.assertDoesNotThrow(() -> operator.evaluate(value));
        verify(value, times(1)).negate();
        assertEquals(expected, result.value());
    }

    @Nonnull
    private static Stream<Arguments> values() {
        return Stream.of(
            Arguments.of(BigDecimal.ONE, BigDecimal.ONE.negate()),
            Arguments.of(BigDecimal.ONE.negate(), BigDecimal.ONE)
        );
    }
}