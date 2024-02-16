package com.github.hc747.calculator.token;

public sealed interface Token
    permits Bracket, Value, Operator {
}