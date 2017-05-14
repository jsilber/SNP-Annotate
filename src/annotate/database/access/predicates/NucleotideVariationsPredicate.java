/**
 * 
 */
package annotate.database.access.predicates;

import java.util.Vector;

import annotate.genome.NucleotideVariations;
import annotate.genome.interfaces.LocalizedSequenceFeature;

/**
 * @author jaschasilbermann
 * <p>
 * Adds no new functionality to the Sequence Feature Predicate,
 * but constrains tyhe entire query to Nucleotide Variations.
 * <p>
 * This should give a performance improvement over using a
 * Sequence Feature Predicate with clazz = NucleotideVariations.class .
 * 
 */
public class NucleotideVariationsPredicate extends SequenceFeaturePredicate {
	

	/**
	 * 
	 */
	public NucleotideVariationsPredicate() {
		super();
	}



	/**
	 * @param clazz
	 * @param names
	 * @param sequences
	 */
	public NucleotideVariationsPredicate(Class clazz, String kind, Vector names, Vector sequences) {
		super(clazz, kind, names, sequences);
	}

	public boolean match( NucleotideVariations variations ) {
		return super.match( (LocalizedSequenceFeature) variations);
	}
	
}
