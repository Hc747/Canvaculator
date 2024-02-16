package com.github.hc747.calculator.tokeniser;

import com.github.hc747.calculator.token.Token;
import jakarta.annotation.Nonnull;

import java.util.Queue;

public interface Tokeniser {

    @Nonnull
    Queue<Token> tokenise(@Nonnull String expression);
}