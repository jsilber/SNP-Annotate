/*
 * 
 * 
 */
package annotate.genome;

import annotate.genome.interfaces.Location;

/**
 * @author jaschasilbermann
 *
 * Genomic Locations store information about the genomic context of a Localized Sequence.
 * 
 * 
 */
public final class GenomicLocation implements Location {
	
	// Chromosome
	private String _chromosome ;
	public String getChromosome() { return _chromosome; }
	public void setChromosome(String chromosome) { _chromosome = chromosome; }
	
	// Strand
	private int _strand ;
	public int getStrand() { return _strand; }
	public void setStrand(int strand) { _strand = strand; }
	
	/**
	 * @param chromosome
	 * @param strand
	 */
	public GenomicLocation(String chromosome, int strand) {
		_chromosome = chromosome;
		_strand = strand;
	}
	
	// convert Genomic GenomicLocation to String
	public String toString() {
		return "Chromosome " + _chromosome + ", Strand " + _strand;		
	}		
	
	// equality
	// only here to satisfy #compareTo() in Object
	// see below for GenomicLocation-specific case
	public boolean equals( Object other ) {
		if ( other instanceof GenomicLocation ) {
			return this.equals( (GenomicLocation) other );
		}
		// the other Object is not a Genomic Location
		return false;
	}
	
	// Genomic Location equality
	public boolean equals( GenomicLocation other ) {
		return ( _chromosome.equals(other.getChromosome()) && _strand == other.getStrand() ); 
	}
}
