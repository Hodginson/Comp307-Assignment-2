package Part3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgap.DefaultFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Add3;
import org.jgap.gp.function.Add4;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Multiply3;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;
import org.jgap.impl.SeededRandomGenerator;

public class Data extends GPProblem {
	private List<Patient> train;
	private List<Patient> test;
	private Variable a, b, c, d, e, f, g, h, i;

	public Data() throws InvalidConfigurationException {
		super(new GPConfiguration());

		this.train = new ArrayList<>();
		this.test = new ArrayList<>();
		try {
			SplitFile.parse("src/part3/breast-cancer-wisconsin.data");
		} catch (IOException e) {
			// error should not occur
			e.printStackTrace();
		}
		this.parse("src/part3/breast-cancer-training.data", "src/part3/breast-cancer-test.data");
		configSetUp();
	}


	public Data(String path) throws InvalidConfigurationException {
		super(new GPConfiguration());

		this.train = new ArrayList<>();
		this.test = new ArrayList<>();
		try {
			SplitFile.parse(path);
		} catch (IOException e) {
			// error should not occur
			e.printStackTrace();
		}
		this.parse("src/part3/breast-cancer-training.data", "src/part3/breast-cancer-test.data");
		configSetUp();
	}

	private void configSetUp() throws InvalidConfigurationException {
		// TODO Auto-generated method stub
		this.fillMissingEntry(train);
		this.fillMissingEntry(test);

		GPConfiguration gpConfig = getGPConfiguration();
		gpConfig.setRandomGenerator(new SeededRandomGenerator(1));
		a = Variable.create(gpConfig, "A", CommandGene.IntegerClass);
		b = Variable.create(gpConfig, "B", CommandGene.IntegerClass);
		c = Variable.create(gpConfig, "C", CommandGene.IntegerClass);
		d = Variable.create(gpConfig, "D", CommandGene.IntegerClass);
		e = Variable.create(gpConfig, "E", CommandGene.IntegerClass);
		f = Variable.create(gpConfig, "F", CommandGene.IntegerClass);
		g = Variable.create(gpConfig, "G", CommandGene.IntegerClass);
		h = Variable.create(gpConfig, "H", CommandGene.IntegerClass);
		i = Variable.create(gpConfig, "I", CommandGene.IntegerClass);
		gpConfig.setFitnessEvaluator(new DefaultFitnessEvaluator());
		gpConfig.setMaxInitDepth(4);
		gpConfig.setPopulationSize(500);
		gpConfig.setMaxCrossoverDepth(8);
		gpConfig.setCrossoverProb(0.60f);
		gpConfig.setMutationProb(0.1f);
		gpConfig.setReproductionProb(0.05f);
		gpConfig.setFitnessFunction(new FitnessFunc(train, a, b, c, d, e,
				f, g, h, i));
		gpConfig.setStrictProgramCreation(true);
	}

	private void parse(String pathToTraining, String pathToTest) {
		readFiles(pathToTraining, train);
		readFiles(pathToTest, test);
	}

	private void readFiles(String path, List<Patient> list) {
		try {
			BufferedReader readFile = new BufferedReader(new FileReader(new File(path)));
			String str;
			while ((str = readFile.readLine()) != null) {
				String[] line = str.split(",");
				int patientID, a, b, c, d, e, f, g, h, i, output;
				patientID = Integer.parseInt(line[0]);
				if (line[1].equals("?")) {
					a = Integer.MIN_VALUE;
				} else {
					a = Integer.parseInt(line[1]);
				}
				if (line[2].equals("?")) {
					b = Integer.MIN_VALUE;
				} else {
					b = Integer.parseInt(line[2]);
				}
				if (line[3].equals("?")) {
					c = Integer.MIN_VALUE;
				} else {
					c = Integer.parseInt(line[3]);
				}
				if (line[4].equals("?")) {
					d = Integer.MIN_VALUE;
				} else {
					d = Integer.parseInt(line[4]);
				}
				if (line[5].equals("?")) {
					e = Integer.MIN_VALUE;
				} else {
					e = Integer.parseInt(line[5]);
				}
				if (line[6].equals("?")) {
					f = Integer.MIN_VALUE;
				} else {
					f = Integer.parseInt(line[6]);
				}
				if (line[7].equals("?")) {
					g = Integer.MIN_VALUE;
				} else {
					g = Integer.parseInt(line[7]);
				}
				if (line[8].equals("?")) {
					h = Integer.MIN_VALUE;
				} else {
					h = Integer.parseInt(line[8]);
				}
				if (line[9].equals("?")) {
					i = Integer.MIN_VALUE;
				} else {
					i = Integer.parseInt(line[9]);
				}
				if (line[10].equals("?")) {
					output = Integer.MIN_VALUE;
				} else {
					output = Integer.parseInt(line[10]);
				}
				list.add(new Patient(patientID, a, b, c, d, e, f, g, h, i, output));
			}
			readFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillMissingEntry(List<Patient> list) {
		for (int col = 0; col < 9; col++) {
			boolean hasMin = false;
			for (Patient p : list) {
				if (checkForMin(p, col))
					hasMin = true;
			}
			if (hasMin)
				fillEntry(list, col);
		}
	}

	private boolean checkForMin(Patient p, int col) {
		// TODO Auto-generated method stub
		return p.checkMin(col);
	}

	private double fillEntry(List<Patient> list, int col) {
		// TODO Auto-generated method stub
		List<Integer> medList = new ArrayList<>();
		for (Patient patient : list) {
			if (patient.getColumnInput(col) != Integer.MIN_VALUE) {
				medList.add(patient.getColumnInput(col));
			}
		}
		double med = 0;
		Collections.sort(medList);
		if (medList.size() % 2 == 0) {
			med = (((double) medList.get(medList.size() / 2)
					+ (double) medList.get(medList.size() / 2 - 1)) / 2);
		} else {
			med = medList.get(medList.size() / 2);
		}
		for (Patient patient : list) {
			if (patient.getColumnInput(col) == Integer.MIN_VALUE) {
				patient.setCol(col, (int) med);
			}
		}
		return med;
	}

	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		GPConfiguration gpConfig = getGPConfiguration();

		Class[] type = { CommandGene.IntegerClass };

		Class[][] args = { {} };

		CommandGene[][] nodeSets = { { a, b, c, d, e, f, g, h, i,
				new Add(gpConfig, CommandGene.IntegerClass),
				new Add3(gpConfig, CommandGene.IntegerClass),
				new Add4(gpConfig, CommandGene.IntegerClass),
				new Multiply(gpConfig, CommandGene.IntegerClass),
				new Multiply3(gpConfig, CommandGene.IntegerClass),
				new Divide(gpConfig, CommandGene.IntegerClass),
				new Subtract(gpConfig, CommandGene.IntegerClass),
				new Terminal(gpConfig, CommandGene.IntegerClass, -10.0, 10.0, true) } };

		GPGenotype result = GPGenotype.randomInitialGenotype(gpConfig, type, args, nodeSets, 20,
				true);

		return result;
	}

	public List<Patient> getTraining() {
		return train;
	}

	public List<Patient> getTest() {
		return test;
	}

	public Variable geta() {
		return a;
	}

	public Variable getb() {
		return b;
	}

	public Variable getc() {
		return c;
	}

	public Variable getd() {
		return d;
	}

	public Variable gete() {
		return e;
	}

	public Variable getf() {
		return f;
	}

	public Variable getg() {
		return g;
	}

	public Variable geth() {
		return h;
	}

	public Variable geti() {
		return i;
	}

}
