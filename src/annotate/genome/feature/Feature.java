/**
 * 
 */
package annotate.genome.feature;

import annotate.Environment;
import annotate.genome.interfaces.LocalizedSequence;
import annotate.genome.interfaces.LocalizedSequenceFeature;

/**
 * @author jaschasilbermann
 *
 */
public abstract class Feature implements LocalizedSequenceFeature {
	
	// name of this Sequence Feature (e.g. 'IFNG')
	protected String _name ;
	public String getName() { return _name; }
	public void setName(String name) { _name = name; }
	
	// kind of this Sequence Feature (e.g. 'Gene')
	protected String _kind ;
	public String getKind() { return _kind; }
	public void setKind(String kind) { _kind = kind; }
	
	/**
	 * 
	 */
	public Feature() {
	}
	
	/**
	 * @param name
	 * @param kind
	 */
	public Feature(String name, String kind) {
		_name = name;
		_kind = kind;
	}
	
	// get the Sequence for this Sequence Feature
	public abstract LocalizedSequence sequence() ;
	
	// internal string representation
	protected String _toString() {
		return getKind() + ": " + getName() + " on [" + sequence().start() + "-"  + sequence().end() + "]" + "\n";
	}
	
	// string representation
	public String toString() {
		return _toString();
	}
	
	/* (non-Javadoc)
	 * @see annotate.interfaces.Name#name()
	 */
	public String name() {
		return _name ;
	}

	/* (non-Javadoc)
	 * @see annotate.interfaces.Kind#kind()
	 */
	public String kind() {
		return _kind ;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object other) {
		if ( other instanceof LocalizedSequenceFeature ) {
			 return compareTo( (LocalizedSequenceFeature ) other );
		}
		else {
			return Environment.CLASS_MISMATCH;
		}
	}
	/* (non-Javadoc)
	 * @see annotate.genome.interfaces.LocalizedSequenceFeature#compareTo(annotate.genome.interfaces.LocalizedSequenceFeature)
	 */
	public int compareTo(LocalizedSequenceFeature other) {
		return this.sequence().compareTo(other.sequence());
	}
	

}
