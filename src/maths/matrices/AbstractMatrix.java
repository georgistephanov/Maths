package maths.matrices;

import java.util.ArrayList;
import java.util.Random;

abstract class AbstractMatrix implements Matrix {
	private int MATRIX_MAX_SIZE = 10;
	private int MATRIX_DEFAULT_SIZE = 3;
	private int MAX_RANDOM_ELEMENT_VALUE = 5;
	private int MIN_RANDOM_ELEMENT_VALUE = -5;
	private boolean NEGATIVE_RANDOM_ELEMENTS_ALLOWED = false;

	int rows;
	int columns;
	private int pivots = -1;
	double [][] matrix;
	double [][] upperTriangular;
	private double [][] reducedRowEchelon;
	int rowExchangesPerformed = 0;

	/**
	 * The three possible types of matrices.
	 */
	enum Type {
		SQUARE, TALL, WIDE
	}
	Type type;

	AbstractMatrix(int rows, int cols, boolean populateRandomly) {
		if (rows > 0 && rows <= MATRIX_MAX_SIZE && cols > 0 && cols <= MATRIX_MAX_SIZE) {
			this.rows = rows;
			this.columns = cols;

			matrix = new double[rows][cols];
		} else {
			this.rows = MATRIX_DEFAULT_SIZE;
			this.columns = MATRIX_DEFAULT_SIZE;
		}

		if (populateRandomly) {
			populateMatrixRandomly();
		}
	}

