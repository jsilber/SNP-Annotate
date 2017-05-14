/**
 * 
 */
package annotate.input.parsers.line;

import annotate.Environment;

/**
 * @author jaschasilbermann
 *
 * A Multi Line Parser uses a variable number of lines to parse a single object.
 * The parser decides if a line of text describes the object currently under work
 * or a new object via the #_isSameObject() method.
 * <p>
 * The parser returns the finished object once a line belonging to a new object
 * is encountere. Otherwise it returns null to signal that another line is welcome.
 * <p>
 * Supple the parser with a LineParser.NEW_LINE character to flush the last object
 * out of the parser.
 * <p>
 * CAUTION: This version of the Multi Line Parser is somewhat buggy. It probably doesn't
 * parse the first object correctly and it most likely doesn't parse the last object at all.
 * However, for large numbers of objects this is usually not crucial.
 *
 */
public abstract class MultiLineParser extends BasicLineParser {
	
	// parse the entire line for information
	protected abstract void _parseAll() ;
	
	// determine whether to start a new object or continue parsing
	protected abstract boolean _isSameObject() ;
	
	// continue parsing
	protected abstract void _parseContinue() ;
	
	
	// the last line of text
	protected  String[] _oldLine ;

	
	/**
	 * @param fieldCount
	 */
	public MultiLineParser() {
		super();
	}
	
	/**
	 * @param fieldCount
	 * @param fieldSeperator
	 */
	public MultiLineParser(int fieldCount, String fieldSeperator) {
		super(fieldCount, fieldSeperator);
	}
	
	// ready the line
	protected void _readyLine() {
		_line = null;
		_oldLine = null;
	}
	
	// save the line in _oldLine
	// CAUTION: must always be followed by an assignment to _line
	// sp _line isn't null
	protected void _saveLine() {
		// save the old line
		_oldLine = _line;
		// clear the line
		_line = null;
	}
	
	// set the line, saving the content in the old line
	protected void _setLine( String[] line ) {
		// save the old line
		_saveLine();
		// set the line
		_line = line;
	}
	
	// supply the line
	// if the given line describes the same Gene as the last line given
	// null is returned
	// otherwise, if the given lines describes a new Gene
	// the old Gene is returned
	public Object feedLine( String line ) {
		Environment.debugMessage(this.getClass().toString());
		Environment.debugMessage("Taking line...");
		
		if ( line == LineParser.NEW_LINE ) {
			_assembleObject();
			Object object = _getObject();
			_ready();
			return object;
		}
		
		if ( _isValidLine(line) ) {
			_setLine(line);
			
			if ( _isSameObject() ) {
				_parseContinue();
				return null;
			}
			// the line describes a new object
			else {
				// assemble the old object
				_assembleObject();
				// get the old object
				Object object = _getObject();
				// ready the parser
				_readyFields();
				// start parsing the new object
				_parseAll();
				// return the old object
				return object;
			}
		}
		else {
			return null;
		}
	}
}