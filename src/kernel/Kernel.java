package kernel;

import data.DataPoint;

public abstract class Kernel {
	public abstract double crossProduct(DataPoint x, DataPoint y);
}
