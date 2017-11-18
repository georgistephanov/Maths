package Maths.Matrices;

public class MatrixFactory {

	public static Matrix createMatrix(int rows, int cols) {
		if (rows == cols) {
			return new SquareMatrix(rows);
		}

		return null;
	}

}
