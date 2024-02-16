package com.github.hc747.calculator.tokeniser;

import com.github.hc747.calculator.token.*;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Queue;

public record ExpressionTokeniser() implements Tokeniser {

    private static final char SUBTRACTION = '-';
    private static final char ADDITION = '+';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';
    private static final char MODULO = '%';
    private static final char EXPONENTIATION = '^';
    private static final char LEFT_BRACKET = '(';
    private static final char RIGHT_BRACKET = ')';

    @Nonnull
    @Override
    public Queue<Token> tokenise(@Nonnull String expression) {
        final var tokens = new ArrayDeque<Token>();
        var index = 0;
        while (index < expression.length()) {
            final var value = expression.charAt(index++);
            final Token token;
            if (Character.isWhitespace(value)) {
                continue;
            } else if (Character.isDigit(value)) {
                // Supports a subset of valid BigDecimals as per the grammar specified in: java.math.BigDecimal.
                // Does not support BigDecimals with exponents denoted by 'e/E'.
                // Does not attempt to defend against improperly formatted BigDecimals, i.e., '1...5.00.0'
                final var builder = new StringBuilder().append(value);
                while (index < expression.length()) {
                    final var next = expression.charAt(index);
                    final var valid = Character.isDigit(next) || next == '.';
                    if (!valid) break;
                    builder.append(next);
                    index++;
                }
                final var number = new BigDecimal(builder.toString());
                token = new Value(number);
            } else {
                token = switch (value) {
                    case ADDITION, SUBTRACTION -> {
                        // Unary/Binary logic as per: https://wcipeg.com/wiki/Shunting_yard_algorithm
                        final var positive = value == ADDITION;
                        if (tokens.isEmpty()) yield Operator.unary(positive);
                        final var previous = tokens.getLast();
                        final var unary = previous instanceof Operator || previous instanceof LeftBracket;
                        final var binary = previous instanceof Value || previous instanceof RightBracket;
                        if (!unary && !binary) throw new IllegalStateException("A '" + value + "' must be unary OR binary.");
                        yield unary ? Operator.unary(positive) : Operator.binary(positive);
                    }
                    case MULTIPLICATION -> Operator.multiplication();
                    case DIVISION -> Operator.division();
                    case MODULO -> Operator.modulo();
                    case EXPONENTIATION -> Operator.exponentiation();
                    case LEFT_BRACKET -> Bracket.left();
                    case RIGHT_BRACKET -> Bracket.right();
                    default -> throw new IllegalStateException("Unexpected value: " + value);
                };
            }
            tokens.add(token);
        }
        return tokens;
    }
}