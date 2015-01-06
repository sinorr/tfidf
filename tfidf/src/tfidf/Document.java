package tfidf;

import java.util.HashMap;
import java.util.TreeMap;

public class Document{
	
	// Instance variables
	
	TreeMap<String, Integer> freq;
	TreeMap<String, Double> score;
	
	// Contractor
	
	public Document(){
		score = new TreeMap<String, Double>();
	}
	
	public void add(HashMap<String,Integer> freq){
		this.freq = new TreeMap<String,Integer> (freq);
	}
}