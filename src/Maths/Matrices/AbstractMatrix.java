package Maths.Matrices;

import java.util.ArrayList;
import java.util.Random;

abstract class AbstractMatrix implements Matrix {
	static int MATRIX_MAX_SIZE = 10;
	static int MATRIX_DEFAULT_SIZE = 3;
	static int MAX_ELEMENT_VALUE = 5;
	static int MIN_ELEMENT_VALUE = -5;
	static boolean NEGATIVE_ELEMENTS_ALLOWED = false;

	int rows;
	int columns;
	int [][] matrix;
	int [][] upperTriangular;
	int [][] reducedRowEchelon;

	AbstractMatrix(int rows, int cols, boolean populateRandomly) {
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


	/* ======== ABSTRACT METHODS ======== */
	public abstract void calculateUpperTriangular();
	public abstract void calculateReducedRowEchelonForm();



	/**
	 * This method populates the matrix by an array of given element values.
	 * If the array's length is less than the size of the matrix, the rest
	 * of the elements are automatically filled with zeros.
	 * @param elements -> array of integers with the values in a following
	 *                    order going row by row
	 */
	public void populateMatrix(int [] elements) {
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
	 * @param row -> the number of the row to be filled
	 * @param elements -> an ArrayList of integers with which to fill the row of the matrix
	 */
	void fillRow(int [][] m,int row, ArrayList<Integer> elements) {
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
	private void reorderPivotRows(int [][] m) {
		// The index stands for the number of the row
		// And the element is the position of the first non-zero element
		int firstNonZeroElementsInRow[] = new int[m.length];

		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				if ( m[row][column] != 0 ) {
					firstNonZeroElementsInRow[row] = column;
					break;
				}
			}

			firstNonZeroElementsInRow[row] = rows;
		}

		// Compare each row with the others and swap if necessary
		for (int row = rows - 1; row > 0; row--) {
			int shortestRow = Integer.MAX_VALUE;
			// Place the first row first and so on

			for (int i = 0; i < rows; i++) {
				if (firstNonZeroElementsInRow[i] < shortestRow)
					shortestRow = i;
			}

			if ( firstNonZeroElementsInRow[row] < firstNonZeroElementsInRow[shortestRow] || firstNonZeroElementsInRow[shortestRow] == rows ) {
				swapTwoRows(m, row, shortestRow);

				// Swap the values in the array we're looking
				int temp = firstNonZeroElementsInRow[row];
				firstNonZeroElementsInRow[row] = firstNonZeroElementsInRow[shortestRow];
				firstNonZeroElementsInRow[shortestRow] = temp;
			}
		}
	}
	private int getFirstNonZeroElement(ArrayList<Integer> arr) {
		for (int a : arr) {
			if (a != 0)
				return a;
		}

		return 0;
	}

	/**
	 *
	 * @param m: the matrix to be manipulated
	 * @param row1, row2: the rows to be swapped
	 */
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

	/**
	 * Prints any matrix in the format in which the regular matrix is being printed.
	 * Refer to the toString() method.
	 *
	 * @param m: the matrix which is to be printed
	 */
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
	public void printUpperTriangularMatrix() {
		if (upperTriangular != null) {
			printMatrix(upperTriangular);
		}
	}
	public void printReducedRowEchelonForm() {
		if (reducedRowEchelon != null) {
			printMatrix(reducedRowEchelon);
		}
	}



	/* ======== STATIC METHODS ======== */
	static boolean areSameSize(AbstractMatrix a, AbstractMatrix b) {
		return ( a.getRows() == b.getRows()
				&& a.getColumns() == b.getColumns());
	}
	static boolean canBeMultiplied(AbstractMatrix a, AbstractMatrix b) {
		return a.getColumns() == b.getRows();
	}



	/* ======== GETTERS ======== */
	private int getRows() { return rows; }
	private int getColumns() { return columns; }
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


}
