package maths.matrices;

import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractMatrixTest {
	Matrix a = MatrixFactory.createMatrix(3, 4);
	Matrix b = MatrixFactory.createMatrix(4, 3);

	private double [] elements = {
			1, 2, 3, 4,
			5, 6, 7, 8,
			9, 10, 11, 12
	};

	@Test
	public void equals() throws Exception {
		a.populateMatrix(elements);

		Matrix c = MatrixFactory.createMatrix(3, 4);
		c.populateMatrix(elements);

		assertTrue(a.equals(c));
	}

	@Test
	public void transpose() throws Exception {
		a.populateMatrix(elements);

		Matrix c = MatrixFactory.createMatrix(4, 3);
		double [] elem = {
				1, 5, 9,
				2, 6, 10,
				3, 7, 11,
				4, 8, 12
		};
		c.populateMatrix(elem);

		assertTrue(a.transpose().equals(c));
	}

	@Test
	public void at() throws Exception {
		a.populateMatrix(elements);
		assertTrue(a.at(0, 2) == 3);
		assertTrue(a.at(2, 1) == 10);
		assertTrue(a.at(0, 3) == 4);
	}

	@Test
	public void isOfSameSize() throws Exception {
		Matrix c = MatrixFactory.createMatrix(3, 4);

		assertFalse(a.isOfSameSize(b));
		assertTrue(a.isOfSameSize(c));
	}

	@Test
	public void canBeMultiplied() throws Exception {
		Matrix c = MatrixFactory.createMatrix(3, 4);

		assertTrue(a.canBeMultiplied(b));
		assertFalse(a.canBeMultiplied(c));
	}

	@Test
	public void add() throws Exception {
		a.populateMatrix(elements);

		Matrix c = MatrixFactory.createMatrix(3, 4);
		double [] elem = {
				1, 2, 3, 5,
				6, 7, 8, 11,
				0, 4, 5, -3
		};
		c.populateMatrix(elem);

		Matrix answer = MatrixFactory.createMatrix(3, 4);
		double [] answ = {
				2, 4, 6, 9,
				11, 13, 15, 19,
				9, 14, 16, 9
		};
		answer.populateMatrix(answ);

		assertTrue(a.add(c).equals(answer));
	}

	@Test
	public void subtract() throws Exception {
		a.populateMatrix(elements);

		Matrix c = MatrixFactory.createMatrix(3, 4);
		double [] elem = {
				1, 2, 3, 5,
				6, 7, 8, 11,
				0, 4, 5, -3
		};
		c.populateMatrix(elem);

		Matrix answer = MatrixFactory.createMatrix(3, 4);
		double [] answ = {
				0, 0, 0, -1,
				-1, -1, -1, -3,
				9, 6, 6, 15
		};
		answer.populateMatrix(answ);

		assertTrue(a.subtract(c).equals(answer));
	}

	@Test
	public void multiply() throws Exception {
		a.populateMatrix(elements);

		Matrix c = MatrixFactory.createMatrix(4, 3);
		double [] elem = {
				 1,  2,  1,
				 3, -2, -1,
				 0,  0,  3,
				-1,  1,  1
		};
		c.populateMatrix(elem);

		Matrix answer = MatrixFactory.createMatrix(3, 3);
		double [] answ = {
				3,  2,  12,
				15, 6,  28,
				27, 10, 44
		};
		answer.populateMatrix(answ);

		assertTrue(a.multiply(c).equals(answer));
	}

	@Test
	public void getRank() throws Exception {
		a.populateMatrix(elements);
		b.populateMatrix(elements);

		assertTrue(a.getRank() == 2);
		assertTrue(b.getRank() == 2);
	}

}