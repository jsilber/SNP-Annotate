/**
 * 
 */
package annotate.genome.feature;

import annotate.genome.interfaces.LocalizedSequence;

/**
 * @author jaschasilbermann
 *
 * A Simple Sequence Feature consists of one Sequence (Simple or Complex) and a name and kind specifying that Sequence.
 * 
 * Examples:
 * 
 * 1.) Simple Sequence Feature with a Simple Sequence
 * This can be used to model any Feature with one continous Sequence, e.g. a Contig
 * 
 * 2.) Simple Sequence Feature with a Complex Sequence
 * This serves to model a Feature with a non-contigous Sequence, e.g. the DNA-origin of a spliced RNA
 */
public class SimpleFeature extends Feature {

	// the Sequence for this Feature
	private LocalizedSequence _sequence ;
	protected void _setSequence( LocalizedSequence sequence ) {
		_sequence = sequence;
	}
		
	/**
	 * @param name
	 * @param kind
	 */
	public SimpleFeature(String name, String kind) {
		super(name, kind);
	}

	/**
	 * @param sequence
	 * @param name
	 * @param kind
	 */
	public SimpleFeature(LocalizedSequence sequence, String name, String kind) {
		super(name, kind);
		_sequence = sequence;
	}
	
	/* (non-Javadoc)
	 * @see annotate.sequence.feature.SequenceFeature#sequence()
	 */
	public LocalizedSequence sequence() {
		return _sequence;
	}
	
	// string representation
	public String toString() {
		return _toString();
	}

}
