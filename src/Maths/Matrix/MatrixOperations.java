package Maths.Matrix;

import Maths.Matrix.MatrixOperation;

public enum MatrixOperations implements MatrixOperation {
	PLUS ("+", (a, b) -> {
		if (Matrix.areSameSize(a, b)) {
			int elements[] = new int[a.getCols() * a.getRows()];
			int elementIndex = 0;

			for (int i = 0; i < a.getRows(); i++) {
				for (int j = 0; j < a.getCols(); j++) {
					elements[elementIndex++] = a.matrix[i][j] + b.matrix[i][j];
				}
			}

			Matrix matrixSum = new Matrix(a.getRows(), a.getCols(), false);
			matrixSum.populateMatrix(elements);

			return matrixSum;
		}

		System.out.println("The two matrices are not of the same sizes.");
		return null;
	}
	);

	private String symbol;
	private MatrixOperation operation;

	MatrixOperations(String symbol, MatrixOperation operation) {
		this.symbol = symbol;
		this.operation = operation;
	}

	@Override
	public String toString() {
		return this.symbol;
	}

	public Matrix apply(Matrix a, Matrix b) {
		return operation.apply(a, b);
	}
}
