/**
 * 
 */
package annotate.database.access.predicates;

import java.util.Vector;

import annotate.genome.feature.Feature;
import annotate.genome.interfaces.LocalizedSequenceFeature;

/**
 * @author jaschasilbermann
 * <p>
 * Adds no new functionality to the Sequence Feature Predicate,
 * but constrains tyhe entire query to Sequence Features.
 * <p>
 * This should give a performance improvement over using a
 * Sequence Feature Predicate with clazz = FeaturePredicate.class .
 *
 */
public class FeaturePredicate extends SequenceFeaturePredicate {

	/**
	 * 
	 */
	public FeaturePredicate() {
		super();
	}

	/**
	 * @param clazz
	 * @param names
	 * @param sequences
	 */
	public FeaturePredicate(Class clazz, String kind, Vector names, Vector sequences) {
		super(clazz, kind, names, sequences);
	}

	public boolean match( Feature feature ) {
		return super.match( (LocalizedSequenceFeature) feature );
	}
}
