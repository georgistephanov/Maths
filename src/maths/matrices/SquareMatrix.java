package maths.matrices;

public interface SquareMatrix {
	double det();
	double [] eig();
	boolean hasInverse();
	boolean isSymmetric();
	Matrix getLowerTriangular();
	Matrix getUpperTriangular();
}
