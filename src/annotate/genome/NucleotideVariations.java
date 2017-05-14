/**
 * 
 =
 */
package annotate.genome;

import java.util.Iterator;
import java.util.Vector;

import annotate.Environment;
import annotate.genome.interfaces.LocalizedSequence;
import annotate.genome.interfaces.LocalizedSequenceFeature;
import annotate.genome.sequence.PointSequence;

/**
 * @author jaschasilbermann
 * 
 * A Nucleotid Variations object stores the information for a number of SNPs,
 * all of which are at the same position in the genome and have the same rsID.
 * 
 * The individual SNPs can be retrieved via the #next() method as long as #hasNext() returns true.
 * Make sure to call #getIterator() before using these two methods!
 *
 */
public final class NucleotideVariations implements LocalizedSequenceFeature {
	
	public static final class Snp {
		
		// this SNPs Allele
		private String _allele ;
		public String getAllele() { return _allele; }
		public void setAllele(String allele) { _allele = allele; }
		
		// the observed Frequency for the given Population
		private float _frequency ;
		public float getFrequency() { return _frequency; }
		public void setFrequency(float frequency) { _frequency = frequency; }
		
		// the Population in which this SNP was observed
		private String _population ;
		public String getPopulation() { return _population; }
		public void setPopulation(String population) { _population = population; }
		
		// the SNP Class (e.g. CSHL-HAPMAP:HapMap-CEU, PERLEGEN:AFD_EUR_PANEL, etc.)
		private String _snpClass ;
		public String getSnpClass() { return _snpClass; }
		public void setSnpClass(String snp_class) { _snpClass = snp_class; }
		
		// the NucleotideVariations Object this Snp belongs to
		private NucleotideVariations _variations ;
		public NucleotideVariations getVariations() { return _variations; }
		public void setVariations(NucleotideVariations variations) { _variations = variations; }
		
		/**
		 * @param allele
		 * @param frequency
		 * @param population
		 * @param snpClass
		 */
		public Snp(String allele, float frequency, String population, String snpClass) {
			_allele = allele;
			_frequency = frequency;
			_population = population;
			_snpClass = snpClass;
			_variations = null;
		}
		
		// String representation
		public String toString() {
			return "Population " + _population + " (" + _snpClass + ") has Allele " + _allele + " with Frequency " + _frequency ;
		}
		
		public String toFileString() {
			return "Population:" + _population +
			"\t" + "SNP Class:" + _snpClass +
			"\t" + "Allele:" + _allele +
			"\t" + "Allele Frequency:" + _frequency ;
		}
	}
	
	// 
	public static final String KIND = "Nucleotide Variations" ;
	
	// the rsID
	private String _rsid ;
	public String getRsid() { return _rsid; }
	public void setRsid(String rsid) { _rsid = rsid; }
	
	// the Nucleotide Variations position given by a Point Sequence
	private PointSequence _position;
	
	// the SNPs
	private Vector _snps ;
	
	// iterator over the SNPs
	private Iterator _snps_i ;
	
	/**
	 * @param rsid
	 * @param location
	 * @param position
	 */
	public NucleotideVariations(String rsid, GenomicLocation location, int position) {
		_rsid = rsid;
		_position = new PointSequence(location, position);	
		_snps = new Vector();
	}
	
	// are there any SNPs ?
	public boolean hasSnps() {
		return ! _snps.isEmpty();
	}
	
	// add an SNP to these Variations
	// this will also link the added SNP to the Variations
	public void addSnp( Snp the_snp ) {
		// add the Snp to the SNPs vector
		_snps.add(the_snp);
		// link the newly added Snp to these NucleotideVariations
		the_snp.setVariations(this);
	}
	
	// SNP iterator
	public void getIterator() {
		_snps_i = _snps.iterator();
	}
	
	// are there more SNPs in the vector ?
	public boolean hasNext() {
		return _snps_i.hasNext();
	}
	
	// get the next SNP from the vector
	public Snp next() {
		return (Snp) _snps_i.next();
	}
	
	// string representation
	// for display on screen
	public String toString() {
		Iterator snps_i = _snps.iterator();
		String result = "| SNPs for " + _rsid + " on " + sequence().toString() + ":\n";
		while ( snps_i.hasNext() ) {
			result += "| " + snps_i.next().toString() + "\n";
		}
		return result;
	}
	
	// string representation 
	// for tab-seperated file output
	public String toFileString() {
		Iterator snps_i = _snps.iterator();
		String result = "> rsID:" + _rsid +
		"\t" + "Chromosome:" + _position.getLocation().getChromosome() +
		"\t" + "Strand:" + _position.getLocation().getStrand() +
		"\t" + "Position:" + _position.start() + "\n";
		while ( snps_i.hasNext() ) {
			result += "| " + ((Snp) snps_i.next()).toFileString() + "\n";
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see annotate.interfaces.Name#name()
	 */
	public String name() {
		return _rsid;
	}
	/* (non-Javadoc)
	 * @see annotate.interfaces.Kind#kind()
	 */
	public String kind() {
		return KIND;
	}
	
	/* (non-Javadoc)
	 * @see annotate.genome.interfaces.LocalizedSequenceHolder#sequence()
	 */
	public LocalizedSequence sequence() {
		return _position;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object other) {
		if ( other instanceof LocalizedSequence ) {
			return compareTo( (LocalizedSequence) other );
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
