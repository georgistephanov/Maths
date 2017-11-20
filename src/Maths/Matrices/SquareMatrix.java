package Maths.Matrices;

class SquareMatrix extends AbstractMatrix {

	SquareMatrix(int size) {
		super(size, size, false);
		type = Type.SQUARE;
	}

	SquareMatrix(int size, boolean populateRandomly) {
		super(size, size, populateRandomly);
		type = Type.SQUARE;
	}

}