	/**
	 * Checks whether two matrices are equal. In order to be considered equal, the matrices should be
	 * of the same size (row and column numbers must match) and every element must be equal to the
	 * element at the corresponding position of the other matrix.
	 * @param obj to be compared to the matrix
	 * @return true if the two matrices are completely identical
	 */
	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}

		if ( obj == null ) {
			return false;
		}

		if ( !(obj instanceof AbstractMatrix) ) {
			return false;
		}

		AbstractMatrix m = (AbstractMatrix) obj;
		if ( rows == m.getRows() && columns == m.getColumns() ) {
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < columns; col++) {
					if ( this.matrix[row][col] != m.matrix[row][col] ) {
						return false;
					}
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return (int) ( getMatrixElementsSum() + rows + columns ) % 97;
	}

	@Override
	public String toString() {
		StringBuilder m = new StringBuilder();

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {

				// Check if the number is a whole number. If so -> print it without the decimal place
				if ( matrix[row][col] == Math.floor(matrix[row][col]) ) {
					m.append( (int) matrix[row][col] );
				} else {
					m.append( matrix[row][col] );
				}
				m.append("\t\t");
			}
			m.append("\n");
		}

		return m.toString();
	}


	/* ======== INTERFACE METHODS ======== */

	/**
	 * This method populates the matrix by an array of given element values.
	 * If the array's length is less than the size of the matrix, the rest
	 * of the elements are automatically filled with zeros.
	 * @param elements -> array of integers with the values in a following
	 *                    order going row by row
	 */
	public void populateMatrix(double [] elements) {
		assert elements != null && elements.length > 0;

		int elementIndex = 0;

		if (elements.length >= (rows * columns) - 1) {

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					matrix[i][j] = elements[elementIndex++];
				}
			}

		} else {

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					if (elementIndex < elements.length) {
						matrix[i][j] = elements[elementIndex++];
					} else {
						matrix[i][j] = 0;
					}
				}
			}
		}
	}

	/**
	 * @return the number of rows of the matrix
	 */
	public int getRows() { return rows; }

	/**
	 * @return the number of columns of the matrix
	 */
	public int getColumns() { return columns; }

	/**
	 * Calculates and returns the transpose of the matrix
	 * @return transpose matrix
	 */
	public Matrix transpose() {
		double [] elements = new double[rows * columns];
		int elementIndex = 0;

		for (int col = 0; col < columns; col++) {
			for (int row = 0; row < rows; row++) {
				elements[elementIndex++] = matrix[row][col];
			}
		}

		Matrix transpose = MatrixFactory.createMatrix(columns, rows);
		transpose.populateMatrix(elements);

		return transpose;
	}

	/**
	 * Returns the element at the (row, col) position of the matrix
	 * @param row number
	 * @param col number
	 * @return the element at the (row, col)
	 * @throws IllegalArgumentException if the row or the col is not in the matrix
	 */
	public double at(int row, int col) {
		if (row >= rows) {
			throw new IllegalArgumentException("The row parameter is out of bounds");
		}
		if (col >= columns) {
			throw new IllegalArgumentException("The column parameter is out of bounds");
		}
		return matrix[row][col];
	}

	/**
	 * Checks whether the passed matrix is of the same size (equal number of rows and columns)
	 * @param a matrix
	 * @return true if the matrix passed as parameter is of the same size
	 */
	public boolean isOfSameSize(Matrix a) {
		assert a != null;

		return ( rows == a.getRows()
				&& columns == a.getColumns());
	}

	/**
	 * Checks whether two matrices can be multiplied (i.e. the number of columns
	 * of the first matrix is equal to the number of rows of the second)
	 * @param a matrix
	 * @return true if the number of columns matches the number of rows of the passed matrix
	 */
	public boolean canBeMultiplied(Matrix a) {
		assert a != null;

		return columns == a.getRows();
	}

	/**
	 * Adds two matrices
	 * @param a matrix to be added
	 * @return the matrix produced from the addition
	 * @throws IllegalArgumentException if the matrix passed is not of the same size
	 */
	public Matrix add(Matrix a) {
		if ( !(isOfSameSize(a)) ) {
			throw new IllegalArgumentException("The matrix must be of the same size");
		}

		return MatrixOperations.ADD.apply(this, a);
	}

	/**
	 * Subtracts two matrices
	 * @param a matrix to be subtracted
	 * @return the matrix produced from the addition
	 * @throws IllegalArgumentException if the matrix passed is not of the same size
	 */
	public Matrix subtract(Matrix a) {
		if ( !(isOfSameSize(a)) ) {
			throw new IllegalArgumentException("The matrix must be of the same size");
		}

		return MatrixOperations.SUBTRACT.apply(this, a);
	}

	/**
	 * Multiplies two matrices
	 * @param a matrix
	 * @return the matrix produced by the multiplication
	 * @throws IllegalArgumentException if the matrices could not be legally multiplied
	 */
	public Matrix multiply(Matrix a) {
		if ( !(canBeMultiplied(a)) ) {
			throw new IllegalArgumentException("The matrices cannot be multiplied.");
		}

		return MatrixOperations.MULTIPLY.apply(this, a);
	}

	/**
	 * Prints the upper triangular matrix
	 */
	public void printUpperTriangular() {
		if (upperTriangular == null) {
			calculateUpperAndLowerTriangular();
		}

		printMatrix(upperTriangular);
	}

	/**
	 * Prints the reduced row echelon form of the matrix
	 */
	public void printReducedRowEchelonForm() {
		if (reducedRowEchelon == null) {
			calculateReducedRowEchelonForm();
		}

		printMatrix(reducedRowEchelon);
	}

	/**
	 * Calculates the upper triangular matrix via elimination
	 */
	public void calculateUpperAndLowerTriangular() {
		assert matrix.length > 0;

		if (upperTriangular == null) {
			upperTriangular = new double[rows][columns];
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
	 * Calculates the reduced row echelon form of the matrix via
	 * further elimination of the upper triangular matrix
	 */
	public void calculateReducedRowEchelonForm() {
		assert matrix.length > 0;

		if (upperTriangular == null) {
			calculateUpperAndLowerTriangular();
		}

		reducedRowEchelon = new double[rows][columns];

		// Copy the upper triangular matrix
		{
			for (int row = 0; row < rows; row++) {
				reducedRowEchelon[row] = upperTriangular[row].clone();
			}
		}

		calculateReducedRowEchelonMatrix();
	}


	/* =========== Package-private methods =========== */
	/**
	 * Finds and stores the number of pivots of the matrix
	 * after elimination
	 */
	void findNumberOfPivots() {
		assert upperTriangular != null;

		pivots = 0;

		for (int row = 0; row < rows; row++) {
			if ( !(isNullRow(upperTriangular, row)) ) {
				pivots++;
			}
		}
	}

	/**
	 * Fills a single row of a matrix with specified values. If the values are less
	 * than the matrix columns, the rest of the elements will be filled with zeros.
	 *
	 * @param row -> the number of the row to be filled
	 * @param elements -> an ArrayList of integers with which to fill the row of the matrix
	 */
	void fillRow(double [][] m, int row, ArrayList<Double> elements) {
		assert m != null;
		assert row > 0 && row < rows;
		assert elements != null;
		assert m.length != 0;
		assert !elements.isEmpty();

		int elementIndex = 0;

		if (elements.size() == columns) {
			for (int col = 0; col < columns; col++) {
				m[row][col] = elements.get(elementIndex++);
			}
		}
		else {
			for (int col = 0; col < columns; col++) {
				if (elementIndex < columns) {
					m[row][col] = elements.get(elementIndex++);
				} else {
					m[row][col] = 0;
				}
			}
		}
	}

	/**
	 * Reorders the rows of a given matrix based on their length
	 * (counted from the first non-zero element in the row)
	 *
	 * @param m: the matrix to be manipulated
	 */
	void reorderPivotRows(double [][] m) {
		assert m != null;
		assert m.length != 0;

		// The index stands for the number of the row
		// And the element is the position of the first non-zero element
		int firstNonZeroElementsInRow[] = new int[m.length];

		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				if ( m[row][column] != 0 ) {
					firstNonZeroElementsInRow[row] = column;
					break;
				}

				// Zero row
				firstNonZeroElementsInRow[row] = rows;
			}
		}

		// Compare each row with the others and swap if necessary
		for (int row = 0; row < rows; row++) {
			int longestRow = row;
			// Place the first row first and so on

			for (int i = row + 1; i < rows; i++) {
				if (firstNonZeroElementsInRow[i] < firstNonZeroElementsInRow[longestRow])
					longestRow = i;
			}

			if ( row != longestRow ) {
				rowExchangesPerformed++;

				swapTwoRows(m, row, longestRow);

				// Swap the values in the array we're looking
				int temp = firstNonZeroElementsInRow[row];
				firstNonZeroElementsInRow[row] = firstNonZeroElementsInRow[longestRow];
				firstNonZeroElementsInRow[longestRow] = temp;
			}
		}
	}

	/**
	 * Returns the first non zero element of a given array
	 * @param arr from which to be taken the first non zero element
	 * @return the first non zero element of the array
	 */
	double getFirstNonZeroElement(ArrayList<Double> arr) {
		assert arr != null && !arr.isEmpty();

		for (double a : arr) {
			if (a != 0)
				return a;
		}

		return 0;
	}

	/**
	 * Prints any matrix in the format in which the regular matrix is being printed.
	 * Refer to the toString() method.
	 *
	 * @param m: a matrix in a two-dimensional array form which is to be printed
	 */
	private void printMatrix(double [][] m) {
		assert m != null && m.length > 0;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {

				// This checks if the number is a whole number. If so -> print it without the decimal place
				if ( m[row][col] == Math.floor(m[row][col]) ) {
					System.out.printf("%d\t\t", (int) m[row][col]);
				} else {
					System.out.printf("%.1f\t\t", m[row][col]);
				}
			}
			System.out.println();
		}
	}

	/**
	 * Creates and returns a Matrix object of a two-dimensional array.
	 * @param elements two-dimensional array with the elements with which to populate the newly created matrix
	 * @param rows number of rows of the new matrix
	 * @param columns number of columns of the new matrix
	 * @return the newly created matrix
	 */
	Matrix generateMatrix(double [][] elements, int rows, int columns) {
		Matrix newMatrix = MatrixFactory.createMatrix(rows, columns);
		double [] newMatrixElements = new double[rows * columns];
		int index = 0;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				newMatrixElements[index++] = elements[row][col];
			}
		}

		newMatrix.populateMatrix(newMatrixElements);
		return newMatrix;
	}


	/* ======== GETTERS ======== */
	// TODO: This could be used if a matrix operation for finding some row/col of vector/matrix multiplication
	public double getRowProduct(int row) {
		assert row >= 0 && row < rows;

		int product = 1;

		for (int col = 0; col < columns; col++) {
			product *= matrix[row][col];
		}

		return product;
	}
	public double getColumnProduct(int column) {
		assert column >= 0 && column < columns;

		int product = 1;

		for (int row = 0; row < rows; row++) {
			product *= matrix[row][column];
		}

		return product;
	}
	private double getMatrixElementsSum() {
		int sum = 0;

		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				sum += matrix[row][column];
			}
		}

		return sum;
	}

	public int getRank() {
		if (pivots == -1) {
			calculateUpperAndLowerTriangular();
		}

		return pivots;
	}
	public ArrayList<Double> getRowElements(int row) {
		assert row >= 0 && row < rows;

		return getRowElements(matrix, row);
	}
	public ArrayList<Double> getColumnElements(int column) {
		assert column >= 0 && column < columns;

		return getColumnElements(matrix, column);
	}
	ArrayList<Double> getRowElements(double [][] matrix, int row) {
		assert matrix != null && matrix.length > 0;
		assert row >= 0 && row < rows;

		ArrayList<Double> rowElements = new ArrayList<>();

		for (int column = 0; column < columns; column++) {
			rowElements.add( matrix[row][column] );
		}

		return rowElements;
	}
	private ArrayList<Double> getColumnElements(double [][] matrix, int column) {
		assert matrix != null && matrix.length > 0;
		assert column >= 0 && column < columns;

		ArrayList<Double> colElements = new ArrayList<>();

		for (int row = 0; row < rows; row++) {
			colElements.add( matrix[row][column] );
		}

		return colElements;
	}


	/* ======== HELPER METHODS ======== */
	/**
	 * Helper method which does the actual calculations for the
	 * calculateUpperAndLowerTriangular() method
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

		findNumberOfPivots();
	}

	/**
	 * Helper method which does the actual calculations for the
	 * calculateReducedRowEchelonForm() method.
	 */
	private void calculateReducedRowEchelonMatrix() {

		for ( int row = rows - 1; row >= 0; row-- ) {
			int pivotElementColNumber = 0;

			if ( !isNullRow(reducedRowEchelon, row) ) {
				// Find the pivot element and it's column
				while ( reducedRowEchelon[row][pivotElementColNumber] == 0 && pivotElementColNumber < columns ) {
					pivotElementColNumber++;
				}

				// Make all elements above the pivot element of the current row zeros
				for ( int i = row - 1; i >= 0; i-- ) {
					if ( reducedRowEchelon[row][pivotElementColNumber] != 0 ) {
						double multiplierPivotRow = reducedRowEchelon[i][pivotElementColNumber] / reducedRowEchelon[row][pivotElementColNumber];

						// Do the row elimination from the pivot element onwards, as the previous elements of the pivot row are zeros
						for ( int col = pivotElementColNumber; col < columns; col++ ) {
							// Subtract the pivot row element multiplied by the correct multiplier from the current row element
							reducedRowEchelon[i][col] -= ( reducedRowEchelon[row][col] * multiplierPivotRow );
						}

					}
					else {
						// Skip this row as the element is already zero
					}
				}
			}
			else {
				// null row
			}
		}

	}

	/**
	 * This method populates the matrix elements with random values between
	 * the MIN_RANDOM_ELEMENT_VALUE and MAX_RANDOM_ELEMENT_VALUE constants
	 */
	private void populateMatrixRandomly() {
		assert matrix != null;

		Random rand = new Random();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (NEGATIVE_RANDOM_ELEMENTS_ALLOWED) {
					matrix[i][j] = rand.nextInt(MAX_RANDOM_ELEMENT_VALUE - MIN_RANDOM_ELEMENT_VALUE) + MIN_RANDOM_ELEMENT_VALUE;
				} else {
					matrix[i][j] = rand.nextInt(MAX_RANDOM_ELEMENT_VALUE);
				}
			}
		}
	}

	/**
	 * Swap two rows of a matrix (passed as a two-dimensional array)
	 * @param m: the two-dimensional array matrix
	 * @param row1, row2: the rows to be swapped
	 */
	private void swapTwoRows(double [][] m, int row1, int row2) {
		assert m.length > 0 && row1 < m.length && row2 < m.length;

		if ( row1 != row2 ) {
			for (int col = 0; col < m[0].length; col++) {
				double temp = m[row1][col];
				m[row1][col] = m[row2][col];
				m[row2][col] = temp;
			}
		}
	}

	/**
	 * Checks whether a matrix row is null (all zeros)
	 * @param m matrix which row to check
	 * @param row the row number to be checked
	 * @return true if the row consists only zeros
	 */
	private boolean isNullRow(double [][] m, int row) {
		assert m != null && m.length > 0;
		assert row >= 0 && row < rows;

		for (int col = 0; col < m[0].length; col++) {
			if ( m[row][col] != 0 ) {
				return false;
			}
		}

		return true;
	}

}
