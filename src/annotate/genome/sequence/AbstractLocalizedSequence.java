/**
 * 
 */
package annotate.genome.sequence;

import annotate.Environment;
import annotate.exceptions.LocationMismatchException;
import annotate.genome.interfaces.LocalizedSequence;
import annotate.genome.interfaces.Location;

/**
 * @author jaschasilbermann
 *
 * An Abstract Localized Sequence provides minimal implementations for many of the methods
 * in the Localized Sequence interface.
 * 
 * Most importantly, the #compareTo(LocalizedSequence) method is implemented.
 * 
 * Please note that specific implementations for the various overloaded #overlaps() methods
 * are located in the more specific classes.
 *
 */
public abstract class AbstractLocalizedSequence implements LocalizedSequence {
	
	public abstract boolean overlaps( PointSequence other ) ;
	
	public abstract boolean overlaps( SimpleSequence other ) ;
	
	public abstract boolean overlaps( ComplexSequence other ) ;
	
	/* (non-Javadoc)
	 * @see annotate.interfaces.Location#location()
	 */
	public abstract Location location() ;

	/* (non-Javadoc)
	 * @see annotate.interfaces.Sequence#end()
	 */
	public abstract int end() ;

	/* (non-Javadoc)
	 * @see annotate.interfaces.Sequence#length()
	 */
	public int length() {
		return end() - start();
	}

	/* (non-Javadoc)
	 * @see annotate.interfaces.Sequence#start()
	 */
	public abstract int start() ;

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object other) {
		if ( other instanceof LocalizedSequence ) {
			return compareTo( (LocalizedSequence) other);
		}
		return Environment.CLASS_MISMATCH;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo( LocalizedSequence other ) {
//		 Sequences can only be compared if they are from the same Genomic Location
		if ( this.location().equals(other.location()) ) {
			// this Sequence start farther to the left than the other Sequence
			// i.e. this Sequence is smaller compared to the other Sequence
			if ( this.start() < other.start() ) {
				return -1;
			}
			// this Sequence start farther to the right than the other Sequence
			// i.e. this Sequence is greater compared to the other Sequence
			else if ( this.start() > other.start() ) {
				return 1;
			}
			// both Sequences start at the same position
			// the shorter Sequence is considered to be smaller than the longer Sequence
			else /*if (this.start() == other.start())*/ {
				// this Sequence is shorter hence smaller than the other Sequence
				if (this.length() < other.length() ) {
					return -1;
				}
				// this Sequence is longer hence greater than the other Sequence
				else if (this.length() > other.length()) {
					return 1;
				}
				// both Sequences have the same length
				else /*if (this.length() == other.length())*/ {
					return 0;
				}
			}
		} // if ( this.location().equals(other.location()) )
		// the Sequences are from different Genomic Locations
		else {
			return Location.LOCATION_MISMATCH;
		}
	}
	
	// relate this Sequence to another, yielding a Relative Position
	public LocalizedSequence.RelativePosition relateTo( LocalizedSequence other )
	throws LocationMismatchException {
		try {
			return new LocalizedSequence.RelativePosition(this, other);
		}
		catch (LocationMismatchException exception) {
			throw exception;
		}
	}
	
	/* (non-Javadoc)
	 * @see annotate.interfaces.LocalizedSequence#overlaps(annotate.interfaces.LocalizedSequence)
	 */
	public boolean overlaps(LocalizedSequence other) {
		if ( other instanceof PointSequence ) {
			return overlaps( (PointSequence) other);
		}
		else if ( other instanceof SimpleSequence ) {
			return overlaps( (SimpleSequence) other);
		}
		else if ( other instanceof ComplexSequence ) {
			return overlaps( (ComplexSequence) other);
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return _toString();
	}
	
	// internal string representation
	protected String _toString() {
		return "[" + start() + " - " + end() + "] on " + location().toString(); 
	}
}
