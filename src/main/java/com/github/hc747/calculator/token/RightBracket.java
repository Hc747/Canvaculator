package com.github.hc747.calculator.token;

import jakarta.annotation.Nonnull;

public record RightBracket() implements Bracket {

    @Nonnull
    public static final Bracket BRACKET = new RightBracket();
}