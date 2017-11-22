package Maths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Solves equations in the form:
 * QUADRATIC: ax^2 + bx + c
 */
public enum Equations implements Equation {
	/**
	 * Solves quadratic equation in the form: ax^2 + bx + c
	 * @throws IllegalArgumentException if the expression passed as a parameter is not in the correct format
	 */
	QUADRATIC 	(expression -> {
		double [] roots;

		// Removes all whitespace and non-visible characters
		expression = expression.replaceAll("\\s+","");

		// Checks if the expression passed is in the correct format
		Pattern equation 		= Pattern.compile("-?(([0-9]*[.])?[0-9]+)*x\\^2([+-]([0-9]*[.])?[0-9]*)x([+-]([0-9]*[.])?[0-9]*)?");
		Matcher equationMatcher = equation.matcher(expression);

		if (equationMatcher.find()) {
			System.out.println("\n\n" + expression);
		} else {
			// Incorrect format
			throw new IllegalArgumentException("Invalid quadratic equation");
		}


		// Get the correct parts of the equation
		Pattern quadraticPart = Pattern.compile("(-?\\d+)x\\^2");
		Pattern singularPart  = Pattern.compile("([+-]\\d+)x(?!\\^2)");
		Pattern number		  = Pattern.compile("([+-]\\d+)(?!x)");

		Matcher quadraticPartMatcher = quadraticPart.matcher(expression);
		Matcher singularPartMatcher	 = singularPart.matcher(expression);
		Matcher numberMatcher		 = number.matcher(expression);

		// Quadratic equation coefficients
		double a;
		double b;
		double c;

		if (quadraticPartMatcher.find()) {
			a = extractCoefficient(quadraticPartMatcher.group());
		} else {
			a = 1;
		}

		if (singularPartMatcher.find()) {
			b = extractCoefficient(singularPartMatcher.group());
		} else {
			b = 1;
		}

		if (numberMatcher.find()) {
			c = extractCoefficient(numberMatcher.group());
		} else {
			c = 0;
		}

		if (a != 0 && b != 0 && c == 0) {
			// Equation in the form ax^2 + bx = 0 which goes to x(ax + b) = 0
			roots = new double[2];
			roots[0] = 0;
			roots[1] = -b / a;

			return roots;
		}
		if (a != 0 && c != 0 && b == 0) {
			if (c < 0) {
				// Equation in the form ax^2 - c = 0  -->  x^2 = c / a
				roots = new double[2];
				roots[0] = Math.sqrt(-c / a);
				roots[1] = -roots[0];

				return roots;
			} else {
				return new double[0];
			}
		}

		double discriminant;
		discriminant = BasicOperation.POW.apply(b, 2) - (4 * a * c);

		if (discriminant < 0) {
			// No solution
			return new double[0];
		} else if (discriminant == 0) {
			// One solution
			roots = new double[1];
			roots[0] = -b / (2 * a);

			return roots;
		} else {
			// Two solutions
			roots = new double[2];
			roots[0] = (-b + Math.sqrt(discriminant))
					/ (2 * a);
			roots[1] = (-b - Math.sqrt(discriminant))
					/ (2 * a);

			return roots;
		}

	});

	private final Equation equation;

	Equations(Equation equation) {
		this.equation = equation;
	}

	@Override
	public double [] apply(String expression) {
		return equation.apply(expression);
	}

	private static double extractCoefficient(String expression) {
		assert expression != null;

		String coefficient = "";

		if (expression.contains("x^2")) {
			coefficient = expression.substring(0, expression.indexOf("x^2"));
		} else if (expression.contains("x")) {
			coefficient = expression.substring(0, expression.indexOf("x"));
		} else {
			coefficient = expression;
		}

		if (coefficient.length() > 0) {
			return Double.parseDouble(coefficient);
		}

		return 1;
	}
}
