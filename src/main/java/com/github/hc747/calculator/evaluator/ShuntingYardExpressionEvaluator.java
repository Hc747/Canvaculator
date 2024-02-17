package com.github.hc747.calculator.evaluator;

import com.github.hc747.calculator.token.*;
import com.github.hc747.calculator.tokeniser.Tokeniser;
import jakarta.annotation.Nonnull;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public record ShuntingYardExpressionEvaluator(
    @Nonnull Tokeniser tokeniser
) implements ExpressionEvaluator {

    public ShuntingYardExpressionEvaluator {
        Objects.requireNonNull(tokeniser, "tokeniser");
    }

    @Nonnull
    private Queue<Token> RPN(@Nonnull Queue<Token> tokens) {
        // The logic for converting from infix notation to postfix notation (reverse polish notation) as per:
        // - https://en.wikipedia.org/wiki/Shunting_yard_algorithm#The_algorithm_in_detail
        // - https://wcipeg.com/wiki/Shunting_yard_algorithm
        final var operators = new ArrayDeque<Token>();
        final var output = new ArrayDeque<Token>();

        while (!tokens.isEmpty()) {
            final var token = tokens.poll();
            switch (token) {
                case Value value -> output.push(value);
                case UnaryOperator operator -> operators.push(operator);
                case BinaryOperator operator -> {
                    while (operators.peek() instanceof Operator other) {
                        final var supersedes = (other.precedence() > operator.precedence()) || (operator.precedence() == other.precedence() && operator.associativity() == Associativity.LEFT);
                        if (!supersedes) break;
                        output.push(operators.poll());
                    }
                    operators.push(operator);
                }
                case LeftBracket bracket -> operators.push(bracket);
                case RightBracket bracket -> {
                    Token top;
                    while ((top = operators.poll()) != null) {
                        if (top instanceof Operator) {
                            output.push(top);
                        } else if (top instanceof LeftBracket) {
                            break;
                        } else {
                            throw new IllegalStateException("Unexpected token in operator stack: " + top);
                        }
                    }
                }
                case null, default -> throw new IllegalStateException("Unexpected token in input: " + token);
            }
        }

        while (!operators.isEmpty()) {
            final var token = operators.poll();
            if (token instanceof Bracket bracket) {
                throw new IllegalStateException("Mismatched brackets in operator stack: " + bracket);
            } else {
                output.push(token);
            }
        }

        return output.reversed();
    }

    @Nonnull
    public Number evaluate(@Nonnull String expression) {
        final var infix = tokeniser.tokenise(expression);
        final var postfix = RPN(infix);
        final var output = new ArrayDeque<Value>();

        while (!postfix.isEmpty()) {
            final var token = postfix.poll();
            switch (token) {
                case Value value -> output.push(value);
                case Operator operator -> output.push(operator.evaluate(output));
                default -> throw new IllegalStateException("Unexpected token: " + token);
            }
        }

        if (output.size() != 1) {
            throw new IllegalStateException("Malformed output: " + output);
        }

        return output.poll().value();
    }
}
