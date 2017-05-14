/**
 * 
 */
package annotate.database;

import java.util.Date;

import annotate.Environment;
import annotate.genome.interfaces.LocalizedSequenceFeature;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

/**
 * Access to the underlying database (normally db4o).
 * <p>
 * In this version there is only one database file for all the data.
 * However, due to performance issues this may have to be changed in the future.
 *
 *@author jaschasilbermann
 */
public class Database {
	
	private Database() {
		// Database is used in a static context all the time
		// therefore no instantiation is desired
	}
	
	// folder the database resides in below the home folder given in annotate.Environment
	private static final String DATABASE_DIR	= "db/";
	
	// name of the database file in use
	private static final String DATABASE_FILE_NAME = "human.db4o";
	
	// the database
	private static ObjectContainer _database = null;
	
	private static Date _warmUpTime ;
	
	/** 
	 * Is the database open?
	 * There is an issue with opening the database multiple times in db40 so
	 *  #is_open() is used to prevent multiple #open() to the same database.
	 * 
	 */
	private static boolean _isOpen = false;
	public boolean isOpen() { return _isOpen; }
	
	// open the database
	public static void open() {
		if ( ! _isOpen ) {
			_database = Db4o.openFile( Environment.HOME_DIR + DATABASE_DIR + DATABASE_FILE_NAME );
			_isOpen = true;
		}
		else { /* nothing */ }
	}
	
	// close the database
	public static void close() {
		_database.close();
		_isOpen = false;
	}
	
	protected static void _set( Object object ) {
		_database.set(object);
	}
	
	protected static ObjectSet _get( Object object ) {
		return _database.get(object);
	}
	
	// store an object in the database
	public static void store( Object object ) {
		// defaults to #storeAndRemember()
		storeAndRemember( object );
	}
	
	// store an object and forget about it
	// this calls #purge(object)
	public static void storeAndForget( Object object ) {
		_set(object);
		purge(object);
	}
	
	//	store an object without purging 
	public static void storeAndRemember( Object object ) {
		_set(object);
	}
	
	// commit database changes
	public static void commit() {
		_database.commit();
	}
	
	// purge the entire Database reference
	public static void purgeAll() {
		_database.ext().purge();
	}
	// purge a specific object from the Database reference
	public static void purge( Object object ) {
		_database.ext().purge(object);	
	}
	
	// retrieve an object from the database
	public static ObjectSet retrieve( Object object ) {
		return _get(object);
	}
	
	// get a query
	public static Query query() {
		return _database.query();
	}
	
	// run a query
	public static ObjectSet query( Predicate predicate ) {
		return _database.query(predicate);
	}
	
	// run a query
	public static ObjectSet query( Class clazz ) {
		return _database.query(clazz);
	}
	
	public static void activate( Object object, int depth ) {
		_database.activate(object, depth);
	}
	
	public static void warmUp() {
		// save the start time
		Date then = new Date();
		
		// load all Localized Sequence Features
		ObjectSet lsf = query( LocalizedSequenceFeature.class );
		while ( lsf.hasNext() ) {
			activate( lsf.next(), 3 );
		}
		
		// save the end time
		Date now = new Date();
		// and compute the total warm up time
		_warmUpTime = new Date( now.getTime() - then.getTime() );
		System.out.println( "Query returned: " + lsf.size() );
	}
	
	public static String getWarmUpTime() {
		return _warmUpTime.getMinutes() + ":" + _warmUpTime.getSeconds();
	}
	
	public static void configure() {
		DatabaseConfiguration.configureDatabase();
	}
}
