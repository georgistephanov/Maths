package maths.matrices;

public class IdentityMatrix extends SquareMatrix {
	IdentityMatrix(int size) {
		super(size);
		populate();
	}

	private void populate() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				if (row == col) {
					matrix[row][col] = 1;
				} else {
					matrix[row][col] = 0;
				}
			}
		}
	}
}
