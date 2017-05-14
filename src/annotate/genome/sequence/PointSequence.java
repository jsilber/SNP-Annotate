/**
 * 
 */
package annotate.genome.sequence;

import annotate.genome.GenomicLocation;
import annotate.genome.interfaces.Location;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

/**
 * @author jaschasilbermann
 *
 * 
 *
 */
public final class PointSequence extends AbstractLocalizedSequence {

	
	/**
	 * @param location
	 * @param position
	 */
	public PointSequence(GenomicLocation location, int position) {
		_location = location;
		_position = position;
	}
	
	// Genomic Location of this Point Sequence
	private GenomicLocation _location ;
	public GenomicLocation getLocation() { return _location; }
	public void setLocation(GenomicLocation location) { _location = location; }
	
	// the Position on the Genomic Location
	private int _position ;
	public int getPosition() { return _position; }
	public void setPosition(int position) { _position = position; }	
	
	/* (non-Javadoc)
	 * @see annotate.interfaces.Location#location()
	 */
	public Location location() {
		return _location;
	}
	
	// two Point Sequence are equal
	// if they are from the same Genomic Location and
	// they start at the same position 
	public boolean equals(PointSequence other) {
		return ( this.location().equals(other.location()) &&
				this.start() == other.start());
	}
	
	
	// a Point Sequence only overlaps one other Point Sequence: itself
	public boolean overlaps(PointSequence other) {
		return this.equals(other);
	}

	/* (non-Javadoc)
	 * @see annotate.interfaces.LocalizedSequence#overlaps(annotate.genome.sequence.ContiguousSequence)
	 */
	public boolean overlaps(SimpleSequence other) {
		return other.overlaps(this);
	}

	/* (non-Javadoc)
	 * @see annotate.interfaces.LocalizedSequence#overlaps(annotate.genome.sequence.GappedSequence)
	 */
	public boolean overlaps(ComplexSequence other) {
		return other.overlaps(this);
	}

	/* (non-Javadoc)
	 * @see annotate.interfaces.Sequence#size()
	 */
	public int size() {
		return 1;
	}
	/* (non-Javadoc)
	 * @see annotate.genome.sequence.AbstractLocalizedSequence#end()
	 */
	public int end() {
		return _position;
	}
	/* (non-Javadoc)
	 * @see annotate.genome.sequence.AbstractLocalizedSequence#start()
	 */
	public int start() {
		return _position;
	}
	
	// Callback method called when this Point Sequence is about to be stored
	// in the Db4o database.
	// This will ensure that there is only one Genomic Location object per
	// Genomic Location in the dataabse.
	public boolean objectCanNew( ObjectContainer db ) {
		// query for the Genomic Location to see if it already exists
		ObjectSet result = db.get( _location );
		// and if so replace this Simple Sequence's Genomic Location
		// with the one already stored in the database
		if ( result.size() != 0 ) {
			GenomicLocation location = (GenomicLocation)result.next();
			this._location = location;
		}
		return true;
	}
	
	// see #objectCanNew()
	public boolean objectCanUpdate( ObjectContainer db ) {
		return objectCanNew(db);
	}
	
}
