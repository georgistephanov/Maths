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

	boolean isOfSameSize(Matrix a);
	boolean canBeMultiplied(Matrix a);
	Matrix add(Matrix a);
	Matrix subtract(Matrix a);
	Matrix multiply(Matrix a);

	void calculateUpperAndLowerTriangular();
	void calculateReducedRowEchelonForm();

	void printUpperTriangular();
	void printReducedRowEchelonForm();
}
