package com.github.hc747.calculator.token;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Precedence {

    public static final int PRECEDENCE_UNARY_OPERATOR = Integer.MAX_VALUE;
    public static final int PRECEDENCE_EXPONENTIATION = PRECEDENCE_UNARY_OPERATOR - 1;
    public static final int PRECEDENCE_MULTIPLICATION_DIVISION_MODULO = PRECEDENCE_EXPONENTIATION - 1;
    public static final int PRECEDENCE_ADDITION_SUBTRACTION = PRECEDENCE_MULTIPLICATION_DIVISION_MODULO - 1;
}