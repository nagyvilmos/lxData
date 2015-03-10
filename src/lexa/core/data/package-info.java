/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * package-info.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2013-03-07   WNW 13-03       Tidy up for initial publication to Code project.
 * 2013-07-11   WNW 13-07       Bug fix in SealedDataSet
 * 2013-08-10   WNW             Add support for quoted strings to DataReader.
 * 2013-08-10   WNW             Added support fot iterating the DataSet.
 * 2013-08-12   WNW             Refactored the whole of persistance.  Each data
 *                              type now has a formatting class to convert to and
 *                              from the persisted strings.  Quite cool really.
 * 2014-03-08	WNW				Another hit on persistance, moved a lot of the 
 *								formatting for an item out to new helper classes.
 *								These are then useable outside of the flat style.
 * 2014-10-02	WNW	14-10		Build and submit to git.
 * 2015-03-05	WNW 15-03		Refactor to move values into their own class and 
 *								add the concept of an array.
 *								The .put methods return the DataSet to allow chaining
 *								The .add methods return the ValueArray to allow chaining.
 *								Add ARRAY and LONG types.
 *================================================================================
 */
/**
 * Provides the classes for managing a polymorphic set of data.
 *
 * <p>Each {@link lexa.core.data.DataSet} is a list of {@code key/value} pairs stored as
 * a {@link lexa.core.data.DataItem}.
 * The {@code key} is always a {@link java.lang.String} and the {@code value} can be any type of object.
 * There is inbuilt support for the basic data types of {@link java.lang.Boolean},
 * {@link lexa.core.data.DataSet}, {@link java.util.Date}, {@link java.lang.Double},
 * {@link java.lang.Integer} and {@link java.lang.String}.
 * <p>Support is provided to serialise and deserialise the data using the inbuilt
 * {@link lexa.core.data.formatting.Format} classes.
<p>Data can be sent to streams using the
 * {@link lexa.core.data.io.DataReader} and {@link lexa.core.data.io.DataWriter}.
 * @author William N-W
 * @since 2009-07
 */
package lexa.core.data;
