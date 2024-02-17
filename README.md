A simple calculator written in Java 21 implementing the shunting yard algorithm.

# Example
```Java
public static void main(String[] args) {
  final var calculator = new ShuntingYardExpressionEvaluator(new InfixExpressionTokeniser());
  final var expression = "-3 + ((4 * 2) / ((1 - 5) ^ (2 ^ 3)))";
  final var result = calculator.evaluate(expression); // -2.9998779296875
}
```
