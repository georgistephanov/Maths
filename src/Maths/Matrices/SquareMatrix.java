package Maths.Matrices;

public class SquareMatrix extends AbstractMatrix {

	SquareMatrix(int row, int col) {
		super(row, col, false);
	}

	SquareMatrix(int row, int col, boolean populateRandomly) {
		super(row, col, populateRandomly);
	}

	public void calculateUpperTriangular() {
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

			calculateUpperTriangular(upperTriangular);
		}
	}
}
