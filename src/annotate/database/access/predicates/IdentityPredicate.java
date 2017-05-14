/**
 * 
 */
package annotate.database.access.predicates;

import java.util.Iterator;
import java.util.Vector;

import annotate.genome.interfaces.Identity;

/**
 * @author jaschasilbermann
 * <p>
 * Provides methods to constrain the query to Identity objects
 * of a given kind with a name in a given set.
 *
 */
public class IdentityPredicate extends DatabasePredicate {
	
	private Vector _names = null;
	private String _kind = null;
	
	/**
	 * 
	 */
	public IdentityPredicate() {
		super();
	}

	/**
	 * @param clazz
	 * @param names
	 */
	public IdentityPredicate(Class clazz, String kind, Vector names) {
		super(clazz);
		_names = names;
		_kind = kind;
	}

	protected boolean matchKind( Identity identity ) {
		if ( _kind == null ) {
			return true;
		}
		
		return identity.kind().equals(_kind);
	}
	
	protected boolean matchIdentity( Identity identity ) {
		return matchKind(identity) && matchName(identity);
	}
	
	protected boolean matchName( Identity identity ) {
		if ( _names == null ) {
			return true;
		}
		
		Iterator names_i = _names.iterator();
		while ( names_i.hasNext() ) {
			if ( names_i.next().equals(identity.name()) ) {
				names_i.remove();
				return true;
			}
		}
		return false;
	}
	
	public boolean match( Identity identity ) {
		return super.match(identity) && matchIdentity(identity);
	}
}
