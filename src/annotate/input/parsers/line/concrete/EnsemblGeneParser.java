/**
 * 
 */
package annotate.input.parsers.line.concrete;

import java.util.Iterator;
import java.util.Vector;

import annotate.Environment;
import annotate.genome.GenomicLocation;
import annotate.genome.feature.Feature;
import annotate.genome.feature.gene.CodingRegion;
import annotate.genome.feature.gene.Exon;
import annotate.genome.feature.gene.ExonIntronBoundary;
import annotate.genome.feature.gene.Gene;
import annotate.genome.feature.gene.Promoter;
import annotate.genome.sequence.SimpleSequence;
import annotate.input.parsers.line.MultiLineParser;

/**
 * @author jaschasilbermann
 *
 * The Ensembl Gene Parser parses a file in a certain Ensembl Gene format to produce
 * a number of Gene objects.
 *
 */
public class EnsemblGeneParser extends MultiLineParser {
	
	// Chromosome field
	public int _chromosomeField = 0 ;
	
	// Gene start position field
	public  int _geneStartField = 1 ;
	
	// Gene end position field
	public  int _geneEndField = 2 ;
	
	// Strand field
	public  int _strandField = 3 ;
	
	// Ensembl Gene ID field
	public  int _geneIdField = 4 ;
	
	// HGNC Symbol field
	public  int _hgncSymbolField = 5 ;
	
	// Ensembl Exon ID field
	public  int _exonIdField = 6 ;
	
	// Exon start position
	public  int _exonStartField = 7 ;
	
	// Exon end position
	public  int _exonEndField = 8 ;
	
	// Coding region start position
	public  int _codingStartField = 9 ;
	
	// Coding region end position
	public  int _codingEndField = 10 ;
	
	// Constitutive Exon field
	public  int _isConstitutiveField = 11 ;
	

	// the Gene
	private  Gene _gene ;
	
	// Genomic Location for this Gene
	private  GenomicLocation _location ;
	
	// the Gene's Promoter
	private  Promoter _promoter ;
	
	// the Gene's Exon-Intron Boundaries
	private  Vector _exonIntronBoundaries ;
	
	// the Gene's Exons
	private  Vector _exons ;
	
	// the Gene's Coding Regions
	private  Vector _codingRegions ;


	/**
	 * @param fieldCount
	 * @param fieldSeperator
	 */
	public EnsemblGeneParser(int fieldCount, String fieldSeperator) {
		super(fieldCount, fieldSeperator);
	}



	/**
	 * 
	 */
	public EnsemblGeneParser() {
		super();
	}
	
	// compare two lines to see if they describe the same Gene
	// compare by HGNC Symbol
	protected  boolean _isSameObject() {
		// try-catch for the   
		try {
			// the old line is null => parsing a new Gene has just started
			if ( _oldLine == null ) {
				return false;
			} 
			
			// the two lines have different numbers of fields
			else if ( _oldLine.length != _line.length ) {
				return false;
			}
			
			else if ( _oldLine.toString() == _line.toString() ) {
				Environment.debugMessage("Found duplicate line.");
				return true ;
			}
			
			// compare by HGNC Symbol
			else if ( _oldLine[ _hgncSymbolField ].equals(_line[ _hgncSymbolField ]) ) {
				return true ;
			}	
			else {
				return false ;	
			}
		}
		catch (Exception exception) {
			Environment.errorMessage("Error with the input line !");
			return false;
		}
	}
	
	// parse the line for information common to all lines of this Gene
	// (Gene, Genomic Location, Promoter)
	protected void _parseAll() {
		// parse the first fields
		_parseGene();
		_parseLocation();
		_parsePromoter();
		// continue parsing
		_parseContinue();
	}
	
	// parse the line for unique information
	// (Exons, Exon-Intron Boundaries, Coding Regions)
	protected void _parseContinue() {
		_parseExon();
		_parseExonIntronBoundaries();
		_parseCodingRegion();
	}
	
	// set the fields to ready state
	// do this before starting to parse a new object
	protected void _readyFields() {
		// ready this classes fields
		_gene = null;
		_location = null;
		_promoter = null;
		_exonIntronBoundaries = new Vector();
		_exons = new Vector();
		_codingRegions = new Vector();
	}
	
