package test;

import maths.*;
import maths.matrices.*;

public class Test {
	public static void execute() {
		double x = 10;
		double y = 2;
		//testBasicOperations(BasicOperation.class, x, y);

		Matrix a = MatrixFactory.createMatrix(5, 5);

		double elem[] = {
				1, 2, 3,
				0, 3, 4,
				1, 4, 4
		};

		double elem1[] = {
				0, 1, 1, 0, 3,
				0, 0, 12, 4, 2,
				0, 1, 1, 1, 1,
				3, 3, 2, 2, 3,
				0, 0, 6, 2, 1 };

		double elem2[] = {
				0, 0, 2,
				2, 2, 5,
				1, 3, 1,
				0, 3, 2,
				1, 0, 5 };

		double elem3[] = {2,1,  1,1,  3,6};

		double elem4[] = {
				1, 2, 3,
				1, 2, 3,
				2, 4, 7
		};

		a.populateMatrix(elem1);
		a.printUpperTriangular();
		System.out.println();
		a.printReducedRowEchelonForm();

	}

	/**
	 *	The bounded type token (<T extends Enum<T> & Operation> ensures that
	 *	the Class object represents both Enum and a subtype of Operation in
	 *	order for the operation to be performed.
	 */
	private static <T extends Enum<T> & NumericOperation> void testBasicOperations(Class<T> operationsSet, double x, double y) {
		for (NumericOperation operation : operationsSet.getEnumConstants()) {
			System.out.printf("%.1f %s %.1f = %.1f%n", x, operation, y, operation.apply(x, y));
		}
	}

	private static <T extends Enum<T> & MatrixOperation> void testMatrixOperations(Class<T> operationSet, Matrix a, Matrix b) {
		for (MatrixOperation operation : operationSet.getEnumConstants()) {
			Matrix c = operation.apply(a, b);

			if (c != null) {
				System.out.printf("%n%s%n\t\t%s%n%n%s-------------------%n%s%n%n%n", a, operation, b, operation.apply(a, b));
			}
		}
	}
}
