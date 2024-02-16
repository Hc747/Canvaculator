package com.github.hc747.calculator.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class TestBracket {

    @Test
    @DisplayName("Bracket::left returns LeftBracket::BRACKET singleton")
    public void bracket_left_returns_left_bracket() {
        assertSame(LeftBracket.BRACKET, Bracket.left());
    }

    @Test
    @DisplayName("Bracket::right returns RightBracket::BRACKET singleton")
    public void bracket_right_returns_right_bracket() {
        assertSame(RightBracket.BRACKET, Bracket.right());
    }
}
