package Part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.impl.TournamentSelector;
import org.jgap.gp.terminal.Variable;

public class aRegression {
	static Float[] xArray = new Float[20], yArray = new Float[20];
	public static Variable vx;

	public static void main(String[] args) throws Exception {
		if(args[0] == null) {
			System.out.println("You need to enter at least one file and no more than two");
			return;
		}
		File file1 = new File("C:\\Users\\rawso\\Desktop\\regression.txt");
		//LoadData(file1);
		LoadData(args[0]);
		GPConfiguration config = new GPConfiguration();
		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		config.setMaxInitDepth(4);
		config.setMaxCrossoverDepth(10);
		config.setPopulationSize(500);
		config.setSelectionMethod( new TournamentSelector(2));

		GenoType gp = new GenoType(config);
		gp.setVariable();
		config.setFitnessFunction(new FitnessFunc(xArray, yArray, gp.getVX(), gp.getVB()));

		GPGenotype gptype = gp.create();
		gptype.setVerboseOutput(true);
		gptype.evolve(1000);
		gptype.outputSolution(gptype.getAllTimeBest());
		gp.showTree(gptype.getAllTimeBest(), "regression.png");

	}

	private static void LoadData(/*File file1*/String fileName) throws FileNotFoundException {
		
		File file = new File(fileName);
		int count = 0;
		Scanner input = new Scanner(file);
		input.nextLine();
		input.nextLine();

		while (input.hasNext()) {
			Float x = input.nextFloat();
			Float y = input.nextFloat();
			xArray[count] = x;
			yArray[count] = y;
			System.out.println(xArray[count] + " " + yArray[count]);
			count++;
		}
		input.close();
	}
}
