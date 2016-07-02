package algorithm;

import data.DataPoint;
import kernel.Kernel;

public abstract class Algorithm {
	public abstract double calculate(DataPoint[] train, DataPoint[] test);
	public abstract void setKernel(Kernel k);
}
