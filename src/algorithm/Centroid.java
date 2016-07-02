package algorithm;

import java.util.ArrayList;
import java.util.List;

import data.DataPoint;
import kernel.Kernel;

public class Centroid extends Algorithm{
	private Kernel kernel;
	
	public Centroid(Kernel kernel){
		this.kernel = kernel;
	}
	
	public double calculate(DataPoint[] train, DataPoint[] test){
		List<DataPoint>[] classes = new ArrayList[10];
		for(int i=0;i<10;i++) classes[i] = new ArrayList<DataPoint>();
		for(DataPoint tr: train){
			classes[tr.getCls()].add(tr);
		}
		double[] ci = new double[10], cix = new double[10];
		double kxx, min, ossz;
		int minClass = 0, error = 0;
		for(int i=0;i<10;i++){
			ci[i] = 0;
			for(DataPoint z:classes[i])
				for(DataPoint v:classes[i])
					ci[i]+=kernel.crossProduct(z, v);
			ci[i]/=classes[i].size()*classes[i].size();
		}
		for(DataPoint te:test){
			kxx = kernel.crossProduct(te, te);
			for(int i=0;i<10;i++){
				cix[i] = 0;
				for(DataPoint v:classes[i])
					cix[i]+=kernel.crossProduct(te, v);
				cix[i]/=classes[i].size();
				cix[i]*=2;
			}
			min = 9999999;
			for(int i=0;i<10;i++){
				ossz = ci[i]+kxx-cix[i]; 
				if(ossz < min){
					min = ossz;
					minClass = i;
				}
			}		
			if(te.getCls() != minClass) error++;
			te.setClass(minClass);
			classes[minClass].add(te);
		}
		return ((double)error)/test.length;
 	}
	
	public void setKernel(Kernel k){
		this.kernel = k;
	}
}
