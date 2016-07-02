package kernel;

import data.DataPoint;

public class GaussianKernel extends Kernel{
	private double sigma;
	
	public GaussianKernel(double sigma){
		this.setSigma(sigma);
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	@Override
	public double crossProduct(DataPoint x, DataPoint y) {
		double eud = Distance.d(new LinearKernel(),x,y);
		return Math.exp(sigma*eud);
	}
}
