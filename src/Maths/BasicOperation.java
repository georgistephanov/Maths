package Maths;

public enum BasicOperation implements Operation {
	PLUS 	("+", (x, y) -> x + y),
	MINUS 	("-", (x, y) -> x - y),
	TIMES 	("*", (x, y) -> x * y),
	DIVIDE 	("/", (x, y) -> x / y),
	EXP 	("^", (x, y) -> Math.pow(x, y)),
	REM 	("%", (x, y) -> x % y);

	private final String symbol;
	private final Operation operation;

	BasicOperation(String symbol, Operation operation) {
		this.symbol = symbol;
		this.operation = operation;
	}

	@Override
	public String toString() {
		return symbol;
	}

	@Override
	public double apply(double x, double y) {
		return operation.apply(x, y);
	}
}
