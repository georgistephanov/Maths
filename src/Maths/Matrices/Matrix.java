package Maths.Matrices;

import Algorithms.Algorithm;

import java.util.ArrayList;
import java.util.Random;

public interface Matrix {
	void populateMatrix(int [] elements);
	Matrix add(Matrix a, Matrix b);
	Matrix multiply(Matrix a, Matrix b);

	// TODO: Think of an elegant way to calculate rectangular matrices too!!!
	/*private void calculateUpperTriangular() {
		if (matrix.length > 0) {
			if (upperTriangular == null) {
				upperTriangular = new int[rows][columns];
			}

			// Copy the original matrix
			{
				for (int row = 0; row < rows; row++) {
					upperTriangular[row] = matrix[row].clone();
				}
			}

			calculateUpperTriangular(upperTriangular);
		}
	}

	private void calculateUpperTriangular(int [][] upperTriangular) {
		// Reorders the pivot rows if necessary
		reorderPivotRows(upperTriangular);

		for (int column = 0; column < columns; column++) {
			// Make all zeros under the current column
			for (int row = column + 1; row < rows; row++) {
				int pivotColumn = column;
				// If the current element of the pivot row is 0 -> find the pivot variable
				while ( pivotColumn < columns && upperTriangular[column][pivotColumn] == 0 ) {
					pivotColumn++;
				}

				if (pivotColumn == columns) {
					// Zero row
					continue;
				}

				ArrayList<Integer> pivotRow = getRowElements(upperTriangular, column);
				ArrayList<Integer> currentRow = getRowElements(upperTriangular, row);
				ArrayList<Integer> newRow = new ArrayList<>();

				int multiplierPivotRow = upperTriangular[row][pivotColumn];
				int multiplierCurrentRow = getFirstNonZeroElement(pivotRow);

				// Find the smallest possible multipliers
				int multipliersGCD = Algorithm.GCD(multiplierPivotRow, multiplierCurrentRow);
				if ( multipliersGCD > 1 ) {
					multiplierPivotRow /= multipliersGCD;
					multiplierCurrentRow /= multipliersGCD;
				}

				for (int i = 0; i < columns; i++) {
					int currentRowElement = currentRow.get(i);
					int pivotRowElement = pivotRow.get(i);

					if (currentRowElement == 0 && pivotRowElement == 0) {
						newRow.add(0);
					} else {
						int newRowElement = currentRow.get(i) * multiplierCurrentRow
								- pivotRow.get(i) * multiplierPivotRow;

						newRow.add(newRowElement);
					}
				}

				fillRow(upperTriangular, row, newRow);
			}
		}
	} */

	/* ======== HELPER METHODS ======== */
	private int getFirstNonZeroElement(ArrayList<Integer> arr) {
		for (int a : arr) {
			if (a != 0)
				return a;
		}

		return 0;
	}
}
