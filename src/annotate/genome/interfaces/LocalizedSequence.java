/**
 * 
 */
package annotate.genome.interfaces;

import annotate.exceptions.LocationMismatchException;

/**
 * @author jaschasilbermann
 *
 */
public interface LocalizedSequence extends Localized, Sequence, Comparable {

	/**
	 * @author jaschasilbermann
	 *
	 * A Relative Position compares two Localized Sequences and decides which
	 * one is further to the left on their Genomic Location and which one is further
	 * to the right.
	 * 
	 * Please note that one Sequence can be both on the left AND on the right if it extends
	 * farther to both sides than the other Sequence.
	 */
	public static final class RelativePosition {
		
		// the Sequence on the left
		private LocalizedSequence _left ;
		public LocalizedSequence left() {
			return _left ;
		}
		
		// the Sequence on the right
		private LocalizedSequence _right ;
		public LocalizedSequence right() {
			return _right ;
		}
		
		/**
		 * @param sequence1
		 * @param sequence2
		 */
		public RelativePosition(LocalizedSequence sequence1, LocalizedSequence sequence2) 
		throws LocationMismatchException {
			// compare the two Sequences
			switch ( sequence1.compareTo(sequence2) ) {
			// Sequence 1 is smaller than Sequence 2
			case -1:
				_left = sequence1;
				_right = sequence2;
				break;
			// Sequence 1 is equal to Sequence 2	
			case 0:
				_left = sequence1;
				_right = sequence2;
				break;
			// Sequence 1 is greater than Sequence 2	
			case	 1:
				_left = sequence2;
				_right = sequence1;
				break;
			// The Sequences are from different Genomic Locations	
			case Location.LOCATION_MISMATCH:
				throw new LocationMismatchException("Trying to relate two Sequences from different Genomic Locations.");
			// unknown case	
			default:
				// nothing
				break;
			}
			
			// check if the left Sequence extends farther to the right than the right Sequence
			if ( _left.end() > _right.end() ) {
				// then left and right must both point to the
				// same Sequence
				_right = _left;
			}
		}	
	
		// does the left Sequence overlap the right one ?
		public boolean hasOverlap() {
			return _left.end() >= _right.start();
		}
	}

	// compare two Localized Sequences
	// The information used in the comparison include the Genomic Locations,
	// the start positions and the Sequences lengths.
	int compareTo( LocalizedSequence other );
	
	// 
	RelativePosition relateTo( LocalizedSequence other ) 
	throws LocationMismatchException;
	
	boolean overlaps( LocalizedSequence other );
}
