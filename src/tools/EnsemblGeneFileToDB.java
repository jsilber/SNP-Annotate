/**
 * 
 */
package tools;

import annotate.Environment;
import annotate.input.database.FileParserInput;
import annotate.input.parsers.file.SimpleFileParser;
import annotate.input.parsers.line.concrete.EnsemblGeneParser;

/**
 * @author jaschasilbermann
 *
 */
public class EnsemblGeneFileToDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// file name
		String file_name = Environment.HOME_DIR + "data/" + "Gene_Structures_on_Chr12.tsv";
		
		// file parser using the Ensembl Gene Parser
		SimpleFileParser file_parser = new SimpleFileParser( file_name, new EnsemblGeneParser() );
		
		// database input
		FileParserInput input = new FileParserInput( file_parser );
		input.fileToDatabase();
		Environment.debugModeOn = true;
		Environment.debugMessage( "End of input." + "\n" + input.status() );
		Environment.debugModeOn = false;
	}
}
