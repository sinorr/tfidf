package tfidf;

public class tfidf{
	public static void main(String [] args){
		
		String datafile = "./corpus/plain.en";
		String outfile = "./corpus/tag.txt";
		
		Integer M = 1000;
		
		Dataset data = new Dataset(M);
		data.readFile(datafile);
		data.savefile(outfile);
		
		System.out.println("Done! " );
	}
}