package maths.matrices;

import java.util.ArrayList;

public interface Matrix {
	void populateMatrix(double [] elements);
	int getColumns();
	int getRows();
	double at(int row, int col);
	Matrix transpose();

	int getRank();
	ArrayList<Double> getRowElements(int row);
	ArrayList<Double> getColumnElements(int column);
	// boolean hasInverse();

	Matrix add(Matrix a, Matrix b);
	Matrix multiply(Matrix a, Matrix b);
	void calculateUpperTriangular();

	void printUpperTriangular();
	void printReducedRowEchelonForm();
}
