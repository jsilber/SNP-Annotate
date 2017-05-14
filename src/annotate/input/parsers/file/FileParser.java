/**
 * 
 */
package annotate.input.parsers.file;

/**
 * @author jaschasilbermann
 *
 */
public interface FileParser {
	
	// does the parser have another object ?
	public boolean hasObject() ;
	
	// get the next object from the parser
	public Object nextObject() ;
	
	// number of lines processed so far
	public int linesProcessed() ;
	
	// number of objects parsed so far
	public int objectsParsed() ;
}
