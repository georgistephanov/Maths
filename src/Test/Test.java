package Test;

import Maths.*;

public class Test {
	public static void execute() {
		double x = 10;
		double y = 2;

		testBasicOperations(BasicOperation.class, x, y);
	}

	/**
	 *	The bounded type token (<T extends Enum<T> & Operation> ensures that
	 *	the Class object represents both Enum and a subtype of Operation in
	 *	order for the operation to be performed.
	 */
	private static <T extends Enum<T> & Operation> void testBasicOperations(Class<T> operationsSet, double x, double y) {
		for (Operation operation : operationsSet.getEnumConstants()) {
			System.out.printf("%.1f %s %.1f = %.1f%n", x, operation, y, operation.apply(x, y));
		}
	}
}
