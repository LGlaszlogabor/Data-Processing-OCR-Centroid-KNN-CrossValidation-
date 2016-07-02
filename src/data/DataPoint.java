package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataPoint {
	private int[] data;
	private int cls;
	
	public DataPoint(){
		data = new int[64];
		for(int i=0;i<64;i++)
			data[i]=0;
		cls = 0;
	}
	
	public DataPoint(DataPoint p){
		int[] pData = p.getData();
		data = new int[64];
		for(int i=0;i<64;i++)
			data[i] = pData[i];
		cls = p.getCls();
	}
	
	public DataPoint(int[] d, int cls){
		data = new int[64];
		for(int i=0;i<64;i++)
			data[i]= d[i];
		this.cls = cls;
	}
	
	public DataPoint(BufferedReader br) throws IOException{
		String s = br.readLine();
		String[] parts = s.split(",");
		data = new int[64];
		for(int i=0;i<64;i++)
			data[i]= Integer.parseInt(parts[i]);
		this.cls = Integer.parseInt(parts[64]);
	}
	
	public boolean equals(DataPoint p){
		int[] d = p.getData();
		for(int i=0;i<64;i++)
			if(data[i] == d[i]) return false;
		return true;
	}
	
	public int[] getData(){
		return data;
	}
	
	public int get(int i){
		return data[i];
	}
	
	public int getCls(){
		return cls;
	}
	
	public void setClass(int cls){
		this.cls = cls;
	}
	
	public ArrayList<Integer> getDataAsList(){
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(int i=0;i<64;i++)
			l.add(data[i]);
		return l;
	}
}
