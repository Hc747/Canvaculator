package com.github.hc747.calculator.token;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestDivision {

    private BinaryOperator operator;
    private Value left, right;

    @BeforeEach
    public void setup() {
        left = Mockito.spy(new Value(BigDecimal.ONE));
        right = Mockito.spy(new Value(BigDecimal.TWO));
        operator = new Division();
    }

    @Test
    @DisplayName("Division::associativity is `Associativity::LEFT`")
    public void operator_is_left_associative() {
        assertSame(Associativity.LEFT, operator.associativity());
    }

    @Test
    @DisplayName("Division::precedence is `Precedence::PRECEDENCE_MULTIPLICATION_DIVISION_MODULO`")
    public void operator_precedence_is_equal_to_precedence_multiplication_division_modulo() {
        assertEquals(Precedence.PRECEDENCE_MULTIPLICATION_DIVISION_MODULO, operator.precedence());
    }

    @Test
    @DisplayName("Division::evaluate invokes Value::divide")
    public void operator_evaluate_invokes_value_divide() {
        final var expected = new BigDecimal("0.5");
        final var result = Assertions.assertDoesNotThrow(() -> operator.evaluate(left, right));
        verify(left, times(1)).divide(eq(right));
        assertEquals(expected, result.value());
    }
}