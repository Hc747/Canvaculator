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
public class TestExponentiation {

    private BinaryOperator operator;
    private Value left, right;

    @BeforeEach
    public void setup() {
        left = Mockito.spy(new Value(BigDecimal.TWO));
        right = Mockito.spy(new Value(BigDecimal.TWO));
        operator = new Exponentiation();
    }

    @Test
    @DisplayName("Exponentiation::associativity is `Associativity::RIGHT`")
    public void operator_is_right_associative() {
        assertSame(Associativity.RIGHT, operator.associativity());
    }

    @Test
    @DisplayName("Exponentiation::precedence is `Precedence::PRECEDENCE_EXPONENTIATION`")
    public void operator_precedence_is_equal_to_precedence_exponentiation() {
        assertEquals(Precedence.PRECEDENCE_EXPONENTIATION, operator.precedence());
    }

    @Test
    @DisplayName("Exponentiation::evaluate invokes Value::pow")
    public void operator_evaluate_invokes_value_pow() {
        final var expected = new BigDecimal("4");
        final var result = Assertions.assertDoesNotThrow(() -> operator.evaluate(left, right));
        verify(left, times(1)).pow(eq(right));
        assertEquals(expected, result.value());
    }
}