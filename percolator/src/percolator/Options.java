package percolator;

import org.kohsuke.args4j.*;

public class Options{
	
	@Option( name = "-plain", usage = "Specify whether we want to get the plain text !")
	public boolean plain = false;
	
	@Option( name = "-ridstops", usage = "Specify whether we want to get rid of the stopsword !")
	public boolean ridstops = false;
	
	@Option( name = "-filter", usage = "Specify whether we want to get rid of words with low frequence !")
	public int filter = 0;
	
	@Option( name = "-dir", usage = "Specify directory of data and output!")
	public String dir = "/home/cyno/corpus/tang/";
	
	@Option( name = "-dfile", usage = "Specify data file !")
	public String dfile = "sample_data.txt";
	
	@Option( name = "-dstops", usage = "Specify the stopwords file !")
	public String dstops = "stoplist";
	
	@Option( name = "-ndocs", usage = "Stecity number of documents you want !")
	public int ndocs = 900;

	
}