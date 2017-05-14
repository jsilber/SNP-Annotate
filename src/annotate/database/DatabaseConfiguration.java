/**
 * 
 */
package annotate.database;

import annotate.genome.NucleotideVariations;
import annotate.genome.NucleotideVariations.Snp;
import annotate.genome.feature.Feature;
import annotate.genome.sequence.PointSequence;
import annotate.genome.sequence.SimpleSequence;

import com.db4o.Db4o;

/**
 * @author jaschasilbermann
 * <p>
 * Configuration commands for the db4o database.
 * <p>
 * There is a problem with the indexing in that the indexes below are defined in terms
 * of actual fields, but become useless when using accessor methods in native queries.
 * <p>
 * For example, the field _allele is indexed, but not the values of #getAllele().
 *
 */
public class DatabaseConfiguration {
	
	public static void configureDatabase() {
		
		// Database Default
		Db4o.configure().activationDepth(1);
		Db4o.configure().optimizeNativeQueries(true);
		Db4o.configure().allowVersionUpdates(true);
		
		// SNP
		Db4o.configure().objectClass(Snp.class).minimumActivationDepth(1);
		Db4o.configure().objectClass(Snp.class).objectField("_allele").indexed(true);
		Db4o.configure().objectClass(Snp.class).objectField("_frequency").indexed(true);
		Db4o.configure().objectClass(Snp.class).objectField("_population").indexed(true);

		// Nucleotide Variations
		Db4o.configure().objectClass(NucleotideVariations.class).minimumActivationDepth(3);
		Db4o.configure().objectClass(NucleotideVariations.class).objectField("_rsid").indexed(true);
		
		// Point Sequence
		Db4o.configure().objectClass(PointSequence.class).minimumActivationDepth(2);
		Db4o.configure().objectClass(PointSequence.class).objectField("_position").indexed(true);
		
		// Simple Sequence
		Db4o.configure().objectClass(SimpleSequence.class).minimumActivationDepth(2);
		Db4o.configure().objectClass(SimpleSequence.class).objectField("_start").indexed(true);
		Db4o.configure().objectClass(SimpleSequence.class).objectField("_end").indexed(true);
		
		// Sequence Feature
		Db4o.configure().objectClass(Feature.class).minimumActivationDepth(2);
		Db4o.configure().objectClass(Feature.class).objectField("_name").indexed(true);
		Db4o.configure().objectClass(Feature.class).objectField("_kind").indexed(true);
	}
	
}
