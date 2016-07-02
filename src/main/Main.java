package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import algorithm.Algorithm;
import algorithm.Centroid;
import algorithm.KNN;
import data.DataPoint;
import kernel.GaussianKernel;
import kernel.Kernel;
import kernel.LinearKernel;
import kernel.PolinomialKernel;

public class Main{
	
	public static double error(DataPoint[] prediction, DataPoint[] data){
		double ossz = 0;
		for(int i=0;i<prediction.length;i++){
			if(prediction[i].getCls() != data[i].getCls()) 
				ossz++;
		}
		return ossz/prediction.length;
	}
	
	public static double[] classError(DataPoint[] prediction, DataPoint[] data){
		double ossz[] = new double[10], classNr[] =  new double[10];
		for(int i=0;i<10;i++)
			ossz[i] = 0;
		for(int i=0;i<prediction.length;i++){
			if(prediction[i].getCls() != data[i].getCls()) 
				ossz[data[i].getCls()]++;
			classNr[data[i].getCls()]++;
		}
		for(int i=0;i<10;i++)
			ossz[i] /= classNr[i];
		return ossz;
	}
	
	public static void Gauss_OptimalParameter(DataPoint[] data, Algorithm a){
		double sigma = -0.8;
		double error = 0;
		double minParam = 9999;
		double minError = 9999;
		
		
		for(int i=0;i<10;i++){
			a.setKernel(new GaussianKernel(sigma+i*0.2));
			error = crossValidation5(data, a);
			System.out.println("\tParameter: "+(sigma+i*0.2)+"---error: "+error);
			if(error < minError){
				minError = error;
				minParam = sigma+i*0.2;
			}
		}
		System.out.println("\tOptimalis parameter: "+minParam);
	}
	
	public static void Poly_OptimalParameter(DataPoint[] data, Algorithm a){
		double aa = -0.9, bb = -0.9, cc = -0.9;
		double error = 0;
		double minA = 9999, minB = 9999, minC = 9999;
		double minError = 9999;
		
		
		for(int i=0;i<4;i++){
			for(int j=0;j<3;j++){
				for(int k=0;k<3;k++){
			
					a.setKernel(new PolinomialKernel(aa+i*0.4,bb+j*0.5,cc+k*0.5));
					error = crossValidation5(data, a);
					System.out.println("\tParameter: a:"+(aa+i*0.4)+" b: "+(bb+j*0.5)+" c: "+(cc+k*0.5)+"---error: "+error);
					if(error < minError){
						minError = error;
						minA = aa+i*0.4;
						minB = bb+j*0.5;
						minC = cc+k*0.5;
					}
				}
			}
		}
		System.out.println("\tOptimalis parameter: "+minA+" "+ minB+ " "+minC);
	}
	
	public static double crossValidation5(DataPoint[] data, Algorithm a){
		double error = 0;
		int length = data.length/5;
		DataPoint[] copy = new DataPoint[data.length];
		for(int i=0;i<data.length;i++)
			copy[i] = new DataPoint(data[i]);
		DataPoint[] train = new DataPoint[4*length];
		DataPoint[] test = new DataPoint[length];
		for(int s = 0;s<5;s++){
			for(int i=0;i<s*length;i++){
				train[i] = copy[i];
			}
			for(int i=s*length;i<(s+1)*length;i++){
				test[i-s*length] = copy[i];
			}
			for(int i=(s+1)*length;i<data.length;i++){
				train[i-length] = copy[i];
			}		
			error += a.calculate(train, test);
		}
		return error/5;	
	}
	
	public static void main(String[] args){
		try {
			BufferedReader trainReader = new BufferedReader(new FileReader("src/optdigits.tra"));
			BufferedReader testReader = new BufferedReader(new FileReader("src/optdigits.tes"));
			DataPoint[] train = new DataPoint[3823];
			DataPoint[] test = new DataPoint[1797];
			DataPoint[] all = new DataPoint[5620];
			for(int i = 0;i<train.length;i++){
				train[i] = new DataPoint(trainReader);
				all[i] = new DataPoint(train[i]);
			}
			for(int i = 0;i<test.length;i++){
				test[i] = new DataPoint(testReader);
				all[train.length+i] = new DataPoint(test[i]);
			}
			//----------------------------------------------KNN--------------------------------------------
			Algorithm knn = new KNN(new LinearKernel(),3);
			knn.calculate(train, test);
			System.out.println("KNN Teljes Egyezes: "+(1-error(test,Arrays.copyOfRange(all, train.length, all.length))));
			double[] classError = classError(test,Arrays.copyOfRange(all, train.length, all.length));
			for(int i=0;i<10;i++)
				System.out.println("\t"+i+"-osztaly hiba:" + classError[i]);
			knn.calculate(train, train);
			System.out.println("KNN Tanulasi Hiba:"+error(train,Arrays.copyOfRange(all, 0, train.length)));
			classError = classError(train,Arrays.copyOfRange(all, 0, train.length));
			for(int i=0;i<10;i++)
				System.out.println("\t"+i+"-osztaly tanulasi hiba:" + classError[i]);
			
			//----------------------------------------------Centroid-----------------------------------------
			Algorithm centroid = new Centroid(new PolinomialKernel(1, 0, 2));
			centroid.calculate(train, test);
			System.out.println("Centroid Egyezes: "+(1-error(test,Arrays.copyOfRange(all, train.length, all.length))));
			classError = classError(test,Arrays.copyOfRange(all, train.length, all.length));
			for(int i=0;i<10;i++)
				System.out.println("\t"+i+"-osztaly hiba:" + classError[i]);
			centroid.calculate(train, train);
			System.out.println("Centroid Tanulasi Hiba:"+error(train,Arrays.copyOfRange(all, 0, train.length)));
			classError = classError(train,Arrays.copyOfRange(all, 0, train.length));
			for(int i=0;i<10;i++)
				System.out.println("\t"+i+"-osztaly tanulasi hiba:" + classError[i]);
			//-------------------------------------------Cross Validation-----------------------------------
			//System.out.println("Centroid Gauss:");
			//Gauss_OptimalParameter(all, new Centroid(new GaussianKernel(-1)));
			//System.out.println("KNN3 Gauss:");
			//Gauss_OptimalParameter(all, new Centroid(new GaussianKernel(-1)));
			
			System.out.println("Centroid Polynomial:");
			Poly_OptimalParameter(all, new Centroid(new PolinomialKernel(1,0,1)));
			//System.out.println("KNN3 Polynomial:");
			//Poly_OptimalParameter(all, new Centroid(new PolinomialKernel(-1)));
		} catch (IOException e) {
			System.out.println("File read error!!!");
		}
	}
}
