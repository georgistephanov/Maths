package Maths.Matrices;

import java.util.ArrayList;

public enum MatrixOperations implements MatrixOperation {
	ADD ("+", (a, b) -> {
		if (AbstractMatrix.areSameSize(a, b)) {
			int elements[] = new int[a.getColumns() * a.getRows()];
			int elementIndex = 0;

			for (int i = 0; i < a.getRows(); i++) {
				for (int j = 0; j < a.getColumns(); j++) {
					elements[elementIndex++] = a.at(i, j) + b.at(i, j);
				}
			}

			Matrix matrixSum = MatrixFactory.createMatrix(a.getRows(), a.getColumns());
			matrixSum.populateMatrix(elements);

			return matrixSum;
		}

		System.out.println("The two matrices are not of the same sizes.");
		return null;
	}),
	SUBTRACT ("-", (a, b) -> {
		if (AbstractMatrix.areSameSize(a, b)) {
			int elements[] = new int[a.getColumns() * a.getRows()];
			int elementIndex = 0;

			for (int i = 0; i < a.getRows(); i++) {
				for (int j = 0; j < a.getColumns(); j++) {
					elements[elementIndex++] = a.at(i, j) - b.at(i, j);
				}
			}

			Matrix newMatrix = MatrixFactory.createMatrix(a.getRows(), a.getColumns());
			newMatrix.populateMatrix(elements);

			return newMatrix;
		}

		System.out.println("The two matrices are not of the same sizes.");
		return null;
	}),
	MULTIPLY ("*", (a, b) -> {
		if (AbstractMatrix.canBeMultiplied(a, b)) {
			int elements[] = new int[a.getColumns() * a.getRows()];
			int elementIndex = 0;

			int row = 0;
			int col = 0;

			for (int i = 0; i < a.getRows(); i++) {
				ArrayList<Integer> rowElements = a.getRowElements(i);

				for (int j = 0; j < a.getColumns(); j++) {
					ArrayList<Integer> colElements = b.getColumnElements(j);

					int elementAtThisIndex = 0;

					for (int n = 0; n < a.getColumns(); n++) {
						elementAtThisIndex += rowElements.get(n) * colElements.get(n);
					}

					elements[elementIndex++] = elementAtThisIndex;
				}
			}

			Matrix productMatrix = MatrixFactory.createMatrix(a.getRows(), b.getColumns());
			productMatrix.populateMatrix(elements);

			return productMatrix;
		}

		System.out.println("These two matrices cannot be multiplied.");
		return null;
	});

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
