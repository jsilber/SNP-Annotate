/**
 * 
 */
package annotate.database.access.queries;

import annotate.database.Database;
import annotate.database.access.predicates.DatabasePredicate;

import com.db4o.ObjectSet;


/**
 * @author jaschasilbermann
 * <p>
 * Just a simple wrapper that contains a Database Predicate as well
 * as an #execute() method which evaluates the Predicate and returns
 * an ObjectSet.
 *
 */
public class DatabaseQuery {

	private DatabasePredicate _predicate ;
	
	public ObjectSet execute() {
		return Database.query(_predicate); 
	}

	/**
	 * @param predicate
	 */
	public DatabaseQuery(DatabasePredicate predicate) {
		_predicate = predicate;
	}
	
}
