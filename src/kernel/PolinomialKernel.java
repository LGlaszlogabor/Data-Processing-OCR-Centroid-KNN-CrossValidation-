package kernel;

import data.DataPoint;

public class PolinomialKernel extends Kernel{
	private double a, b, c;
	
	public PolinomialKernel(double a, double b, double c){
		this.setA(a);
		this.setB(b);
		this.setC(c);
	}
	
	@Override
	public double crossProduct(DataPoint x, DataPoint y) {
		double ossz = 0;
		int[] xx = x.getData(), yy = y.getData();
		for(int i = 0;i<64;i++)
			ossz += xx[i]*yy[i];
		return Math.pow(a*ossz+b, c);
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}
}
