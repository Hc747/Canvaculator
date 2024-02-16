package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

public sealed interface Bracket extends Token
    permits LeftBracket, RightBracket {

    @Nonnull
    static Bracket left() {
        return LeftBracket.BRACKET;
    }

    @Nonnull
    static Bracket right() {
        return RightBracket.BRACKET;
    }
}