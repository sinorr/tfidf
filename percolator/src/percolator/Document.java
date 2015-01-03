package percolator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Document{
	
	// Instance variable 
	
	HashSet<String> enDoc ;
	HashSet<String> chDoc ;
	
	// contractor
	
	public Document(){
		enDoc = new HashSet<String>();
		chDoc = new HashSet<String>();
	}
	
	public Document(Document ori){
		enDoc = new HashSet<String>(ori.enDoc);
		chDoc = new HashSet<String>(ori.chDoc);
	}
	
	// Add methods
	
	public void enAdd(String word){
		enDoc.add(word);
	}
	public void chAdd(String word){
		chDoc.add(word);
	}
	
	// Index methods
	
	public boolean enContains(String word){
		if(enDoc.contains(word))
			return true;
		return false;
	}
	
	public boolean chContains(String word){
		if(chDoc.contains(word)){
			return true;
		}
		return false;
	}
	
	// judge method
	
	public boolean isValue(){
		if(enDoc.size() >= 2 && chDoc.size() >= 2 ){
			return true;
		}
		return false;
	}
	
	// toString methods
	
	public String enToString(){
		Iterator<String> iter = enDoc.iterator();
		String str = iter.next();
		while(iter.hasNext()){
			str += " " + iter.next();
		}
		return str;
	}
	
	public String chToString(){
		Iterator<String> iter = chDoc.iterator();
		String str = iter.next();
		while(iter.hasNext()){
			str += " " + iter.next();
		}
		return str;
	}
	
	//
	
	public String enFilterToString( HashMap<String,Integer> dic, int filter){
		Iterator<String> iter = enDoc.iterator();
		String word = iter.next();
		String line = "";
		while(iter.hasNext()){
			if(dic.get(word) > filter ){
				line += word + " "; 
			}
			word = iter.next();
		}
		return line;
	}
	
	public String chFilterToString( HashMap<String,Integer> dic, int filter){
		Iterator<String> iter = chDoc.iterator();
		String word = iter.next();
		String line = "";
		while(iter.hasNext()){
			if(dic.get(word) > filter ){
				line += word + " "; 
			}
			
			
			word = iter.next();
		}
		return line;
	}
	
	// fresh method
	
	public void fresh(){
		enDoc.clear();
		chDoc.clear();
	}
}
