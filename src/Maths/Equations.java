package Maths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Solves equations in the form:
 * QUADRATIC: ax^2 + bx + c
 */
public enum Equations implements Equation {
	QUADRATIC 	(expression -> {
		double [] x = {2};

		// Removes all whitespace and non-visible characters
		expression = expression.replaceAll("\\s+","");
		System.out.println("\n\n" + expression + "\n");

		// Get the correct parts of the equation
		Pattern quadraticPart = Pattern.compile("(-?\\d+)x\\^2");
		Pattern singularPart  = Pattern.compile("([+-]\\d+)x(?!\\^2)");
		Pattern number		  = Pattern.compile("([+-]\\d+)(?!x)");

		Matcher quadraticPartMatcher = quadraticPart.matcher(expression);
		Matcher singularPartMatcher	 = singularPart.matcher(expression);
		Matcher numberMatcher		 = number.matcher(expression);

		if (quadraticPartMatcher.find()) {
			System.out.println(quadraticPartMatcher.group());
		}
		if (singularPartMatcher.find()) {
			System.out.println(singularPartMatcher.group());
		}
		if (numberMatcher.find()) {
			System.out.println(numberMatcher.group());
		}


		return x;
	});

	private final Equation equation;

	Equations(Equation equation) {
		this.equation = equation;
	}

	@Override
	public double [] apply(String expression) {
		return equation.apply(expression);
	}
}
