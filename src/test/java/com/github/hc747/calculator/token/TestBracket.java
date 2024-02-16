package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestBracket {

    @DisplayName("Bracket factory methods return singleton instances")
    @ParameterizedTest(name = "Bracket::{0} returns {1}::BRACKET singleton")
    @MethodSource("brackets")
    public void bracket_factory_methods_return_singleton_instances(final String method, final String type, final Bracket expected, final Bracket value) {
        assertSame(expected, value);
    }

    @Nonnull
    private static Stream<Arguments> brackets() {
        return Stream.of(
            arguments("left", "LeftBracket", LeftBracket.BRACKET, Bracket.left()),
            arguments("right", "RightBracket", RightBracket.BRACKET, Bracket.right())
        );
    }
}
