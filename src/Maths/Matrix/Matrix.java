package Maths.Matrix;

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

		rrefMatrix = new int[this.rows][this.columns];

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
					if (elementIndex <= elements.length) {
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
	// TODO: Reorder the pivots if necessary
	public void calculateReducedRowEchelonForm() {
		if (matrix.length > 0) {

			// Copy the original matrix
			{
				for (int row = 0; row < rows; row++) {
					rrefMatrix[row] = matrix[row].clone();
				}
			}

			int [] elements = new int[rows * columns];
			int elementIndex = 0;

			for (int column = 0; column < columns; column++) {
				ArrayList<Integer> pivotRow = getRowElements(rrefMatrix, column);

				// Make all zeros under the current element
				for (int row = column + 1; row < rows; row++) {
					if (rrefMatrix[row][column] == 0) {
						System.out.println("Hi");
						continue;
					}

					int multiplierPivotRow = rrefMatrix[row][column];
					int multiplierCurrentRow = rrefMatrix[column][column];

					ArrayList<Integer> currentRow = getRowElements(rrefMatrix, row);
					ArrayList<Integer> newRow = new ArrayList<>();

					for (int i = 0; i < columns; i++) {
						int newRowElement = currentRow.get(i) * multiplierCurrentRow
										  	- pivotRow.get(i) * multiplierPivotRow;
						newRow.add(newRowElement);
					}

					fillRow(rrefMatrix, row, newRow);
				}
			}
		}
	}

	private void calculateUpperTriangular() {
		//upperTriangular
	}

	public void printReducedRowEchelonForm() {
		if (rrefMatrix.length > 0) {

			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < columns; col++) {
					System.out.print(rrefMatrix[row][col] + "\t\t");
				}
				System.out.println();
			}

		}
	}
}
