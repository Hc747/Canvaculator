package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

public record LeftBracket() implements Bracket {

    @Nonnull
    public static final Bracket BRACKET = new LeftBracket();
}