package Maths;

public enum BasicOperation implements NumericOperation {
	PLUS 		("+", (x, y) -> x + y),
	MINUS 		("-", (x, y) -> x - y),
	MULTIPLY 	("*", (x, y) -> x * y),
	DIVIDE 		("/", (x, y) -> x / y),
	POW 		("^", (x, y) -> Math.pow(x, y)),
	REM 		("%", (x, y) -> x % y);

	private final String symbol;
	private final NumericOperation operation;

	BasicOperation(String symbol, NumericOperation operation) {
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
