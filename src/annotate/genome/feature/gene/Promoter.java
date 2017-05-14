/**
 * 
 */
package annotate.genome.feature.gene;

import annotate.exceptions.LocationMismatchException;
import annotate.genome.feature.ComplexFeature;
import annotate.genome.feature.SimpleFeature;
import annotate.genome.sequence.SimpleSequence;

/**
 * @author jaschasilbermann
 *
 */
public class Promoter extends ComplexFeature {
	
	public static final String KIND = "Promoter";
	
	public static final String CORE_REGION_KIND = "Promoter Core Region";
	public static final String EXTENDED_REGION_KIND = "Promoter Extended Region";
	public static final String TOTAL_REGION_KIND = "Promoter Total Region";
	
	// number of positions the core Promoter extends upstream of its Gene
	public static int _coreRegion = 100 ;
	
	// number of positions the extended Promoter extends upstream of its Gene
	public static int _extendedRegion = 500 ;
	
	// number of positions the total Promoter extends upstream of its Gene
	public static int _totalRegion = 5000 ;

	/**
	 * @param sequence
	 * @param name
	 * 
	 * The Sequence given has length zero and marks the beginning of the Gene.
	 */
	public Promoter(SimpleSequence sequence, String name) {
		super(name, KIND);
		
		int end = sequence.getStart();
	
		// build the core Promoter
		SimpleFeature core = new SimpleFeature( 
				new SimpleSequence(sequence.location(), end - _coreRegion, end), 
				name, CORE_REGION_KIND );
		
		// build the extended Promoter
		SimpleFeature extended = new SimpleFeature( 
				new SimpleSequence(sequence.location(), end - _extendedRegion, end), 
				name, EXTENDED_REGION_KIND );
		
		// build the total Promoter
		sequence.setStart( end - _totalRegion );
		SimpleFeature total = new SimpleFeature( 
				new SimpleSequence(sequence.location(), end - _totalRegion, end), 
				name, TOTAL_REGION_KIND );
		
		// add the Promoter Regions
		try {
			this.add(core);
			this.add(extended);
			this.add(total);
		}
		catch (LocationMismatchException exception) {
			System.err.println("Error Constructing Promoter " + name + " on " + sequence.toString() + ".");
		}
	}
	
	
}
