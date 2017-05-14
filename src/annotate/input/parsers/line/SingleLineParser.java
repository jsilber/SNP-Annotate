/**
 * 
 */
package annotate.input.parsers.line;

/**
 * @author jaschasilbermann
 *
 * A Single Line Parser returns one object per line of text parsed.
 *
 */
public abstract class SingleLineParser extends BasicLineParser {
	
	
	// parse the line
	protected abstract void _parse() ;
	
	// 
	public Object feedLine( String line ) {
		if ( _isValidLine(line) ) {
			// set the line
			_setLine(line);
			// parse the line
			_parse();
			// assemble the object
			_assembleObject();
			// get the object
			Object object = _getObject();
			// ready the parser for the next line
			_ready();
			// return the object
			return object;
		}
		else {
			return null;
		}
	}

	/**
	 * 
	 */
	public SingleLineParser() {
		super();
	}

	/**
	 * @param fieldCount
	 * @param fieldSeperator
	 */
	public SingleLineParser(int fieldCount, String fieldSeperator) {
		super(fieldCount, fieldSeperator);
	}
	
	
}
