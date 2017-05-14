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
public class Exon extends SimpleFeature {
	
	public static final String KIND = "Exon" ;
	
	// is this Exon constitutive
	private boolean _constitutive ;
	public boolean isConstitutive() { return _constitutive; }
	public void setConstitutive(boolean constitutive) { _constitutive = constitutive; }
	
	// Ensembl Exon ID
	public String ensemblID() {
		return _name;
	}
	
	/**
	 * @param sequence
	 * @param name
	 * @param constitutive
	 */
	public Exon(SimpleSequence sequence, String name, boolean constitutive) {
		super(sequence, name, KIND);
		_constitutive = constitutive;
	}
}
