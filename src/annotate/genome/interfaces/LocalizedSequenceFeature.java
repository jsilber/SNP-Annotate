/**
 * 
 */
package annotate.genome.interfaces;

/**
 * @author jaschasilbermann
 *
 */
public interface LocalizedSequenceFeature extends Identity, Comparable {
	
	LocalizedSequence sequence();
	
	int compareTo( LocalizedSequenceFeature other );
	
}
