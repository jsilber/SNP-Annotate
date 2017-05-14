/**
 * 
 */
package annotate.database.access.predicates;

import com.db4o.query.Predicate;

/**
 * @author jaschasilbermann
 * 
 * Base class for all derived Predicates.
 * <p>
 * Provides the #matchClass() method to constrain a query
 * to objects of the given class.
 * 
 */
public class DatabasePredicate extends Predicate {
 
/*	public static int limit = 0;
	private static int _count = 0;*/
	
	private Class _class = null;
	
	protected boolean matchClass( Object object ) {
		if ( _class == null ) {
			return true;
		}
		
		if ( _class.isInstance(object) ) {
			return true;
		}
		else return false;
	}
	
	public boolean match( Object object ) {
		return matchClass(object);
	}
	
	/**
	 * @param class1
	 */
	public DatabasePredicate(Class clazz) {
		_class = clazz;
	}

	/**
	 * 
	 */
	public DatabasePredicate() {
	}
	
	
	
}
