/**
 * 
 */
package annotate.input.database;

import annotate.Environment;
import annotate.database.Database;
import annotate.input.parsers.file.FileParser;

/**
 * @author jaschasilbermann
 *
 */
public class FileParserInput {
	
	private FileParser _fileParser ;
	
	// number of lines read and parsed before a clean-up is performed
	// cleaning-up involves calling #commit() and a #purge() on the database and a #gc() on the system
	public int CLEAN_UP_THRESHOLD = 10000;
	
	/**
	 * @param fileParser
	 */
	public FileParserInput(FileParser fileParser) {
		_fileParser = fileParser;
	}

	// the clean-up procedures
	private static void _cleanUp() {
		Database.commit();
		Database.purgeAll();
		System.gc();
	}
	
	// display status message
	public String status() {
		String status = "Status: "
			+ _fileParser.linesProcessed() + " lines processed | "
			+ _fileParser.objectsParsed() + " objects parsed.";
		return status;
	}
	
	public void fileToDatabase() {
		
		// open the database
		Database.open();	
		Environment.debugMessage("Database opened.");
	
		try {
			while ( _fileParser.hasObject() ) {
				// get the next object
				Object object = _fileParser.nextObject();
				// store it in the database
				// and purge it from memory (i.e. forget it)
				Database.store(object);
				Environment.debugMessage("Object stored in database.");
				if ( _fileParser.objectsParsed() % CLEAN_UP_THRESHOLD == 0 ) {
					_cleanUp();
					Environment.debugMessage("Clean-up done.");
					Environment.debugMessage( status() );
				}
			}
		} 
		catch (Exception exception) {
			exception.printStackTrace();
		}	
		finally {
			// close the database
			Database.close();
		}
	}
	
}
