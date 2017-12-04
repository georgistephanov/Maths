package maths.matrices;

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
			return new ConcreteSquareMatrix(rows);
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
	public static ConcreteSquareMatrix createSquareMatrix(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Illegal size of a matrix");
		}

		return new ConcreteSquareMatrix(size);
	}

	/**
	 * Creates and returns an identity matrix
	 * @param size of the matrix
	 * @return the newly created identity matrix
	 * @throws IllegalArgumentException if the size is less than 1
			*/
	public static IdentityMatrix createIdentityMatrix(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Illegal size of a matrix");
		}

		return new IdentityMatrix(size);
	}

}
