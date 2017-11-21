package Maths.Matrices;

public class MatrixFactory {

	/**
	 * Creates a matrix of the correct size based on the row and column values
	 * @param rows of the matrix
	 * @param cols of the matrix
	 * @return the created matrix of the given size
	 * @throws IllegalArgumentException if the rows or the cols are less than 1
	 */
	public static Matrix createMatrix(int rows, int cols) {
		if (rows < 1 || cols < 1) {
			throw new IllegalArgumentException("Illegal value for the rows and/or columns.");
		}

		if (rows == cols) {
			return new SquareMatrix(rows);
		} else if ( rows > cols) {
			return new TallMatrix(rows, cols);
		} else {
			return new WideMatrix(rows, cols);
		}
	}

	/**
	 * Creates and returns a square matrix
	 * @param size of the matrix
	 * @return the newly created square matrix
	 * @throws IllegalArgumentException if the size is less than 1
	 */
	public static SquareMatrix createSquareMatrix(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Illegal size of a matrix");
		}

		return new SquareMatrix(size);
	}

}
