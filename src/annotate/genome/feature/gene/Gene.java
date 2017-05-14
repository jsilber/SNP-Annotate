/**
 * 
 */
package annotate.genome.feature.gene;

import annotate.genome.feature.ComplexFeature;

/**
 * @author jaschasilbermann
 *
 */
public class Gene extends ComplexFeature {

	public static final String KIND = "Gene" ;
	
	// HGNC symbol
	private String _hgncSymbol ;
	public String getHgncSymbol() { return _hgncSymbol; }
	public void setHgncSymbol(String hgncSymbol) { _hgncSymbol = hgncSymbol; }
	
	public String getName() {
		return _name + " (" + _hgncSymbol + ")"; 
	}
	
	/**
	 * @param name
	 * @param kind
	 */
	public Gene(String name, String hgnc_symbol) {
		super(name, KIND);
		_hgncSymbol = hgnc_symbol;
	}
}
