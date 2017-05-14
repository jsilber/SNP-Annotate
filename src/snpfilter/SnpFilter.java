/**
 * 
 */
package snpfilter;

import java.util.Date;
import java.util.Vector;

import annotate.database.access.DatabaseResult;
import annotate.database.access.predicates.FeaturePredicate;
import annotate.database.access.predicates.SimpleVariationsPredicate;
import annotate.database.access.queries.DatabaseQuery;
import annotate.genome.interfaces.LocalizedSequenceFeature;

/**
 * @author jaschasilbermann
 *
 */
public class SnpFilter {

	// predicate for the Features to be queried
	private FeaturePredicate _featurePredicate ;
	
	// rsIDs for the SNPs
	private Vector _rsIDs = null;
	
	// the Sequences to scan for SNPs
	private Vector _sequences = null;
	
	// the query's result
	private DatabaseResult _result ;
	
	private int _featureCount ;

	private Date _queryTime ;
	
	/**
	 * @param featurePredicate
	 * @param rsIDs
	 */
	public SnpFilter(FeaturePredicate featurePredicate, Vector rsIDs) {
		_featurePredicate = featurePredicate;
		_rsIDs = rsIDs;
		_sequences = new Vector();
	}

	// run the Feature query
	protected void _runFeatureQuery() {
		DatabaseQuery query = new DatabaseQuery( _featurePredicate ); 
		DatabaseResult result = new DatabaseResult( query );
		result.get();
		
		_featureCount = result.size();
		
		if ( result.size() != 0 ) {
			while ( result.hasNext() ) {
				_sequences.add( ((LocalizedSequenceFeature) result.next()).sequence() );
			}
		}
	}
	
	// run the SNP query
	protected void _runSnpQuery() {
		SimpleVariationsPredicate variations_predicate = new SimpleVariationsPredicate(
			null, null, _rsIDs, _sequences
		);
		DatabaseQuery query = new DatabaseQuery( variations_predicate );
		_result = new DatabaseResult( query );
		_result.get();
	}
	
	// run the query
	public DatabaseResult runQuery() {
		// save the start time
		Date then = new Date();
		
		// query for Features
		_runFeatureQuery();
		// query for SNPs
		_runSnpQuery();
		
		// get the end time
		Date now = new Date();
		// and compute the total query time
		_queryTime  = new Date( now.getTime() - then.getTime() );
		
		return _result;
	}

	/**
	 * @return Returns the queryTime as String.
	 */
	public String getQueryTime() {
		return _queryTime.getMinutes() + ":" + _queryTime.getSeconds();
	}

	/**
	 * @return Returns the featureCount.
	 */
	public int getFeatureCount() {
		return _featureCount;
	}
	
}
