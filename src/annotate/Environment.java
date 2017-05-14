/**
 * 
 */
package annotate;

/**
 * @author jaschasilbermann
 *
 * Environment information such as file paths, debug and error handling, and other
 * stuff that doesn't belong any place more specific.
 * 
 */
public final class Environment {
	
	// home folder used as the top folder of the project
	public static final String HOME_DIR = "/Users/jaschasilbermann/Code/Eclipse/Annotate/Annotate/";
	
	// the file name for the standard error output
	//public static final String STD_ERR = HOME_DIR + "/logs/annotation.log";
	
	// return value for #compareTo( Object )
	// if the Argument Object cannot be compared to the receiving Object
	public static final int CLASS_MISMATCH = Integer.MIN_VALUE ;
	
	// set this field to turn the debug mode on and off
	public static boolean debugModeOn = false ;
	
	// prints a debug message with the given string
	public static void debugMessage( String message ) {
		if (debugModeOn) {
			System.out.println( "-> " + message );	
		}
		else {
			; // nothing
		}
	}
	
	// toggles the display of error messages
	public static boolean showErrors = true ;
	
	// prints an error message with the given string
	public static void errorMessage( String message ) {
		if (showErrors) {
			System.err.println( "***** " + message + " *****" );	
		}
		else {
			; // nothing		
		}
	}
}
