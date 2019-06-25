package Part2;

import org.jgap.gp.*;
import org.jgap.gp.terminal.*;

public class FitnessFunc extends GPFitnessFunction {
/**
	 * 
	 */
	private static final long serialVersionUID = 6377235159248676480L;
/**
	 * 
	 */

Float[] x, y;
Variable vx, bx;

public FitnessFunc(Float[] x, Float[] y, Variable vx, Variable bx) {
	// TODO Auto-generated constructor stub
	this.x = x;
	this.y = y;
	this.vx = vx;

}

@Override
protected double evaluate(IGPProgram arg0) {
	// TODO Auto-generated method stub
	return computeRawFitness(arg0);
}

private double computeRawFitness(IGPProgram arg0) {

	double error = 0.0f;
	Object[] noArgs = new Object[0];
		for (int i = 0; i < 20; i++) {
		vx.set(x[i]);
		
		try {
				double result = arg0.execute_float(0, noArgs);
				error += Math.abs(result - y[i]);
				if (Double.isInfinite(error)) {
			return Double.MAX_VALUE;
			}
		}
		catch (ArithmeticException e) {
			// This should not happen, some illegal operation was executed.
			// ------------------------------------------------------------
			System.out.println("x = " + x[i].floatValue());
			System.out.println(arg0);
			throw e;
		}
	}
		if (error < 0.001) {
			error = 0.0d;
		}
		return error;
}

}
