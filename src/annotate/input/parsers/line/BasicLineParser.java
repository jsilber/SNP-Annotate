/**
 * 
 */
package annotate.input.parsers.line;


/**
 * @author jaschasilbermann
 *
 * A Basic Line Parser implements the basic operations on the line of text to be parsed
 * such as breaking the line into its parts and readying the object.
 *
 */
public abstract class BasicLineParser implements LineParser {
	
	// number of fields in the file
	// default is zero, this will result in the line being split
	// into the appropriate number of fields automatically
	public int _fieldCount = 0 ;
	
	// field seperator
	// default is tab ("\t")
	public String _fieldSeperator = "\t";
	
	
	// the line of text to be parsed, broken into fields
	protected  String[] _line ;
	
	// the object to be built from the parsed information
	protected Object _object ;
	
	
	/**
	 * @param fieldCount
	 * @param fieldSeperator
	 */
	public BasicLineParser(int fieldCount, String fieldSeperator) {
		_fieldCount = fieldCount;
		_fieldSeperator = fieldSeperator;
		
		_ready();
	}
	
	/**
	 * 
	 */
	public BasicLineParser() {
		_ready();
	}

	protected void _ready() {
		_readyLine();
		_readyObject();
		_readyFields();
	}

	// ready the line
	protected void _readyLine() {
		_line = null;
	}
	
	protected boolean _isValidLine( String line ) {
		if ( line == null || line == "" ) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// set the line
	protected void _setLine( String[] line ) {
		_line = line;
	}
	
	// set the line given a String string
	protected void _setLine( String line ) {
		_setLine( _splitLine(line) );
	}
	
	// break a line into fields
	// using _fieldSeperator and _fieldCount 
	protected String[] _splitLine( String line ) {
		// split the line into individual fields
		String[] strings = line.split( _fieldSeperator, _fieldCount );	
		return strings;
	}
	
	// set the object
	protected void _setObject( Object object ) {
		_object = object;
	}
	
	// get the object
	protected Object _getObject() {
		return _object;
	}
	
	// assemble the object from parsed information
	protected abstract void _assembleObject() ;
	
	// ready the classe's fields
	protected abstract void _readyFields() ;
	
	// ready the object
	protected void _readyObject() {
		_object = null;
	}
	
	// make a boolean out of an integer
	// auxiliary method
	public boolean intToBool(int i) {
		if ( i == 1 ) {
			return true ;
		}
		else {
			return false ;
		}
	}
}
