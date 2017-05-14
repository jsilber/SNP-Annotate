/**
 * 
 */
package annotate.database.access;

import java.util.Iterator;

import annotate.database.Database;
import annotate.database.access.queries.DatabaseQuery;

import com.db4o.ObjectSet;

/**
 * @author jaschasilbermann
 * <p>
 * The result returned when executing a Database Query.
 * <p> 
 * Serves mainly to work around problems with db4o's ObjectSet.
 * <p>
 * Especially, you can set an activation depth here and every object
 * returned by a call to #next() will be activated to the given depth.
 *
 */
public class DatabaseResult implements Iterator {

	private int _activationDepth = 3;
	
	// the query to be used
	private DatabaseQuery _query;
	
	// the query's result
	private ObjectSet _result;
	
	/**
	 * @param query
	 */
	public DatabaseResult(DatabaseQuery query) {
		_query = query;
	}

	// get the query's result
	public void get() {
		_result = _query.execute();
	}
	
	public int size() {
		if ( _result != null )
			return _result.size();
		else
			return 0;
	}
	
	// more objects in result ?
	public boolean hasNext() {
		if ( _result != null )
			return _result.hasNext();
		else 
			return false;
	}
	
	// get the next object
	// this will activate the object
	public Object next() {
		// retrieve the object from the object set
		Object next = _result.next();
		
		// activate the object
		Database.activate( next, _activationDepth );
		
		return next;
	}

	/**
	 * @return Returns the activationDepth.
	 */
	public int getActivationDepth() {
		return _activationDepth;
	}

	/**
	 * @param activationDepth The activationDepth to set.
	 */
	public void setActivationDepth(int activationDepth) {
		_activationDepth = activationDepth;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		_result.remove();
	}
	
}
