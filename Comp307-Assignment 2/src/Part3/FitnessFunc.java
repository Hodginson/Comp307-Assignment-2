package Part3;

import java.util.List;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class FitnessFunc extends GPFitnessFunction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7406325141155750066L;
	private List<Patient> training;
	private Variable a, b, c, d,  e,  f,  g, h, i;

	private static Object[] noArgs = new Object[0];

	public FitnessFunc(List<Patient> train, Variable a, Variable b,
			Variable c, Variable d, Variable e, Variable f, Variable g,
			Variable h, Variable i) {
		this.training = train;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
	}


	@Override
	protected double evaluate(IGPProgram program) {
		// TODO Auto-generated method stub
		int x = 0;
		for (int y = 0; y < training.size(); y++) {
			// Set the input values
			Patient p = training.get(y);
			a.set(p.geta());
			b.set(p.getb());
			c.set(p.getc());
			d.set(p.getd());
			e.set(p.gete());
			f.set(p.getf());
			g.set(p.getg());
			h.set(p.geth());
			i.set(p.geti());
			long value = program.execute_int(0, noArgs);
			// accuracy calculation
			if (value >= 0) {
				if (p.getOutput() == 2) {
					x++;
				}
			} else {
				if (p.getOutput() == 4) {
					x++;
				}
			}
		}
		double out = ((double) x / (double) training.size()) * 100;
		return out;
	}

}