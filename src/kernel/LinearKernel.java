package kernel;

import data.DataPoint;

public class LinearKernel extends Kernel{
	public LinearKernel(){}
	
	@Override
	public double crossProduct(DataPoint x, DataPoint y){
		double ossz = 0;
		int[] xx = x.getData(), yy = y.getData();
		for(int i = 0;i<64;i++)
			ossz += xx[i]*yy[i];
		return ossz;
	}
}
