/**
 * 
 */
package annotate.genome.feature.gene;

import annotate.genome.feature.SimpleFeature;
import annotate.genome.sequence.SimpleSequence;

/**
 * @author jaschasilbermann
 *
 */
public class CodingRegion extends SimpleFeature {

	public static final String KIND = "Coding Region" ;
	
	/**
	 * @param sequence
	 * @param name
	 */
	public CodingRegion(SimpleSequence sequence, String name) {
		super(sequence, name, KIND);
	}
	
}
