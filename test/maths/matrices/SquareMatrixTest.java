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
		//assertTrue(b.det() == -5);
	}

	@Test
	public void eig() throws Exception {

	}

	@Test
	public void hasInverse() throws Exception {
	}

	@Test
	public void isSymmetric() throws Exception {
	}

	@Test
	public void getLowerTriangular() throws Exception {
	}

	@Test
	public void getUpperTriangular() throws Exception {
	}

}