package maths.matrices;

import maths.Equations;

import java.util.ArrayList;

public class ConcreteSquareMatrix extends AbstractMatrix implements SquareMatrix {

	private double determinant;
	private boolean determinantCalculated = false;
	private double [] eigenvalues;
	private double [][] lowerTriangular;

	ConcreteSquareMatrix(int size) {
		super(size, size, false);
		type = Type.SQUARE;
	}
	ConcreteSquareMatrix(int size, boolean populateRandomly) {
		super(size, size, populateRandomly);
		type = Type.SQUARE;
	}

	/**
	 * Calculates the upper triangular matrix via elimination
	 * and keeps the correct steps taken in the lower triangular
	 * matrix.
	 */
	@Override
	public void calculateUpperAndLowerTriangular() {
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

		calculateUpperAndLowerTriangularMatrices();
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
					calculateUpperAndLowerTriangular();
				}

				for (int row = 0; row < rows; row++) {
					determinant *= upperTriangular[row][row];
				}
			}

			determinantCalculated = true;
		}

		if (rowExchangesPerformed % 2 == 1) {
			determinant = -determinant;
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

	/**
	 * Checks the determinant to find out if the matrix has inverse.
	 * @return true if the determinant is not equal to zero, false otherwise
	 */
	public boolean hasInverse() {
		return det() != 0;
	}

	/**
	 * Checks whether the matrix is symmetric.
	 * @return true if the matrix is symmetric
	 */
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
	 * Calculates the upper triangular (and lower triangular if such exists i.e. no row exchanges)
	 * matrix and finds the number of pivots
	 */
	private void calculateUpperAndLowerTriangularMatrices() {
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
		if ( rowExchangesPerformed != 0 ) {
			// Removes the lower triangular matrix as it is usually not computed if row exchanges has happened
			lowerTriangular = null;
		}
	}

	/**
	 * Generates and prints the lower triangular if such can be computed (i.e. no row exchanges
	 * had been performed during elimination towards the upper-triangular matrix)
	 * @return the matrix L or null if it can't be computed
	 */
	public Matrix getLowerTriangular() {
		if ( !hasLowerTriangular() ) {
			return null;
		}
		else if (upperTriangular == null) {
			calculateUpperAndLowerTriangular();
		}

		return generateMatrix(lowerTriangular, rows, columns);
	}

	/**
	 * Computes and returns the upper-triangular matrix after elimination.
	 * @return the matrix U
	 */
	public Matrix getUpperTriangular() {
		if (upperTriangular == null) {
			calculateUpperAndLowerTriangular();
		}

		return generateMatrix(upperTriangular, rows, columns);
	}

	/**
	 * A method to check whether a lower triangular matrix can be computed
	 * for the upper triangular matrix.
	 * @return true if no row exchanges has been done while computing the upper triangular matrix
	 */
	public boolean hasLowerTriangular() {
		if (upperTriangular == null) {
			calculateUpperAndLowerTriangular();
		}

		if (rowExchangesPerformed != 0 || lowerTriangular == null) {
			return false;
		}

		return true;
	}

	/**
	 * Returns a two-dimensional array filled with ones on the main diagonal and with
	 * zeros everywhere else (the identity matrix).
	 * @param size the size of the matrix
	 * @return the identity matrix as a two-dimensional array
	 */
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
