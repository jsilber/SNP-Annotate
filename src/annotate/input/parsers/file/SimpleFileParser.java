/**
 * 
 */
package annotate.input.parsers.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import annotate.Environment;
import annotate.input.parsers.line.LineParser;

/**
 * @author jaschasilbermann
 *
 */
public class SimpleFileParser implements FileParser {
	
	// the file reader for this file
	private BufferedReader _fileReader ;
	
	// the line parser for this file
	private LineParser _lineParser ;
	
	// the object to be built from parsing
	private Object _object ;
	
	// lines processed so far
	private int _linesProcessed = 0 ;
	public int linesProcessed() { return _linesProcessed; }
	
	
	// objects parsed so far
	private int _objectsParsed = 0 ;
	public int objectsParsed() { return _objectsParsed; }
	
	// should the first line be dropped ?
	// the first line contains the field names
	public boolean _dropFirstLine = true ;
	
	// feed a last newline after the input is done ?
	public boolean _feedTrailingNewline = true ;
	
	
	
	/**
	 * @param file_name
	 * @param line_parser
	 */
	public SimpleFileParser(String file_name, LineParser line_parser) {
		_setFile( file_name );
		_lineParser = line_parser;
		
		// drop the first line
		if ( _dropFirstLine ) {
			_readLine();
		}
	}
	
	// read the next line
	protected String _readLine() {
		try {
			String line = _fileReader.readLine();
			_linesProcessed++;
			return line;
			
		} catch (IOException exception) {
			Environment.errorMessage("Error reading line from file [" + _fileReader.toString() + "] !");
			return null;
		}
	}
	
	// parse the file 
	private void _parse() {
		if ( hasObject() ) {
			// line to be parsed
			String line = "";
			// label to break out of nested while
			parsing:
				while ( ( line = _readLine() ) != null ) {
					// object to be constructed
					Object object = null;
					// feed lines until an object (not null !) is returned
					while ( ( object = _lineParser.feedLine(line) ) != null ) {
						// an object was returned
						// store it in _object
						_setObject( object );
						// and break parsing
						break parsing;
					}
				}
		}
		// last line read
		else {
			// feed a reailing newline to get the last object from the line parser
			_setObject( _lineParser.feedLine(LineParser.NEW_LINE) );
		}
	}
	
	private void _setObject(Object object) {
		_object = object;
		_objectsParsed++;
	}


	protected void _setFile( String file_name ) {
		try {
			_fileReader = new BufferedReader(new FileReader( file_name ));
		} catch (FileNotFoundException exception) {
			Environment.errorMessage("File [" + file_name + "] not found !");
		}
	}

	// is there another line to be parsed ?
	public boolean hasObject() {
		try {
			return _fileReader.ready();
		}
		catch (IOException exception) {
			Environment.debugMessage("IO Error caught.");
			return false;
		}
	}
	
	// parse the next object
	public Object nextObject() {
		_parse();
		return _object;
	}
}
