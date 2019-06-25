package Part2;


import org.jgap.*;
import org.jgap.gp.*;
import org.jgap.gp.function.*;
import org.jgap.gp.impl.*;
import org.jgap.gp.terminal.*;

public class GenoType extends GPProblem {

	public Variable vx;
	private Variable bx;

	public GenoType(GPConfiguration gpConfig) throws InvalidConfigurationException {
		super(gpConfig);
	}

	public void setVariable() throws InvalidConfigurationException {
		vx = Variable.create(getGPConfiguration(), "X", CommandGene.FloatClass);
		if (vx == null) {
			System.out.println("Variable X is Null");

		}

	}
	public Variable getVX() {

		return vx;
	}

	public Variable getVB() {

		return bx;
	}
	@SuppressWarnings("rawtypes")
	public GPGenotype create() throws InvalidConfigurationException {

		GPConfiguration gpConfig = getGPConfiguration();

		
		Class[] types = { CommandGene.FloatClass };
		Class[][] args = { {} };

		// TODO Auto-generated method stub

		CommandGene[][] nodeSets = { { vx,
			    new Multiply(gpConfig, CommandGene.FloatClass),
		        new Divide(gpConfig, CommandGene.FloatClass),
		        new Subtract(gpConfig, CommandGene.FloatClass),
		        new Add(gpConfig, CommandGene.FloatClass),
		        new Pow(gpConfig, CommandGene.DoubleClass),
				new Terminal(gpConfig, CommandGene.FloatClass, 2.0d, 10.0d, true),
				} };
		return GPGenotype.randomInitialGenotype(gpConfig, types, args, nodeSets, 20, true);
	}
}
