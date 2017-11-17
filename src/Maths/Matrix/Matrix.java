package Maths.Matrix;

import Algorithms.Algorithm;

import java.util.ArrayList;
import java.util.Random;

public class Matrix {
	private static int MATRIX_MAX_SIZE = 10;
	private static int MATRIX_DEFAULT_SIZE = 3;
	private static int MAX_ELEMENT_VALUE = 5;
	private static int MIN_ELEMENT_VALUE = -10;
	private static boolean NEGATIVE_ELEMENTS_ALLOWED = false;

	private int rows;
	private int columns;
	private int [][] matrix;

	private int [][] upperTriangular;
	private int [][] rrefMatrix;

	public Matrix(int rows, int cols, boolean populateRandomly) {
		if (rows > 0 && rows <= MATRIX_MAX_SIZE && cols > 0 && cols <= MATRIX_MAX_SIZE) {
			this.rows = rows;
			this.columns = cols;

			matrix = new int[rows][cols];
		} else {
			this.rows = MATRIX_DEFAULT_SIZE;
			this.columns = MATRIX_DEFAULT_SIZE;
		}

		if (populateRandomly) {
			populateMatrixRandomly();
		}
	}

	/**
	 * The default matrix constructor creates a 3x3 matrix
	 */
	public Matrix() {
		this(MATRIX_DEFAULT_SIZE, MATRIX_DEFAULT_SIZE, true);
	}

