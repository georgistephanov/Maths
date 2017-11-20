package Maths.Matrices;

import Algorithms.Algorithm;

import java.util.ArrayList;

public class TallMatrix extends AbstractMatrix {

	TallMatrix(int rows, int cols) {
		super(rows, cols, false);
	}

	TallMatrix(int rows, int cols, boolean populateRandomly) {
		super(rows, cols, populateRandomly);
	}

	public void calculateUpperTriangularMatrix() {
		for (int column = 0; column < columns; column++) {
			// Reorder the pivot rows if necessary on every level of elimination
			reorderPivotRows(upperTriangular);

			// Make all zeros under the current column
			for (int row = column + 1; row < rows; row++) {

				// If the current element of the pivot row is 0 -> find the pivot variable
				int pivotColumn = column;
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

				// Get the correct row multipliers
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

					// If both elements are zero -> no need to make calculations
					if (currentRowElement == 0 && pivotRowElement == 0) {
						newRow.add(0);
					} else {
						newRow.add( currentRowElement * multiplierCurrentRow - pivotRowElement * multiplierPivotRow );
					}
				}

				// Fill the current row with the newly calculated row
				fillRow(upperTriangular, row, newRow);
			}
		}
	}
}
