package maths.matrices;

import org.junit.Test;

import static org.junit.Assert.*;

public class SquareMatrixTest {
	ConcreteSquareMatrix a = MatrixFactory.createSquareMatrix(4);

	double [] symmetric = {
			1, 1, 1, 1,
			1, 2, 3, 4,
			1, 3, 6, 10,
			1, 4, 10, 20
	};

	ConcreteSquareMatrix b = MatrixFactory.createSquareMatrix(3);

	double elem[] = {
			1, 2, 3,
			0, 3, 4,
			1, 4, 4
	};

	{
		a.populateMatrix(symmetric);
		b.populateMatrix(elem);
	}

	@Test
	public void det() throws Exception {
		assertTrue(a.det() == 1);
		assertTrue(b.det() == -5);
	}

	@Test
	public void eig() throws Exception {
		ConcreteSquareMatrix c = MatrixFactory.createSquareMatrix(2);
		c.populateMatrix(new double[] {1, 2, 2, 4});

		assertTrue(c.eig()[0] == 0);
		assertTrue(c.eig()[1] == 5);
	}

	@Test
	public void hasInverse() throws Exception {
		ConcreteSquareMatrix c = MatrixFactory.createSquareMatrix(2);
		c.populateMatrix(new double[] {1, 2, 2, 4});

		assertTrue(a.hasInverse());
		assertTrue(b.hasInverse());
		assertFalse(c.hasInverse());
	}

	@Test
	public void isSymmetric() throws Exception {
		assertTrue(a.isSymmetric());
		assertFalse(b.isSymmetric());
	}

	@Test
	public void getLowerTriangular() throws Exception {
	}

	@Test
	public void getUpperTriangular() throws Exception {
	}

}