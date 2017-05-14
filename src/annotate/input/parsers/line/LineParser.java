/**
 * 
 */
package annotate.input.parsers.line;

/**
 * @author jaschasilbermann
 * 
 * This interface is to be used by parsers which read a line of text and return an object.
 *
 */
public interface LineParser {

	static final String NEW_LINE = "\n";
	
	Object feedLine( String line ) ;
	
}
