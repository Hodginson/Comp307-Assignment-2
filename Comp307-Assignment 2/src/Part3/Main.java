package Part3;

import java.util.List;

import org.apache.log4j.Logger;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.util.SystemKit;

public class Main {
	private static Logger LOGGER = Logger.getLogger(Main.class);

	private final static double FITNESS_VALUE = 100;

	
	public static void main(String[] args) {
		try {
			Data dat;
			if (args.length == 1) {
				dat = new Data(args[0]);
			} else {
				System.out.println("You need to enter at least one file and no more than two");
				return;
			}
			GPGenotype gp = dat.create();
			gp.setVerboseOutput(true);
			evolve(1000, gp);
			System.out.println("------------------------------------------------------------------------------------");
			gp.outputSolution(gp.getAllTimeBest());
			System.out.println("------------------------------------------------------------------------------------");
			LOGGER.info("Test set accuracy = "
					+ test(dat, dat.getTest(), gp.getAllTimeBest()) + "%");
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static double test(Data dat, List<Patient> test, IGPProgram program) {
		int x = 0;
		for (int i = 0; i < test.size(); i++) {
			// Set the input values
			Patient patient = test.get(i);
			dat.geta().set(patient.geta());
			dat.getb().set(patient.getb());
			dat.getc().set(patient.getc());
			dat.getd().set(patient.getd());
			dat.gete().set(patient.gete());
			dat.getf().set(patient.getf());
			dat.getg().set(patient.getg());
			dat.geth().set(patient.geth());
			dat.geti().set(patient.geti());
			long value = program.execute_int(0, new Object[0]);
			if (value >= 0) {
				if (patient.getOutput() == 2) {
					x++;
				}
			} else {
				if (patient.getOutput() == 4) {
					x++;
				}
			}
		}
		double output = ((double) x / (double) test.size()) * 100;
		return output;
	}

	public static void evolve(int evo, GPGenotype gp) {
		int offset = gp.getGPConfiguration().getGenerationNr();
		int evolutions;
		if (evo < 0) {
			evolutions = Integer.MAX_VALUE;
		} else {
			evolutions = evo;
		}
		for (int i = 0; i < evolutions; i++) {

			// Stopping Criteria
			if (i >= 1) {
				if (gp.getAllTimeBest().getFitnessValue() == FITNESS_VALUE) {
					LOGGER.info("Best Solution Found");
					return;
				}
			}
			if (i % 25 == 0) {
				String freeMB = SystemKit.niceMemory(SystemKit.getFreeMemoryMB());
				LOGGER.info(
						"Evolving generation " + (i + offset) + ", memory free: " + freeMB + " MB");
			}
			gp.evolve();
			gp.calcFitness();
		}
	}
}