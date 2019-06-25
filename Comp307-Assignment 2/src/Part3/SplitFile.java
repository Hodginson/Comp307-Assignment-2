package Part3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplitFile {
	private final static int TRAINING_PERCENTAGE = 75;


	public static void main(String[] args) {
		if (args.length == 1)
			try {
				parse(args[0]);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else {
			try {
				parse("src/part3/breast-cancer-wisconsin.data");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void parse(String path) throws IOException {
		BufferedReader readFile = new BufferedReader(new FileReader(new File(path)));
		List<String> dataset = new ArrayList<>();
		String line;
		while ((line = readFile.readLine()) != null) {
			dataset.add(line);
		}
		int trainingSize = 0;
		double d = (double) TRAINING_PERCENTAGE * (double) dataset.size() / (double) 100;
		if (d % 1 > 0) {
			trainingSize = (int) d + 1;
		} else {
			trainingSize = (int) d;
		}
		BufferedWriter trainingSet = null;
		BufferedWriter testSet = null;
		// check if dataset exists
		if (!new File("src/part3/breast-cancer-training.data").isFile()||!new File("src/part3/breast-cancer-test.data").isFile()) {
			trainingSet = new BufferedWriter(new FileWriter(new File("src/part3/breast-cancer-training.data")));
			testSet = new BufferedWriter(new FileWriter(new File("src/part3/breast-cancer-test.data")));
		} 
		if (testSet != null && trainingSet != null) {
			for (int i = 0; i < dataset.size(); i++) {
				if (i <= trainingSize) {
					if (trainingSet != null) {
						trainingSet.write(dataset.get(i) + "\n");
					}
				} else {
					if (testSet != null) {
						testSet.write(dataset.get(i) + "\n");
					}
				}
			}
			if (trainingSet != null)
				trainingSet.close();
			if (testSet != null)
				testSet.close();
		}
		readFile.close();
	}
}
