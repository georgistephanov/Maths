package maths.matrices;

import maths.Equations;

import java.util.ArrayList;

public class SquareMatrix extends AbstractMatrix {

	private double determinant;
	private boolean determinantCalculated = false;
	private double [] eigenvalues;
	private double [][] lowerTriangular;


	SquareMatrix(int size) {
		super(size, size, false);
		type = Type.SQUARE;
	}
	SquareMatrix(int size, boolean populateRandomly) {
		super(size, size, populateRandomly);
		type = Type.SQUARE;
	}

	/**
	 * Calculates the determinant of the matrix.
	 */
	public double det() {
		if ( !determinantCalculated ) {

			if (rows == 1) {
				determinant = matrix[rows][columns];
			} else if (rows == 2) {
				// Calculated via det(A 2x2) = ad - bc;
				determinant = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
			} else {
				determinant = 1;

				if (upperTriangular == null) {
					calculateUpperTriangular();
				}

				for (int row = 0; row < rows; row++) {
					determinant *= upperTriangular[row][row];
				}
			}

			determinantCalculated = true;
		}

		return determinant;
	}

	/**
	 * Calculates the eigenvalues of the matrix.
	 * Supports up to 2x2 matrices as of now.
	 */
	public double [] eig() {
		assert rows <= 2;

		if (eigenvalues == null) {
			eigenvalues = new double[rows];
		}

		if (rows == 1) {
			eigenvalues[0] = matrix[rows][columns];
		}
		else if (rows == 2) {
			// det(A - lambda*I) = 0 which leads to a quadratic equation
			StringBuilder equation = new StringBuilder();
			equation.append("x^2");

			// this is the coefficient in front of the x
			double b = -(matrix[0][0] + matrix[1][1]);

			// ad - bc
			double c = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

			if (b >= 0) {
				equation.append("+");
			}
			if (Math.floor(b) == b) {
				equation.append((int) b);
			} else {
				equation.append(b);
			}
			equation.append("x");

			if (c != 0) {
				if (c > 0) {
					equation.append("+");
				}
				if (Math.floor(c) == c) {
					equation.append((int) c);
				} else {
					equation.append(c);
				}
			}

			System.out.println(equation);

			eigenvalues = Equations.QUADRATIC.apply(equation.toString());
			System.out.println("lambda1=" + eigenvalues[0] + "\tlambda2=" + eigenvalues[1] );
		}

		return eigenvalues;
	}

	public boolean hasInverse() {
		return det() != 0;
	}

	public boolean isSymmetric() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				if (row != col) {
					if (matrix[row][col] != matrix[col][row]) {
						return false;
					}
				}
			}
		}
		
		return true;
	}

	/**
	 * Calculates the upper triangular matrix via elimination
	 * and keeps the correct steps taken in the lower triangular
	 * matrix.
	 */
	@Override
	public void calculateUpperTriangular() {
		assert matrix.length > 0;

		if (upperTriangular == null) {
			upperTriangular = new double[rows][columns];
			lowerTriangular = getIdentityMatrix(rows);
		}

		// Copy the original matrix
		{
			for (int row = 0; row < rows; row++) {
				upperTriangular[row] = matrix[row].clone();
			}
		}

		calculateUpperTriangularMatrix();
	}
	/**
	 * Helper method which does the actual calculations for the
	 * calculateUpperTriangular() method
	 */
	private void calculateUpperTriangularMatrix() {
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

				ArrayList<Double> pivotRow = getRowElements(upperTriangular, column);
				ArrayList<Double> currentRow = getRowElements(upperTriangular, row);
				ArrayList<Double> newRow = new ArrayList<>();

				// Get the correct row multiplier
				double multiplierPivotRow = upperTriangular[row][pivotColumn] / getFirstNonZeroElement(pivotRow);

				// Set the multiplier in the lower triangular matrix
				lowerTriangular[row][column] = multiplierPivotRow;

				for (int i = 0; i < columns; i++) {
					double currentRowElement = currentRow.get(i);
					double pivotRowElement = pivotRow.get(i);

					// If both elements are zero -> no need to make calculations
					if (currentRowElement == 0 && pivotRowElement == 0) {
						newRow.add((double) 0);
					} else {
						newRow.add( currentRowElement - pivotRowElement * multiplierPivotRow );
					}
				}

				// Fill the current row with the newly calculated row
				fillRow(upperTriangular, row, newRow);
			}
		}

		// Finds and stores the number of pivots of the upper triangular matrix
		findNumberOfPivots();

		// Checks whether row exchanges has been performed
		if ( rowExchangesPerformed ) {
			// Removes the lower triangular matrix as it is usually not computed if row exchanges has happened
			lowerTriangular = null;
		}
	}

	/**
	 * A method to check whether a lower triangular matrix can be computed
	 * for the upper triangular matrix.
	 * @return true if no row exchanges has been done while computing the upper triangular matrix
	 */
	public boolean hasLowerTriangular() {
		if (upperTriangular == null) {
			calculateUpperTriangular();
		}

		if (rowExchangesPerformed || lowerTriangular == null) {
			return false;
		}

		return true;
	}

	/**
	 * Prints the lower triangular matrix if it could be computed
	 */
	public void printLowerTriangular() {
		if ( hasLowerTriangular() ) {
			printMatrix(lowerTriangular);
		}
		else {
			System.out.println("The lower triangular matrix could not be computed due to row exchanges needed while computing the upper triangular matrix.");
		}
	}

	// TODO: Make a general method in the abstract class to create and return given matrix
	// TODO: Also add the necessary checks as there is a NullPtrException if lower triangular cannot be computed
	public Matrix getLowerTriangular() {
		assert hasLowerTriangular();

		double [] elements = new double[rows * rows];
		int elementIndex = 0;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				elements[elementIndex++] = lowerTriangular[row][col];
			}
		}

		Matrix L = MatrixFactory.createSquareMatrix(rows);
		L.populateMatrix(elements);

		return L;
	}
	public Matrix getUpperTriangular() {
		assert upperTriangular != null;

		double [] elements = new double[rows * rows];
		int elementIndex = 0;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				elements[elementIndex++] = upperTriangular[row][col];
			}
		}

		Matrix U = MatrixFactory.createSquareMatrix(rows);
		U.populateMatrix(elements);

		return U;
	}

	private double [][] getIdentityMatrix(int size) {
		double [][] matrix = new double[size][size];

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (row == col) {
					matrix[row][col] = 1;
				}
				else {
					matrix[row][col] = 0;
				}
			}
		}

		return matrix;
	}

}
