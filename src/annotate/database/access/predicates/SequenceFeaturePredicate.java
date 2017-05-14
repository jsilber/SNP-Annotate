/**
 * 
 */
package annotate.database.access.predicates;

import java.util.Iterator;
import java.util.Vector;

import annotate.genome.interfaces.Identity;
import annotate.genome.interfaces.LocalizedSequence;
import annotate.genome.interfaces.LocalizedSequenceFeature;

/**
 * @author jaschasilbermann
 * <p>
 * Provides methods to constrain the query to Localized Sequence Features
 * in a given set of Localized Sequences.
 *
 */
public class SequenceFeaturePredicate extends IdentityPredicate {

	private Vector _sequences = null;

	/**
	 * 
	 */
	public SequenceFeaturePredicate() {
		super();
	}

	/**
	 * @param clazz
	 * @param names
	 * @param sequences
	 */
	public SequenceFeaturePredicate(Class clazz,String kind, Vector names, Vector sequences) {
		super(clazz,kind,names);
		_sequences = sequences;
	}

	protected boolean matchSequence( LocalizedSequence sequence ) {
		if ( _sequences == null ) {
			return true;
		}
		
		Iterator sequences_i = _sequences.iterator();
		while ( sequences_i.hasNext() ) {
			LocalizedSequence current = (LocalizedSequence)(sequences_i.next());
		 	if ( current.overlaps(sequence) ) {
		 		return true;
		 	}
		}
		
		return false;
	}
	
	public boolean match( LocalizedSequenceFeature feature ) {
		return super.match( (Identity) feature) && matchSequence( feature.sequence() );
	}
	
}
