/**
 * 
 */
package annotate.input.parsers.line.concrete;

import java.util.Iterator;
import java.util.Vector;

import annotate.Environment;
import annotate.genome.GenomicLocation;
import annotate.genome.NucleotideVariations;
import annotate.input.parsers.line.SingleLineParser;

/**
 * @author jaschasilbermann
 *
 */
public class EnsemblSnpParser extends SingleLineParser {

	// Chromosome field
	public int _chromosomeField = 0 ;
	
	// start position field
	public int _startPositionField = 1 ;
	
	// Strand field
	public  int _strandField = 2 ;
	
	// rsID field
	public int _rsIdField = 3 ;
	
	// Alleles
	public int _alleleField = 4 ;
	
	
	// Genomic Location of these Nucleotide Variations
	private GenomicLocation _location ;
	
	// the Nucleotide Variations
	private NucleotideVariations _variations ;
	
	// vector of SNPs
	private Vector _snps ;
	
	

	/**
	 * 
	 */
	public EnsemblSnpParser() {
		super();
	}

	/**
	 * @param fieldCount
	 * @param fieldSeperator
	 */
	public EnsemblSnpParser(int fieldCount, String fieldSeperator) {
		super(fieldCount, fieldSeperator);
	}

	protected void _readyFields() {
		_location = null ;
		_variations = null ;
		_snps = new Vector() ;
	}

	protected void _assembleObject() {
		Iterator snps_i = _snps.iterator();
		while ( snps_i.hasNext() ) {
			annotate.genome.NucleotideVariations.Snp snp = (annotate.genome.NucleotideVariations.Snp) snps_i.next();
			if ( snp != null ) {
				_variations.addSnp( snp );
			}
			else {
				// nothing
				;
			}
		}
		
		_setObject( _variations );
	}
	
	// parse the line
	protected void _parse() {
		_parseLocation();
		_parseVariations();
		_parseSnps();
	}

	// parse the Genomic Location from the line
	protected void _parseLocation() {
		try {
			// get information for the Genomic Location
			String chromosome = _line[ _chromosomeField ];
			int strand = Integer.parseInt( _line[ _strandField ] );
			// construct the Genomic Location
			_location = new GenomicLocation( chromosome, strand );
			// parsing succesful
		} catch (Exception exception) {
			Environment.errorMessage("Error parsing Location !");
			// parsing unsuccesful
		}
	}
	
	// parse the Nucleotide Variations from the line
	protected void _parseVariations() {
		try {
			// get information for the Nucleotide Variations
			int position = Integer.parseInt( _line[ _startPositionField ] );
			String rsId = _line[ _rsIdField ];
			// construct the Nucleotide Variations
			_variations = new NucleotideVariations( rsId, _location, position );
			// parsing succesful
		} catch (Exception exception) {
			Environment.errorMessage("Error parsing Nucleotide Variations !");
			// parsing unsuccesful
		}
	}

	// parse the SNPs from the line
	protected void _parseSnps() {
		try {
			// get the Alleles
			String allele_field = _line[ _alleleField ];
			// get individual SNP Classes
			String[] snp_classes = allele_field.split( "; " );
			// process each Class
			for ( int i = 0; i < snp_classes.length; i++ ) {
				try {
					// seperate the Class from the Population and the Allele Frequencies
					String[] class_plus_population_plus_frequencies = 
						snp_classes[i].split(" ", 2);
					String snp_class = class_plus_population_plus_frequencies[0];
					// seperate the Population from the Allele Frquencies
					String[] population_plus_frequencies =
						class_plus_population_plus_frequencies[1].split(":", 2);
					String population = population_plus_frequencies[0];
					String[] frequencies =  population_plus_frequencies[1].split(",");
					// process the Allele Frequencies
					for  ( int j = 0; j < frequencies.length; j++ ) {
						try {
							String[] allele_plus_frequency = frequencies[j].split(" ");
							String allele = allele_plus_frequency[0];
							float frequency = Float.parseFloat( allele_plus_frequency[1] );
							
							// assemble the SNP
							annotate.genome.NucleotideVariations.Snp snp = new annotate.genome.NucleotideVariations.Snp( allele, frequency, population, snp_class );
							// add the SNP to the SNPs vector
							_snps.add(snp);
						} catch (RuntimeException exception) {
							Environment.errorMessage("Error parsing Allele or Frequency !");
						}
					}
				} catch (Exception exception) {
					Environment.errorMessage("Error parsing SNP Class or Population !");
				}
			}
		} catch (Exception exception) {
			Environment.errorMessage("Error parsing SNP !");
		}
	}
}
