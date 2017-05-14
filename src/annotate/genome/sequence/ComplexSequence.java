/**
 * 
 */
package annotate.genome.sequence;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import annotate.exceptions.LocationMismatchException;
import annotate.genome.interfaces.LocalizedSequence;
import annotate.genome.interfaces.Location;

/**
 * @author jaschasilbermann
 *
 * A Complex Sequence consists of a number of Simple Sequences. 
 * The Simple Sequences have to be from the same Genomic Location but there may be a gap between any two Simple Sequences.
 * 
 * The Simple Sequences are ordered by their position (see Sequence#compareToSequence()).
 * 
 * When adding a new Simple Sequence any overlaps produced will result in appropriate joining so that the Complex Sequence
 * always has an efficient structure.
 * 
 * e.g. 
 * Complex Sequence: [ (1,100) , (102,200) ]
 * now adding (100,102) will result in the Complex Sequence looking like this: [ (1,200) ]
 * 
 * It is also possible to add a Complex Sequence to another Complex Sequence.
 * However, this will simply break the Complex Sequence to be added into its constituting Simple Sequences
 * and add those. 
 */
public final class ComplexSequence extends AbstractLocalizedSequence {
	
	// the Sequences making up the Complex Sequence
	private Vector _sequences ;

	// iterator over the Sequences
	private Iterator _sequences_i ;
	
	/**
	 * 
	 */
	public ComplexSequence() {
		_sequences = new Vector();
	}

	/**
	 * 
	 */
	public ComplexSequence( SimpleSequence sequence ) {
		_sequences = new Vector();
		_sequences.add(sequence);
	}
	
	/**
	 * 
	 */
	public ComplexSequence( ComplexSequence sequence ) {
		_sequences = sequence._sequences;
	}
	
	//	 join two overlapping Simple Sequences whose relative position is known
	// this is more of an auxiliary method
	protected SimpleSequence _join( SimpleSequence left, SimpleSequence right ) {
		return new SimpleSequence( left.location(), left.start(), right.end() );
	}
	
	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#start()
	 */
	public int start() {
		return first().start();
	}

	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#end()
	 */
	public int end() {
		return last().end();
	}

	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#location()
	 */
	public Location location() {
		return first().location();
	}
	
	// retrieve the first Sequence
	public SimpleSequence first() {
		return (SimpleSequence) _sequences.firstElement();
	}
	
	// retrieve the first Sequence
	public SimpleSequence last() {
		return (SimpleSequence) _sequences.lastElement();
	}

	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#extend(int, int)
	 */
	public void extend(int left, int right) {
		SimpleSequence first = (SimpleSequence) first() ;
		SimpleSequence last = (SimpleSequence) last() ;
		
		first.setStart( first.getStart() - left );
		last.setEnd( last.getEnd() + right );
	}
	
	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#size()
	 */
	public int size() {
		int size = 0;
		getIterator();
		while ( hasNext() ) {
			size += next().size();
		}
		return size;
	}
	
	// get an interator for the Sequences
	public void getIterator() {
		_sequences_i = _sequences.iterator();
	}
	
	// are there more Sequences ?
	// ATTENTION: call only after #getIterator() !
	public boolean hasNext() {
		return _sequences_i.hasNext();
	}
	
	// get the next Sequence
	// ATTENTION: call only after #getIterator() !
	public SimpleSequence next() {
		return (SimpleSequence) (_sequences_i.next());
	}

	// string representation
	public String toString() {		
		String representation = _toString() + " contains:\n";
		getIterator();
		while ( hasNext() ) {
			representation += "| " + next().toString() + "\n";
		}
		return representation;
	}
	
	// add a Sequence to this Complex Sequence
	public void add( LocalizedSequence sequence )
	throws LocationMismatchException {
		if ( sequence != null ) {
			try {
				if ( sequence instanceof PointSequence ) {
					_add( new SimpleSequence( (PointSequence) sequence) );
				}
				else if ( sequence instanceof SimpleSequence ) {
					_add( (SimpleSequence) sequence );
				}
				else if ( sequence instanceof ComplexSequence ) {
					_add( (ComplexSequence) sequence );
				}
			} 
			catch (LocationMismatchException exception) {
				throw new LocationMismatchException( "Trying to add a Sequence from a different Genomic Location.", exception );
			}
		}
	}
	
	// add a Simple Sequence to this Complex Sequence
	// the Simple Sequence is checked for an overlap with each Simple Sequence already in this Complex Sequence
	// if an overlap is found the overlapping Simple Sequences are joined and the process is repeated with the joined Simple Sequence
	// whenever there is no overlap the currently checked Simply Sequence is simply copied
	protected void _add( SimpleSequence sequence )
	throws LocationMismatchException {
		// new Vector to hold the checked Simple Sequences
		Vector sequences = new Vector(this._sequences.size());
		// cycle though this Complex Sequence's Simple Sequences
		this.getIterator();
		while ( this.hasNext() ) {
			// the Simple Sequence currently being considered
			SimpleSequence current_sequence = (SimpleSequence) this.next();
			// relate the current Simple Sequence to the Simple Sequence to be added
			LocalizedSequence.RelativePosition relative_position = sequence.relateTo(current_sequence);
			// if the two Simple Sequences overlap
			if ( relative_position.hasOverlap() ) {
				// join them and assign the joined Simple Sequence to the Simple Sequence to be added
				// so the process can be repeated
				sequence = _join( (SimpleSequence) relative_position.left(), (SimpleSequence) relative_position.right() );
			}
			// if the two Simple Sequences do not overlap
			else {
				// simply add the current Simple Sequence to the new vector
				sequences.add(current_sequence);
			}
		}
		// having checked the Simple Sequence to be added against all Simple Sequences already in this Complex Sequence
		// and having produced the appropriate joined Simple Sequence
		// add the - possibly joined - Simple Sequence to be added to the new vector
		sequences.add(sequence);
		// sort the new vector
		Collections.sort(sequences);
		// and replace the old Sequences vector of this Complex Sequence with the new one
		this._sequences = sequences;
	}
	
	// add another Complex Sequence to this Complex Sequence
	// this simply calls #_addSimpleSequence() for each Simple Sequence in the Complex Sequence to be addedd
	protected void _add( ComplexSequence sequence )
	throws LocationMismatchException {
		sequence.getIterator();
		while ( sequence.hasNext() ) {
			this._add( (SimpleSequence) sequence.next() );
		}
	}


	
	/* (non-Javadoc)
	 * @see annotate.interfaces.LocalizedSequence#overlaps(annotate.genome.sequence.PointSequence)
	 */
	public boolean overlaps(PointSequence other) {
		getIterator();
		while (hasNext()) {
			SimpleSequence sequence = next();
			if ( sequence.overlaps(other) ) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see annotate.interfaces.LocalizedSequence#overlaps(annotate.genome.sequence.ContiguousSequence)
	 */
	public boolean overlaps(SimpleSequence other) {
		getIterator();
		while (hasNext()) {
			SimpleSequence sequence = next();
			if ( sequence.overlaps(other) ) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see annotate.interfaces.LocalizedSequence#overlaps(annotate.genome.sequence.GappedSequence)
	 */
	public boolean overlaps(ComplexSequence other) {
		getIterator();
		while (hasNext()) {
			SimpleSequence sequence = next();
			other.getIterator();
			while (other.hasNext()) {
				if ( sequence.overlaps(other.next()) ) {
					return true;
				}
			}
		}
		return false;
	}
} 
