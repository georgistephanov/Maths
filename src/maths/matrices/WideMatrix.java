package maths.matrices;

class WideMatrix extends AbstractMatrix {

	WideMatrix(int rows, int cols) {
		super(rows, cols, false);
		type = Type.WIDE;
	}

	WideMatrix(int rows, int cols, boolean populateRandomly) {
		super (rows, cols, populateRandomly);
		type = Type.WIDE;
	}

}
