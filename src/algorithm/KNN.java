package algorithm;

import java.util.ArrayList;

import data.DataPoint;
import kernel.Distance;
import kernel.Kernel;

public class KNN extends Algorithm{
	private Kernel kernel;
	private int k;
	
	public KNN(Kernel kernel, int k){
		this.kernel = kernel;
		this.k = k;
	}
	
	public double calculate(DataPoint[] train, DataPoint[] test){
		ArrayList<DataPoint> decided = new ArrayList<>();
		for(int i=0 ;i<train.length;i++){
			decided.add(train[i]);
		}	
		double minD, d;
		ArrayList<DataPoint> kNearest = new ArrayList<>();
		DataPoint minP = null;
		int[] classes = new int[10];
		int maxClass = 0, maxNR = 0, error = 0;
		for(int i = 0; i < 10; i++)
			classes[i] = 0;
		for(DataPoint te : test){
			for(int i = 0;i<k;i++){
				minD = 9999999;
				for(DataPoint tr : decided){
					d = Distance.d(kernel, te, tr);
					if(d < minD && !kNearest.contains(tr)){
						minD = d;
						minP = tr;
					}
				}
				if(minP != null) kNearest.add(minP);
			}			
			for(DataPoint p:kNearest){
				classes[p.getCls()]++;
			}
			for(int i = 0; i<10; i++){
				if(classes[i]> maxNR){
					maxNR = classes[i];
					maxClass = i;
				}
			}
			if(te.getCls() != maxClass){
				error++;
			}
			te.setClass(maxClass);
			decided.add(te);
			maxNR = 0;
			for(int i = 0; i < 10; i++)
				classes[i] = 0;
			kNearest.clear();
			
		}		
		return ((double)error)/test.length;
	}
	
	public void setKernel(Kernel k){
		this.kernel = k;
	}
}
