package Maths.Matrices;

class TallMatrix extends AbstractMatrix {

	TallMatrix(int rows, int cols) {
		super(rows, cols, false);
		type = Type.TALL;
	}

	TallMatrix(int rows, int cols, boolean populateRandomly) {
		super(rows, cols, populateRandomly);
		type = Type.TALL;
	}
}
