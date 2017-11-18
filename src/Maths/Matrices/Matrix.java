package Maths.Matrices;

import java.util.ArrayList;

public interface Matrix {
	void populateMatrix(int [] elements);
	int getColumns();
	int getRows();
	int at(int row, int col);
	ArrayList<Integer> getRowElements(int row);
	ArrayList<Integer> getColumnElements(int column);
	// boolean hasInverse();

	Matrix add(Matrix a, Matrix b);
	Matrix multiply(Matrix a, Matrix b);
	void calculateUpperTriangular();

	void printUpperTriangular();
	void printReducedRowEchelonForm();
}
