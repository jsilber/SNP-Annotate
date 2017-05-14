/**
 * 
 */
package annotate.genome.feature;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import annotate.exceptions.LocationMismatchException;
import annotate.genome.interfaces.LocalizedSequence;
import annotate.genome.sequence.ComplexSequence;

/**
 * @author jaschasilbermann
 *
 * A Complex Sequence Feature consists of a number of Sequence Features, Simple or Complex.
 * 
 */
public class ComplexFeature extends Feature {

	// the Features
	private Vector _features ;
	
	// iterator for the Features
	private Iterator _features_i ;
	
	// Complex Sequence shadowed by this Complex Sequence Feature
	private ComplexSequence _sequence ;
	
	
	/**
	 * 
	 */
	public ComplexFeature() {
		super();
		_features = new Vector();
		_sequence = new ComplexSequence();
	}

	/**
	 * @param name
	 * @param kind
	 */
	public ComplexFeature(String name, String kind) {
		super(name, kind);
		_features = new Vector();
		_sequence = new ComplexSequence();
	}

	/**
	 * @param name
	 * @param kind
	 */
	public ComplexFeature(String name, String kind, Feature feature) {
		super(name, kind);
		_features = new Vector();
		_sequence = new ComplexSequence();
		_features.add(feature);
	}

	// retrieve the first Feature
	public Feature first() {
		return (Feature) _features.firstElement();
	}
	
	// retrieve the first Feature
	public Feature last() {
		return (Feature) _features.lastElement();
	}	
	
	// get an interator for the Features
	public void getIterator() {
		_features_i = _features.iterator();
	}
	
	// are there more Features ?
	// ATTENTION: call only after #getIterator() !
	public boolean hasNext() {
		return _features_i.hasNext();
	}
	
	// get the next Feature
	// ATTENTION: call only after #getIterator() !
	public Feature next() {
		return (Feature) (_features_i.next());
	}

	/* (non-Javadoc)
	 * @see annotate.sequence.feature.SequenceFeature#sequence()
	 */
	public LocalizedSequence sequence() {
		return _sequence;
	}
	
	// add a Sequence Feature to this Complex Sequence Feature
	public void add( Feature feature )
	throws LocationMismatchException {
		if ( feature != null ) {
			try {
				_sequence.add(feature.sequence());
				_features.add(feature);
				Collections.sort(_features);
			}
			
			catch (LocationMismatchException exception) {
				throw new LocationMismatchException( "Trying to add a Sequence Feature from different Genomic Location.", exception );
			}
		}
	}
	
	// string representation
	public String toString() {
		String string = _toString() + " contains:";
		// write contained Sequence Feature
		getIterator();
		while ( hasNext() ) {
			string += "| " + next().toString();
		}
		return string;
	}


}
