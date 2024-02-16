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
            if (Character.isWhitespace(value)) {
                continue;
            }
            final var token = switch (value) {
                case ADDITION, SUBTRACTION -> {
                    // Unary/Binary logic as per: https://wcipeg.com/wiki/Shunting_yard_algorithm
                    if (tokens.isEmpty()) yield unary(value);
                    final var previous = tokens.getLast();
                    final var unary = previous instanceof Operator || previous instanceof LeftBracket;
                    final var binary = previous instanceof Value || previous instanceof RightBracket;
                    if (!unary && !binary) throw new IllegalStateException("A '" + value + "' must be unary OR binary.");
                    yield unary ? unary(value) : binary(value);
                }
                case MULTIPLICATION -> Operator.multiplication();
                case DIVISION -> Operator.division();
                case MODULO -> Operator.modulo();
                case EXPONENTIATION -> Operator.exponentiation();
                case LEFT_BRACKET -> Bracket.left();
                case RIGHT_BRACKET -> Bracket.right();
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
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
                    yield new Value(number);
                }
                default -> throw new IllegalStateException("Unexpected value: " + value);
            };
            tokens.add(token);
        }
        return tokens;
    }

    @Nonnull
    private static Operator unary(char operator) {
        return switch (operator) {
            case ADDITION -> Operator.positive();
            case SUBTRACTION -> Operator.negative();
            default -> throw new IllegalStateException("Unexpected unary operator: " + operator);
        };
    }

    @Nonnull
    private static Operator binary(char operator) {
        return switch (operator) {
            case ADDITION -> Operator.addition();
            case SUBTRACTION -> Operator.subtraction();
            default -> throw new IllegalStateException("Unexpected binary operator: " + operator);
        };
    }
}