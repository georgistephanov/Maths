package Maths.Matrix;

import java.util.ArrayList;
import java.util.Random;

public class Matrix {
	private static int MATRIX_MAX_SIZE = 10;
	private static int MATRIX_DEFAULT_SIZE = 3;
	private static int MAX_ELEMENT_VALUE = 4;
	private static int MIN_ELEMENT_VALUE = -10;
	private static boolean NEGATIVE_ELEMENTS_ALLOWED = false;

	private int rows;
	private int cols;
	private int [][] matrix;

	public Matrix(int rows, int cols, boolean populateRandomly) {
		if (rows > 0 && rows <= MATRIX_MAX_SIZE && cols > 0 && cols <= MATRIX_MAX_SIZE) {
			this.rows = rows;
			this.cols = cols;

			matrix = new int[rows][cols];
		} else {
			this.rows = MATRIX_DEFAULT_SIZE;
			this.cols = MATRIX_DEFAULT_SIZE;
		}

		if (populateRandomly) {
			populateMatrix();
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
	void populateMatrix(int elements[]) {
		int elementIndex = 0;

		if (elements.length >= (rows * cols) - 1) {

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					matrix[i][j] = elements[elementIndex++];
				}
			}

		} else if (elements.length > 0) {

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
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
	private void populateMatrix() {
		if (matrix != null) {
			Random rand = new Random();

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if ( NEGATIVE_ELEMENTS_ALLOWED ) {
						matrix[i][j] = rand.nextInt(MAX_ELEMENT_VALUE - MIN_ELEMENT_VALUE) + MIN_ELEMENT_VALUE;
					} else {
						matrix[i][j] = rand.nextInt(MAX_ELEMENT_VALUE);
					}
				}
			}
		}
	}


	/* ======== STATIC METHODS ======== */
	static boolean areSameSize(Matrix a, Matrix b) {
		return ( a.getRows() == b.getRows()
				&& a.getCols() == b.getCols());
	}
	static boolean canBeMultiplied(Matrix a, Matrix b) {
		return a.getCols() == b.getRows();
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
			for (int j = 0; j < cols; j++) {
				m.append(matrix[i][j]).append("\t\t");
			}
			m.append("\n");
		}

		return m.toString();
	}


	/* ======== GETTERS ======== */
	int getRows() { return rows; }
	int getCols() { return cols; }
	int at(int row, int col) { return matrix[row][col]; }

	int getRowProduct(int row) {
		int product = 1;

		for (int col = 0; col < cols; col++) {
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

		for (int column = 0; column < cols; column++) {
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
