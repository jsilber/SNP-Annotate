/**
 * 
 */
package annotate.genome.sequence;

import annotate.exceptions.LocationMismatchException;
import annotate.genome.GenomicLocation;
import annotate.genome.interfaces.LocalizedSequence;
import annotate.genome.interfaces.Location;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;


/**
 * @author jaschasilbermann
 *
 * 
 *
 */
public final class SimpleSequence extends AbstractLocalizedSequence { 

	// the Sequence's start position
	private int _start ;
	public int getStart() { return _start; }
	protected void _setStart( int start ) {
		if ( start > 0 ) {
			_start = start;
		}
		else {
			_start = 0;
		}
	}
	public void setStart(int start) { _setStart(start); }
	
	// the Sequence's end position
	private int _end ;
	public int getEnd() { return _end; }
	protected void _setEnd( int end ) {
		if ( end >= _start ) {
			_end = end;
		}
		else {
			_end = _start;
		}
	}
	public void setEnd(int end) { _setEnd(end); }
	
	// the Genomic Location of the Sequence
	protected Location _location ;
	public Location getLocation() { return _location; }
	public void setLocation(Location location) { _location = location; }
	
	/**
	 * @param location
	 * @param start
	 * @param end
	 */
	public SimpleSequence(Location location, int start, int end) {
		_location = location;
		_start = start;
		_end = end;
	}
	
	/**
	 * @param sequence
	 */
	public SimpleSequence(PointSequence sequence) {
		_start = sequence.start();
		_end = sequence.end();
		_location = sequence.location();
	}
	/**
	 * @param location
	 * @param position
	 */
	public SimpleSequence(Location location, int position) {
		_location = location;
		_start = position;
		_end = position;
	}
	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#start()
	 */
	public int start() {
		return _start;
	}

	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#end()
	 */
	public int end() {
		return _end;
	}
	
	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#size()
	 */
	public int size() {
		// the size is given by end - start + 1
		return _end - _start + 1;
	}
	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#location()
	 */
	public Location location() {
		return _location ;
	}
	/* (non-Javadoc)
	 * @see annotate.sequence.Sequence#extend(int, int)
	 */
	public void extend(int left, int right) {
		setStart( getStart() - left );
		setEnd( getEnd() + right );
	}
	

	
	/* (non-Javadoc)
	 * @see annotate.interfaces.LocalizedSequence#overlaps(annotate.genome.sequence.PointSequence)
	 */
	public boolean overlaps(PointSequence other) {
		try {
			LocalizedSequence.RelativePosition relative_position = new LocalizedSequence.RelativePosition(this, other);	
			return relative_position.hasOverlap();
		} catch (LocationMismatchException exception) {
			// Sequences are from different Genomic Locations
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see annotate.interfaces.LocalizedSequence#overlaps(annotate.genome.sequence.ContiguousSequence)
	 */
	public boolean overlaps(SimpleSequence other) {
		try {
			LocalizedSequence.RelativePosition relative_position = new LocalizedSequence.RelativePosition(this, other);	
			return relative_position.hasOverlap();
		} catch (LocationMismatchException exception) {
			// Sequences are from different Genomic Locations
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see annotate.interfaces.LocalizedSequence#overlaps(annotate.genome.sequence.GappedSequence)
	 */
	public boolean overlaps(ComplexSequence other) {
		return other.overlaps(this);
	}
	
	// Callback method called when this Simple Sequence is about to be stored
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

