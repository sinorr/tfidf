package percolator;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.HashMap;

public class Dataset {

	// instance variable

	int counter;
	int ndocs;
	Options option;
	Document temp;
	Stopwords stops;
	HashMap<String, Integer> enDic;
	HashMap<String, Integer> chDic;
	Document[] docs ;

	// contractor

	public Dataset(Options option) {
		this.option = option;
		counter = 0;
		ndocs = option.ndocs;
		if(option.ridstops || option.filter > 0 ){
			temp = new Document();
			stops = new Stopwords(option);
			
			if(option.filter > 0){
				docs = new Document[ndocs];
				enDic = new HashMap<String,Integer>();
				chDic = new HashMap<String,Integer>();
			}
		}
	}

	

	// Transport methods

	public boolean tranPlain() {
		try {
			System.out.println("Start plaining ...");
			String dir = option.dir;
			if (!dir.endsWith(File.separator)) {
				dir += File.separator;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(dir + option.dfile)));
			BufferedWriter enWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(dir
							+ "plain.en")));
			BufferedWriter chWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(dir
							+ "plain.ch")));


			int counter = 0;
			int flag = 0;
			String line = "";
			String endoc = "";
			String chdoc = "";
			while (true) {
				line = reader.readLine();
				if (line == null)
					break;
				if (line.startsWith("<doc")) {
					flag = 1;
					continue;
				} else if (flag == 1 && line.startsWith("</doc")) {

					enWriter.write(endoc + "\n");
					chWriter.write(chdoc + "\n");

					endoc = "";
					chdoc = "";
					flag = 0;
					counter++;
					if (counter >= option.ndocs) {
						break;
					}
					continue;
				}

				if (flag == 1) {
					if (line.startsWith("<src>")) {
						chdoc += line.substring(6, line.length() - 6);
					} else if (line.startsWith("<trg>")) {
						endoc += line.substring(6, line.length() - 6);
					}
				}
			}
			
			this.counter = counter ;
			System.out.println("Plain doc finshed ! \nThe num of documents is "
					+ counter);
			reader.close();
			enWriter.close();
			chWriter.close();
			return true;
		} catch (IOException e) {
			System.out.println("Read or write files error while plaining ! "
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean tranRidStops() {
		try {
			System.out.println("Start geting rid of stop words ...");
			String dir = option.dir;
			if (!dir.endsWith(File.separator)) {
				dir += File.separator;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(dir + option.dfile)));
			BufferedWriter enWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(dir
							+ "plain.en")));
			BufferedWriter chWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(dir
							+ "plain.ch")));
			stops.readStops();
			int counter = 0;
			int flag = 0;
			String line = "";
			String endoc = "";
			String chdoc = "";
			while (true) {
				line = reader.readLine();
				if (line == null)
					break;
				if (line.startsWith("<doc")) {
					flag = 1;
					continue;
				} else if (flag == 1 && line.startsWith("</doc")) {
					
					HashSet<String> enTemp = new HashSet<String>();
					HashSet<String> chTemp = new HashSet<String>();
					String[] enWords = endoc.split(" ");
					String[] chWords = chdoc.split(" ");
					int length = enWords.length;
					for(int i = 0; i < length; i ++ ){
						if(!enTemp.contains(enWords[i])){
							if(!stops.conEn(enWords[i])){
								temp.enAdd(enWords[i]);
							}
						}
					}
					
					length = chWords.length;
					for(int i = 0; i < length; i ++ ){
						if(!chTemp.contains(chWords[i])){
							if(!stops.conCh(chWords[i])){
								temp.chAdd(chWords[i]);
							}
						}
					}
					
					if(temp.isValue()){
						enWriter.write(temp.enToString() + "\n");
						chWriter.write(temp.chToString() + "\n");
						counter++;
					}
					temp.fresh();
					
					endoc = "";
					chdoc = "";
					flag = 0;
					if (counter >= option.ndocs) {
						break;
					}
					continue;
				}

				if (flag == 1) {
					if (line.startsWith("<src>")) {
						chdoc += line.substring(6, line.length() - 6);
					} else if (line.startsWith("<trg>")) {
						endoc += line.substring(6, line.length() - 6);
					}
				}
			}
			
			this.counter = counter ;
			System.out.println("Get rid of stops doc finshed ! \nThe num of documents is "
					+ counter);
			reader.close();
			enWriter.close();
			chWriter.close();
			return true;
		} catch (IOException e) {
			System.out.println("Read or write files error while geting rid of stop words ! "
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	public boolean tranFilter() {
		System.out.println("Start filtering ... " );
		stops.readStops();
		readFiles();
		saveFiles();
		return true;
	}
	
	// I/O in filter methods
	
	public boolean readFiles(){
		try {
			System.out.println("Start geting rid of stop words ...\n");
			String dir = option.dir;
			if (!dir.endsWith(File.separator)) {
				dir += File.separator;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(dir + option.dfile),"GBK"));
		
			stops.readStops();
			int counter = 0;
			int flag = 0;
			String line = "";
			String endoc = "";
			String chdoc = "";
			
			// count number of words in corpus 
			
			System.out.println("Start counte number of words in whole corpus ... " );
			
			while (true) {
				line = reader.readLine();
				if (line == null)
					break;
				if (line.startsWith("<doc")) {
					flag = 1;
					continue;
				} else if (flag == 1 && line.startsWith("</doc")) {
					
					HashSet<String> enTemp = new HashSet<String>();
					HashSet<String> chTemp = new HashSet<String>();
					String[] enWords = endoc.split(" ");
					String[] chWords = chdoc.split(" ");
					int length = enWords.length;
					for(int i = 0; i < length; i ++ ){
						if(!enTemp.contains(enWords[i])){
							if(!stops.conEn(enWords[i])){
								
								// Add English word to Dictionary , which is not stop words;
								
								if(!enDic.keySet().contains(enWords[i])){
									enDic.put(enWords[i], 1);
								}else {
									enDic.put(enWords[i],  enDic.get(enWords[i]) + 1);
								}
								temp.enAdd(enWords[i]);
							}
						}else if(temp.enContains(enWords[i])){
							enDic.put(enWords[i], enDic.get(enWords[i]) + 1);
						}
					}
					
					length = chWords.length;
					for(int i = 0; i < length; i ++ ){
						if(!chTemp.contains(chWords[i])){
							if(!stops.conCh(chWords[i])){
								
								// Add Chinese word to Dictionary , which is not stop words;
								
								if(!chDic.keySet().contains(chWords[i])){
									chDic.put(chWords[i], 1);
								}else{
									chDic.put(chWords[i], chDic.get(chWords[i]) + 1);
								}
								
								temp.chAdd(chWords[i]);
							}
						}else if(temp.chContains(chWords[i])){
							chDic.put(chWords[i], chDic.get(chWords[i]) + 1);
						}
					}
					
					// save to array
					
					if(temp.isValue()){
						docs[counter] = new Document(temp);
						counter++;
					}
					temp.fresh();
					
					endoc = "";
					chdoc = "";
					flag = 0;
					if (counter >= option.ndocs) {
						break;
					}
					continue;
				}
				
				// extract real string 
				
				if (flag == 1) {
					if (line.startsWith("<src>")) {
						chdoc += line.substring(6, line.length() - 6);
					} else if (line.startsWith("<trg>")) {
						endoc += line.substring(6, line.length() - 6);
					}
				}
			}
			
			this.counter = counter ;
			System.out.println("Get rid of stops doc finshed ! \nThe num of documents is "
					+ counter);
			reader.close();
			return true;
		} catch (IOException e) {
			System.out.println("Read files error while geting rid of stop words ! "
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}

	}
	
	public boolean saveFiles(){
		String dir = option.dir;
		if (!dir.endsWith(File.separator)) {
			dir += File.separator;
		}
		
		try{
			
			System.out.println("Start saving filter ... " );
			BufferedWriter enWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(dir
							+ "plain.en")));
			BufferedWriter chWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(dir
							+ "plain.ch")));
			
			for(int i = 0 ; i < ndocs; i ++ ){
								
				enWriter.write(docs[i].enFilterToString(enDic, option.filter) + "\n");
				chWriter.write(docs[i].chFilterToString(chDic, option.filter) + "\n");
				
			}

			enWriter.close();
			chWriter.close();
			System.out.println("Save filter finished ! ");
			return true;
		}catch(IOException e){
			System.out.println("Save file error ! " + e.getMessage() );
			e.printStackTrace();
			return false;
		}
	}
}