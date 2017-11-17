package Test;

import Maths.*;
import Maths.Matrix.Matrix;
import Maths.Matrix.MatrixOperation;

public class Test {
	public static void execute() {
		double x = 10;
		double y = 2;
		//testBasicOperations(BasicOperation.class, x, y);

		Matrix a = new Matrix(3, 5, false);
		Matrix b = new Matrix();

		int elem1[] = {
				3, 3, 2, 2, 3,
				0, 0, 0, 1, 6,
				0, 0, 6, 2, 1 };

		int elem2[] = {
				3, 3, 2,
				2, 2, 5,
				2, 3, 1,
				0, 1, 6,
				7, 0, 5 };

		int elem3[] = {2,1,  1,1,  3,6};

		int elem4[] = {
				1, 2, 3,
				0, 1, 0,
				7, 8, 0
		};

		a.populateMatrix(elem1);

		a.calculateMatrices();
		System.out.println(a);
		a.printUpperTriangularMatrix();
		a.printReducedRowEchelonForm();

		//testMatrixOperations(MatrixOperations.class, a, b);
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
