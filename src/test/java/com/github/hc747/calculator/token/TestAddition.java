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
public class TestAddition {

    private BinaryOperator operator;
    private Value left, right;

    @BeforeEach
    public void setup() {
        left = Mockito.spy(new Value(BigDecimal.ONE));
        right = Mockito.spy(new Value(BigDecimal.TWO));
        operator = new Addition();
    }

    @Test
    @DisplayName("Addition::associativity is `Associativity::LEFT`")
    public void operator_is_left_associative() {
        assertSame(Associativity.LEFT, operator.associativity());
    }

    @Test
    @DisplayName("Addition::precedence is `Precedence::PRECEDENCE_ADDITION_SUBTRACTION`")
    public void operator_precedence_is_equal_to_precedence_addition_subtraction() {
        assertEquals(Precedence.PRECEDENCE_ADDITION_SUBTRACTION, operator.precedence());
    }

    @Test
    @DisplayName("Addition::evaluate invokes Value::add")
    public void operator_evaluate_invokes_value_add() {
        final var expected = new BigDecimal("3");
        final var result = Assertions.assertDoesNotThrow(() -> operator.evaluate(left, right));
        verify(left, times(1)).add(eq(right));
        assertEquals(expected, result.value());
    }
}