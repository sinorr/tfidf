package percolator;

import org.kohsuke.args4j.*;

public class percolator{
	public static void main(String [] args){
		Options option = new Options();
		CmdLineParser parser = new CmdLineParser(option);
		
		try{
			if(args.length == 0 ){
				showHelp(parser);
				return ;
			}
			
			parser.parseArgument(args);
			Dataset dataset = new Dataset(option);
			
			if(option.filter > 0){
				dataset.tranFilter();
			}else if(option.ridstops){
				dataset.tranRidStops();
			}else if(option.plain){
				dataset.tranPlain();
			}
		}catch(CmdLineException e){
			System.out.println("Command line error: " + e.getMessage() );
			showHelp(parser);
			return ;
		}catch(Exception e ){
			System.out.println("Error in main : " + e.getMessage() );
			showHelp(parser);
			return ;
		}
	}
	public static void showHelp(CmdLineParser parser){
		System.out.println("percolator [options...] [arguments...]");
		parser.printUsage(System.out);
	}
}