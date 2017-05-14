/**
 * 
 */
package annotate.database.access.predicates;

import java.util.Iterator;
import java.util.Vector;

import annotate.genome.NucleotideVariations;
import annotate.genome.interfaces.Identity;
import annotate.genome.interfaces.LocalizedSequence;

/**
 * @author jaschasilbermann
 * 
 * Serves as a replacement for Nucleotide Variations Predicate.
 * <p>
 * Uses a more efficient, less general approach to compute overlap
 * than the one used by Sequence Feature Predicate.
 * 
 *
 */
public class SimpleVariationsPredicate extends IdentityPredicate {

	private Vector _sequences = null;
	
	/**
	 * 
	 */
	public SimpleVariationsPredicate() {
		super();
	}

	/**
	 * @param clazz
	 * @param kind
	 * @param names
	 */
	public SimpleVariationsPredicate(Class clazz, String kind, Vector names, Vector sequences) {
		super(clazz, kind, names);
		_sequences = sequences;
	}
	
	protected boolean matchSequence( NucleotideVariations variations ) {
		if ( _sequences == null ) {
			return true;
		}
		
		Iterator sequences_i = _sequences.iterator();
		while ( sequences_i.hasNext() ) {
			LocalizedSequence sequence = (LocalizedSequence) sequences_i.next();
			if ( variations.sequence().location().equals(sequence.location()) &&
				variations.sequence().start() >= sequence.start() && 
				variations.sequence().end() <= sequence.end() ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean match( NucleotideVariations variations  ) {
		return super.match( (Identity) variations ) && matchSequence( variations );
	}
}
