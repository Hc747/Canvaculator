package com.github.hc747.calculator.tokeniser;

import com.github.hc747.calculator.token.Token;
import jakarta.annotation.Nonnull;

import java.util.Queue;

@FunctionalInterface
public interface Tokeniser {

    @Nonnull
    Queue<Token> tokenise(@Nonnull String expression);
}