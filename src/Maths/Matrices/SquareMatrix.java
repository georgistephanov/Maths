package Maths.Matrices;

public class SquareMatrix extends AbstractMatrix {

	private double determinant;
	private boolean determinantCalculated = false;
	private double [] eigenvalues;


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

		}

		return eigenvalues;
	}

}
