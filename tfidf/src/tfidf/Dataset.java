package tfidf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Dataset{
	
	// Instance variables
	
	HashMap<String, Integer> dic;
	HashMap<String, Integer> temp;
	Document[] docs ;
	Integer M;
	
	public Dataset(Integer M){
		dic = new HashMap<String, Integer>();
		temp = new HashMap<String, Integer>();
		this.M = M;
		docs = new Document[M];
	}
	
	// I/O methods 
	
	public boolean readFile(String filename){
		try {
			System.out.println("Start reading file ... " );
			BufferedReader reader = new BufferedReader( new InputStreamReader(new FileInputStream(filename)));
			String doc;
			for(int i = 0 ; i < M ; i++ ){
				docs[i] = new Document();
				doc = reader.readLine();
				String[] words = doc.split(" ");
				for( String word: words){
					if(!temp.containsKey(word)){
						temp.put(word, 1);
						
					}else{
						temp.put(word, temp.get(word) + 1);
					}
				}
				
				Iterator<String> iter = temp.keySet().iterator();
				String word;
				
				while(iter.hasNext()){
					word = iter.next();
					if(dic.keySet().contains(word)){
						dic.put(word, dic.get(word) + 1);
					}else{
						dic.put(word, 1);
					}
				}
				
				docs[i].add(temp);
				temp.clear();
			}
			reader.close();
			System.out.println("Read file completely ! " );
			return true;
		}catch(IOException e){
			System.out.println("Read file error ! " + e.getMessage() );
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean savefile(String filename){
		try{
			System.out.println("Start saving file ... " );
			BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(filename)));
			for(int i = 0; i < M; i++ ){
				writer.write("Document : " + (i+ 1) + "\n" );
				Iterator<Map.Entry<String,Integer>> iter = docs[i].freq.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>)iter.next();
					String key = entry.getKey();
					Integer value = entry.getValue();
					Double sc = value * Math.log(M/dic.get(key));
					
					docs[i].score.put(key,sc);
				}
				
				ArrayList<Map.Entry<String,Double> > sorter = new ArrayList<Map.Entry<String,Double> >( docs[i].score.entrySet() );
				
				Collections.sort(sorter,new Comparator<Map.Entry<String,Double> >(){
					
					public int compare(Entry<String, Double> obj1,Entry<String, Double> obj2) {
						return obj2.getValue().compareTo(obj1.getValue());
					}
				});
				
				for(Entry<String, Double> entry : sorter){
					writer.write( "\t" + entry.getKey() + "|||" + entry.getValue() + "\n");
					//System.out.printf("%-15s" + entry.getValue() + "\n", entry.getKey());
					
				}
			}
			writer.close();
			System.out.println("Save file completely ! " );
			return true;
		}catch(IOException e){
			System.out.println("Save file error ! " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
}