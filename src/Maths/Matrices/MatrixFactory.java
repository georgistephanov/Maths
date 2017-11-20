package Maths.Matrices;

public class MatrixFactory {

	public static Matrix createMatrix(int rows, int cols) {
		if (rows == cols) {
			return new SquareMatrix(rows);
		} else if ( rows > cols) {
			return new TallMatrix(rows, cols);
		} else {
			return new WideMatrix(rows, cols);
		}
	}

}
