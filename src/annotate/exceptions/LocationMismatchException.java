/**
 * 
 */
package annotate.exceptions;

/**
 * @author jaschasilbermann
 *
 * A Location Mismatch Exception is thrown when attempts are made to mix information
 * coming from different Genomic Locations.
 * 
 * For example, trying to construct a Complex Sequence from two Simple Sequences from
 * different Genomic Locations will result in a Location Mismatch Exception being thrown.
 *
 */
public class LocationMismatchException extends Exception {

	/**
	 * @param arg0
	 */
	public LocationMismatchException(String arg0) {
		super(arg0);
	}
	
	/**
	 * @param arg0
	 * @param arg1
	 */
	public LocationMismatchException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
