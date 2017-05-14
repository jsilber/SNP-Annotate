/**
 * 
 */
package annotate.genome.feature.gene;

import annotate.genome.feature.SimpleFeature;
import annotate.genome.sequence.SimpleSequence;

/**
 * @author jaschasilbermann
 *
 */
public class ExonIntronBoundary extends SimpleFeature {

	public static final String KIND = "Exon-Intron Boundary";
	
	public static final String START_BOUNDARY_KIND = "Exon Start Boundary";
	public static final String END_BOUNDARY_KIND = "Exon End Boundary";
	public static final String UNKNOWN_BOUNDARY_KIND = "Unknown Exon Boundary";
	
	// the number of positions in either direction around the boundary
	public static int _boundaryRegion = 10 ;
	
	/**
	 * @param sequence
	 * @param name
	 * @param is_start_boundary
	 */
	public ExonIntronBoundary(SimpleSequence sequence, String name, String boundaryKind) {
		super(name, boundaryKind);
		sequence.setStart( sequence.getStart() - _boundaryRegion );
		sequence.setEnd( sequence.getEnd() + _boundaryRegion );
		_setSequence(sequence);
	}
}
