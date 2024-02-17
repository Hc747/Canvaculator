package com.github.hc747.calculator.tokeniser;

import com.github.hc747.calculator.token.Bracket;
import com.github.hc747.calculator.token.Operator;
import com.github.hc747.calculator.token.Token;
import com.github.hc747.calculator.token.Value;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestInfixExpressionTokeniser {

    private Tokeniser tokeniser;

    @BeforeEach
    public void setup() {
        tokeniser = new InfixExpressionTokeniser();
    }

    @DisplayName("ExpressionTokeniser::tokenise whitespace returns no tokens")
    @ParameterizedTest(name = "ExpressionTokeniser::tokenise {1} returns no tokens")
    @MethodSource("whitespace")
    public void expression_tokeniser_tokenise_whitespace_returns_no_tokens(final String expression, final String description) {
        final var tokens = assertDoesNotThrow(() -> tokeniser.tokenise(expression));
        assertTrue(tokens.isEmpty());
    }

    @DisplayName("ExpressionTokeniser::tokenise invalid tokens throws exception")
    @ParameterizedTest(name = "ExpressionTokeniser::tokenise {0} throws exception")
    @MethodSource("invalid")
    public void expression_tokeniser_tokenise_invalid_tokens_throws_exception(final char token) {
        final var cause = assertThrows(IllegalStateException.class, () -> tokeniser.tokenise("" + token));
        assertEquals("Unexpected token: " + token, cause.getMessage());
    }

    @DisplayName("ExpressionTokeniser::tokenise expression yields expected tokens")
    @ParameterizedTest(name = "ExpressionTokeniser::tokenise {0} returns {1}")
    @MethodSource("expressions")
    public void expression_tokeniser_tokenise_expression_yields_expected_tokens(final String expression, final String name, final Collection<Token> expected) {
        final var tokens = assertDoesNotThrow(() -> tokeniser.tokenise(expression));
        assertEquals(expected.size(), tokens.size());
        assertIterableEquals(expected, tokens);
    }

    @Nonnull
    private static Stream<Arguments> whitespace() {
        return Stream.of(
            arguments("", "Empty String"),
            arguments(" ", "Space"),
            arguments("\t", "Tab"),
            arguments("\r", "Carriage return"),
            arguments("\n", "Line feed"),
            arguments("\r\n", "CRLF"),
            arguments("    ", "Spaces")
        );
    }

    @Nonnull
    private static Stream<Arguments> expressions() {
        return Stream.of(
            // simple binary operators
            arguments("*", "multiplication", List.of(Operator.multiplication())),
            arguments("/", "division", List.of(Operator.division())),
            arguments("%", "modulo", List.of(Operator.modulo())),
            arguments("^", "exponentiation", List.of(Operator.exponentiation())),
            // brackets
            arguments("(", "left bracket", List.of(Bracket.left())),
            arguments(")", "right bracket", List.of(Bracket.right())),
            // unary +/-
            arguments("+", "unary plus", List.of(Operator.positive())),
            arguments("-", "unary minus", List.of(Operator.negative())),
            // binary +/-
            arguments("1 + 2", "1 + 2", List.of(new Value(BigDecimal.ONE), Operator.addition(), new Value(BigDecimal.TWO))),
            arguments("1 - 2", "1 + 2", List.of(new Value(BigDecimal.ONE), Operator.subtraction(), new Value(BigDecimal.TWO)))
            //TODO: extend test cases to be more comprehensive
        );
    }

    @Nonnull
    private static Stream<Arguments> invalid() {
        final var lower = IntStream.rangeClosed('a', 'z').mapToObj(i -> (char) i);
        final var upper = IntStream.rangeClosed('A', 'Z').mapToObj(i -> (char) i);
        final var symbols = Stream.of('~', '!', '@', '#', '$', '&', '_', '=', '|', '{', '}', '<', '>', '?', ',');
        return Stream.concat(Stream.concat(lower, upper), symbols).map(Arguments::of);
    }
}