	// build the object after parsing
	protected void _assembleObject() {
			// add the Promoter
			try {
				_gene.add(_promoter);
			} catch (Exception exception) {
				Environment.errorMessage("Error adding Promoter to Gene !");
			}
			
			try {	
				// add the Exons
				Iterator iterator = _exons.iterator();
				while ( iterator.hasNext() ) {
					Feature exon = (Feature) iterator.next();
					if ( exon != null ) {
						_gene.add(exon);
					}
				}
			}
			catch (Exception exception) {
				Environment.errorMessage("Error adding Exon to Gene !");
			}
			
			try {
				// add the Exon-Intron Boundaries
				// first remove the first and the last Boundary, as these are not relevant
				_exonIntronBoundaries.removeElementAt(0);
				_exonIntronBoundaries.removeElementAt(_exonIntronBoundaries.size() - 1);
				Iterator iterator = _exonIntronBoundaries.iterator();
				while ( iterator.hasNext() ) {
					Feature exon_intron_boundary = (Feature) iterator.next();
					if ( exon_intron_boundary != null ) {
						_gene.add(exon_intron_boundary);
					}
				}
			}
			catch (Exception exception) {
				Environment.errorMessage("Error adding Exon-Intron Boundary to Gene !");
			}
			
			try {
				// add the Coding Regions
				Iterator iterator = _codingRegions.iterator();
				while ( iterator.hasNext() ) {
					Feature coding_region = (Feature) iterator.next();
					if ( coding_region != null ) {
						_gene.add(coding_region);
					}
				}
			}
			catch (Exception exception) {
				Environment.errorMessage("Error adding Coding Region to Gene !");
			}	
			
			// set the object to the Gene
			_setObject( _gene );
	}
	
	// parse the Genomic Location information out of the line
	private  void _parseLocation() {
		try {
			String chromosome_name = _line[ _chromosomeField ];
			int strand = Integer.parseInt( _line[ _strandField ] );
			// set the Genomic Location
			_location = new GenomicLocation( chromosome_name, strand );
		}
		catch (Exception exception) {
			Environment.errorMessage("Error parsing Genomic Location !");
		}
	}
	
	// parse the Gene information out of the line
	private  void _parseGene() {
		try {
			// assemble the Gene
			String name = _line[ _geneIdField ];
			String hgnc_symbol = _line[ _hgncSymbolField ];
			// set the Gene
			_gene = new Gene(name,hgnc_symbol);
		}
		catch (Exception exception) {
			Environment.errorMessage("Error parsing Gene !");
		}
	}
	
	// parse the Exon ouf of the line
	private  void _parseExon() {
		try {
			// assemble the Exon
			int exon_start = Integer.parseInt(_line[ _exonStartField ]);
			int exon_end = Integer.parseInt(_line[ _exonEndField ]);
			String exon_name = _line[ _exonIdField ];
			boolean constitutive = intToBool( Integer.parseInt(_line[ _isConstitutiveField ]) );			 
			Exon exon = 
				new Exon(new SimpleSequence(_location,exon_start,exon_end), exon_name, constitutive );
			// add the Exon
			_exons.add(exon);
		}
		catch (Exception exception) {
			Environment.errorMessage("Error parsing Exon !");
		}
	}
	
	// parse the Exon-Intron Boundaries ouf of the line
	private  void _parseExonIntronBoundaries() {
		try {
			// assemble the Exon-Intron Boundaries
			String name = _line[ _exonIdField ];
			int exon_start = Integer.parseInt( _line[ _exonStartField ] );
			int exon_end = Integer.parseInt( _line[ _exonEndField ] );
			ExonIntronBoundary start_boundary = 
				new ExonIntronBoundary(
						new SimpleSequence(_location,exon_start), 
						name, ExonIntronBoundary.START_BOUNDARY_KIND );
			ExonIntronBoundary end_boundary = 
				new ExonIntronBoundary(
						new SimpleSequence(_location,exon_end), 
						name, ExonIntronBoundary.END_BOUNDARY_KIND );
			// add the Boundaries
			_exonIntronBoundaries.add(start_boundary);
			_exonIntronBoundaries.add(end_boundary);
		}
		catch (Exception exception) {
			Environment.errorMessage("Error parsing Exon-Intron Boundary !");
		}
	}	
	
	// parse the Coding Region out of the line
	private  void _parseCodingRegion() {
		try {
			// assemble the Coding Region
			int start = Integer.parseInt(_line[ _codingStartField ]);
			int end = Integer.parseInt(_line[ _codingEndField ]);
			String name = _line[ _exonIdField ];
			CodingRegion coding_region = 
				new CodingRegion(new SimpleSequence(_location,start,end), name);
			// add the Coding Region
			if ( _codingRegions != null ) {
				_codingRegions.add(coding_region);	
			}
			
		}
		catch (Exception exception) {
			Environment.errorMessage("Error parsing Coding Region !");
		}
	}
	
	// parse the Promoter information out of the line
	private  void _parsePromoter() {
		// assemble the Promoter
		try {
			// assemble the Promoter
			int end = Integer.parseInt(_line[ _geneStartField ]);
			String name = _line[ _geneIdField ];
			Promoter promoter =
				new Promoter(new SimpleSequence(_location,end), name);
			// set the Promoter
			_promoter = promoter;
		}
		catch (Exception exception) {
			Environment.errorMessage("Error parsing Promoter !");
			_promoter = null;
		}
	}
}
