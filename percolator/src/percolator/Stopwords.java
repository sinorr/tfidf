package percolator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.HashSet;

public class Stopwords{
	
	// Instance variables 
	
	Options option;
	HashSet<String> enStops;
	HashSet<String> chStops;
	
	// Construct 
	
	public Stopwords(Options option){
		if(option.ridstops || option.filter > 0){
			this.option = option;
			enStops = new HashSet<String>();
			chStops = new HashSet<String>();
		}
	}
	
	// Index method
	
	public boolean conEn(String stop){
		if(enStops.contains(stop)){
			return true;
		}
		return false;
	}
	
	public boolean conCh(String stop){
		if(chStops.contains(stop)){
			return true;
		}
		return false;
	}
	
	// Input method
	
	public boolean readStops(){
		try{
			System.out.println("Starting read stop words ...");
			String dir = option.dir;
			if(!dir.endsWith(File.separator)){
				dir = dir + File.separator;
			}
			dir += option.dstops;
			
			
			BufferedReader enReader = new BufferedReader(new InputStreamReader(new FileInputStream(dir+".en")));
			String line;
			while(true){
				line = enReader.readLine();
				if(line == null )
					break;
				if(!enStops.contains(line)){
					enStops.add(line);
				}
			}
			enReader.close();
			
			BufferedReader chReader = new BufferedReader(new InputStreamReader(new FileInputStream(dir+".ch")));
			while(true){
				line = chReader.readLine();
				if(line == null )
					break;
				if(!chStops.contains(line)){
					chStops.add(line);
				}
			}
			chReader.close();
			System.out.println("Read stop words fininshed !" );
			return true;
			
		}catch(IOException e){
			System.out.println("Read stopwords error ! " + e.getMessage() );
			e.printStackTrace();
			return false;
		}
	}
}