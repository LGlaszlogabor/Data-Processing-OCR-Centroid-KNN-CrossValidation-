package kernel;

import data.DataPoint;

public class Distance {
	private Distance(){}
	
	public static double d(Kernel k, DataPoint x, DataPoint y){
		return k.crossProduct(x, x)+k.crossProduct(y, y)-2*k.crossProduct(x, y);
	}
}