	/**
	 * This method populates the matrix by an array of given element values.
	 * If the array's length is less than the size of the matrix, the rest
	 * of the elements are automatically filled with zeros.
	 * @param elements -> array of integers with the values in a following
	 *                    order going row by row
	 */
	public void populateMatrix(int elements[]) {
		int elementIndex = 0;

		if (elements.length >= (rows * columns) - 1) {

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					matrix[i][j] = elements[elementIndex++];
				}
			}

		} else if (elements.length > 0) {

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
	 * This method populates the matrix elements with random values between
	 * the MIN_ELEMENT_VALUE and MAX_ELEMENT_VALUE constants
	 */
	private void populateMatrixRandomly() {
		if (matrix != null) {
			Random rand = new Random();

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					if ( NEGATIVE_ELEMENTS_ALLOWED ) {
						matrix[i][j] = rand.nextInt(MAX_ELEMENT_VALUE - MIN_ELEMENT_VALUE) + MIN_ELEMENT_VALUE;
					} else {
						matrix[i][j] = rand.nextInt(MAX_ELEMENT_VALUE);
					}
				}
			}
		}
	}

	/**
	 * Fills a single row of a matrix with specified values. If the values are less
	 * than the matrix columns, the rest of the elements will be filled with zeros.
	 *
	 * @param m -> two-dimensional array representing the matrix to be filled
	 * @param row -> the number of the row to be filled
	 * @param elements -> an ArrayList of integers with which to fill the row of the matrix
	 */
	private void fillRow(int [][] m, int row, ArrayList<Integer> elements) {
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


	/* ======== STATIC METHODS ======== */
	static boolean areSameSize(Matrix a, Matrix b) {
		return ( a.getRows() == b.getRows()
				&& a.getColumns() == b.getColumns());
	}
	static boolean canBeMultiplied(Matrix a, Matrix b) {
		return a.getColumns() == b.getRows();
	}


	// TODO: Implement this together with the hash function
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		StringBuilder m = new StringBuilder();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				m.append(matrix[i][j]).append("\t\t");
			}
			m.append("\n");
		}

		return m.toString();
	}


	/* ======== GETTERS ======== */
	int getRows() { return rows; }
	int getColumns() { return columns; }
	int at(int row, int col) { return matrix[row][col]; }

	int getRowProduct(int row) {
		int product = 1;

		for (int col = 0; col < columns; col++) {
			product *= matrix[row][col];
		}

		return product;
	}
	int getColumnProduct(int column) {
		int product = 1;

		for (int row = 0; row < rows; row++) {
			product *= matrix[row][column];
		}

		return product;
	}
	ArrayList<Integer> getRowElements(int row) {
		ArrayList<Integer> rowElements = new ArrayList<>();

		for (int column = 0; column < columns; column++) {
			rowElements.add( matrix[row][column] );
		}

		return rowElements;
	}
	ArrayList<Integer> getColumnElements(int column) {
		ArrayList<Integer> colElements = new ArrayList<>();

		for (int row = 0; row < rows; row++) {
			colElements.add( matrix[row][column] );
		}

		return colElements;
	}
	ArrayList<Integer> getRowElements(int [][] m, int row) {
		ArrayList<Integer> rowElements = new ArrayList<>();

		for (int column = 0; column < columns; column++) {
			rowElements.add( m[row][column] );
		}

		return rowElements;
	}




	/* ======== CALCULATION METHODS ======== */
	public void calculateMatrices() {
		calculateUpperTriangular();
		//calculateReducedRowEchelonForm();
	}

	private void calculateReducedRowEchelonForm() {
		if (matrix.length > 0 && upperTriangular.length > 0) {
			if (rrefMatrix == null) {
				rrefMatrix = new int[rows][columns];
			}

			// Copy the upper triangular matrix
			{
				for (int row = 0; row < rows; row++) {
					rrefMatrix[row] = upperTriangular[row].clone();
				}
			}

			// Iterate the matrix from the last row upwards
			for (int column = columns - 1; column > 0; column--) {
				ArrayList<Integer> pivotRow = getRowElements(rrefMatrix, column);

				// Make all zeros above the main diagonal
				for (int row = column - 1; row >= 0; row--) {
					if (rrefMatrix[row][column] == 0) {
						continue;
					}

					int multiplierCurrentRow = rrefMatrix[column][column];
					int multiplierPivotRow = rrefMatrix[row][column];

					ArrayList<Integer> currentRow = getRowElements(rrefMatrix, row);
					ArrayList<Integer> newRow = new ArrayList<>();

					for (int i = 0; i < columns; i++) {
						// If the element below is zero don't do any calculations
						if (rrefMatrix[column][i] == 0) {
							newRow.add(currentRow.get(i));
							continue;
						}

						int newRowElement = currentRow.get(i) * multiplierCurrentRow
								- pivotRow.get(i) * multiplierPivotRow;
						newRow.add(newRowElement);
					}

					fillRow(rrefMatrix, row, newRow);

					System.out.println("Row: " + row + "   Col: " + column);
					printMatrix(rrefMatrix);
					System.out.println("\n\n\n");
				}
			}
		}
	}

	// TODO: Think of an elegant way to calculate rectangular matrices too!!!
	private void calculateUpperTriangular() {
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

			// TODO: Call the different methods based on the size of the matrix
			if ( matrix.length >= matrix[0].length) {
				// Square or tall matrix
				calculateTallUpperTriangular(upperTriangular);

			} else {
				// Wide matrix
				calculateTallUpperTriangular(upperTriangular);
			}

		}
	}

	private void calculateSquareUpperTriangular(int [][] upperTriangular) {
		for (int column = 0; column < columns; column++) {
			// Reorders the pivot rows if necessary
			reorderPivotRows(upperTriangular, column);

			// Make all zeros under the current column
			for (int row = column + 1; row < rows; row++) {
				// If the current element is 0 -> find the pivot variable
				if ( upperTriangular[row][column] == 0 ) {
					continue;
				}

				ArrayList<Integer> pivotRow = getRowElements(upperTriangular, column);
				ArrayList<Integer> currentRow = getRowElements(upperTriangular, row);
				ArrayList<Integer> newRow = new ArrayList<>();

				int multiplierPivotRow = upperTriangular[row][column];
				int multiplierCurrentRow = getFirstNonZeroElement(pivotRow);

				// Find the smallest possible multipliers
				int multipliersGCD = Algorithm.GCD(multiplierPivotRow, multiplierCurrentRow);
				if ( multipliersGCD > 1 ) {
					multiplierPivotRow /= multipliersGCD;
					multiplierCurrentRow /= multipliersGCD;
				}

				for (int i = 0; i < columns; i++) {
					int newRowElement = currentRow.get(i) * multiplierCurrentRow
							- pivotRow.get(i) * multiplierPivotRow;

					newRow.add(newRowElement);
				}

				fillRow(upperTriangular, row, newRow);
			}
		}
	}

	private void calculateTallUpperTriangular(int [][] upperTriangular) {
		for (int column = 0; column < columns; column++) {
			// Reorders the pivot rows if necessary
			reorderPivotRows(upperTriangular, column);
			reorderPivotRows(upperTriangular);

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
	}

	/**
	 * Reorders the pivot rows of a given column so that no row,
	 * if possible, starts with zero
	 * @param m: the matrix to be manipulated
	 * @param col: the column number
	 */
	private void reorderPivotRows(int [][] m, int col) {
		for (int row = col; row < rows - 1; row++) {

			// Checks if the leading element is zero
			if ( m[row][col] == 0 ) {
				int nextValidRow = row + 1;

				// Finds the next valid row for a row exchange if such exists
				while (nextValidRow < rows) {
					if ( m[nextValidRow][col] != 0 ) {
						break;
					}
					nextValidRow++;
				}

				// Checks if there is next valid row and exchanges it with the current one
				if (nextValidRow < rows) {
					swapTwoRows(m, row, nextValidRow);
				}
			}
		}
	}
	private void reorderPivotRows(int [][] m) {
		int firstNonZeroElementsInRow[] = new int[m.length];
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				if ( m[row][column] != 0 ) {
					firstNonZeroElementsInRow[row] = column;
					break;
				}
			}
		}

		// Compare each row with the others and swap if necessary
		for (int row = 0; row < rows - 1; row++) {

			int longestRow = Integer.MAX_VALUE;
			// Place the first row first and so on
			for (int i = row + 1; i < rows; i++) {
				if (firstNonZeroElementsInRow[i] < longestRow)
					longestRow = i;
			}

			if ( firstNonZeroElementsInRow[row] != firstNonZeroElementsInRow[longestRow] ) {
				swapTwoRows(m, row, longestRow);
			}
		}
	}
	private void swapTwoRows(int [][] m, int row1, int row2) {
		// Checks all required conditions for the exchange to happen
		if ( m.length > 0 && m.length > row1 && m.length > row2 && row1 != row2 ) {
			for (int col = 0; col < m[0].length; col++) {
				int temp = m[row1][col];
				m[row1][col] = m[row2][col];
				m[row2][col] = temp;
			}
		}
	}




	/* ======== PRINTING METHODS ======== */
	public void printUpperTriangularMatrix() {
		printMatrix(upperTriangular);
	}
	public void printReducedRowEchelonForm() {
		//printMatrix(rrefMatrix);
	}
	private void printMatrix(int [][] m) {
		if (m.length > 0) {

			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < columns; col++) {
					System.out.print(m[row][col] + "\t\t");
				}
				System.out.println();
			}

		}
	}

	/* ======== HELPER METHODS ======== */
	private int getFirstNonZeroElement(ArrayList<Integer> arr) {
		for (int a : arr) {
			if (a != 0)
				return a;
		}

		return 0;
	}
}
