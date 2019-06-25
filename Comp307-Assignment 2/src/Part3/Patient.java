package Part3;

public class Patient {

	private int patientID, output;
	private int[] input; 

	public Patient(int patientID,int output, int a, int b, int c, int d, int e, int f, int g, int h,
			int i) {
		super();
		this.input = new int[9];
		this.patientID = patientID;
		this.input[0] = a;
		this.input[1] = b;
		this.input[2] = c;
		this.input[3] = d;
		this.input[4] = e;
		this.input[5] = f;
		this.input[6] = g;
		this.input[7] = h;
		this.input[8] = i;
		this.output = output;
	}

	public void setCol(int i, int med) {
		this.input[i] = med;

	}

	public boolean checkMin(int col) {
		if (input[col] == Integer.MIN_VALUE)
			return true;
		return false;
	}

	public int getId() {
		return patientID;
	}

	public int geta() {
		return input[0];
	}

	public int getb() {
		return input[1];
	}

	public int getc() {
		return input[2];
	}

	public int getd() {
		return input[3];
	}

	public int gete() {
		return input[4];
	}

	public int getf() {
		return input[5];
	}

	public int getg() {
		return input[6];
	}

	public int geth() {
		return input[7];
	}

	public int geti() {
		return input[8];
	}

	public int getColumnInput(int col) {
		return this.input[col];
	}

	public int getOutput() {
		return output;
	}

	public void setId(int id) {
		this.patientID = id;
	}

	public void setX1(int x1) {
		this.input[0] = x1;
	}

	public void setX2(int x2) {
		this.input[1] = x2;
	}

	public void setX3(int x3) {
		this.input[2] = x3;
	}

	public void setX4(int x4) {
		this.input[3] = x4;
	}

	public void setX5(int x5) {
		this.input[4] = x5;
	}

	public void setX6(int x6) {
		this.input[5] = x6;
	}

	public void setX7(int x7) {
		this.input[6] = x7;
	}

	public void setX8(int x8) {
		this.input[7] = x8;
	}

	public void setX9(int x9) {
		this.input[8] = x9;
	}

	public void setOutput(int output) {
		this.output = output;
	}

}