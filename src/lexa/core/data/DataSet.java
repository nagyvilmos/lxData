/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2009
 *--------------------------------------------------------------------------------
 * 2013-04-14   WNW 2013-04     Add a contains method to check if a key exists.
 *                              Changed find to only be private; should be okay.
 * 2013-08-10   WNW             Added support fot iterating the DataSet.
 * 2013-08-11   WNW             Added support fot getting double directly.
 * 2013-08-14   WNW             put(DataSet) handles null value.
 * 2014-06-04	WNW             Remove extra braces from toString()
 * 2014-10-10	WNW -           Redo the equals and hash code for DataSet
 * 2015-03-05	WNW 15-03       Add the concept of an array.
 *                              The .put methods return the DataSet to allow chaining.
 *                              Add ARRAY and LONG types.
 * 2015-03-25	WNW 15-03       The great refactoring.
 * 2015-04-22	WNW             More refactoring
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 * 2016-01-28   WNW 16-01       Add method getType(String)
 * 2016-08-15   WNW 16-08       Change getObject to getObject
 *                              and getValueObject to getObject
 * 2016-08-20   WNW 16-08       add printFormatted method to DataSet
 * 2016-09-09   WNW 16-09       JavaDoc update
 *================================================================================
 */
package lexa.core.data;

import java.io.PrintStream;
import java.util.Date;

/**
 *
 * @author william
 */
public interface DataSet
		extends Iterable<DataItem>
{
	/**
	 * Checks if the {@link DataSet} contains a named key.
	 * <p>
	 * Checks each {@link DataItem} and sees if its key is the same as the named key.
	 * @param key   A key for a {@link DataItem}.
	 * @return      {@code true} if a {@link DataItem} exists with the named key,
	 *              otherwise {@code false}.
	 */
	public boolean contains(String key);

	/**
	 * Get a {@link DataItem} from the list for the supplied key.
	 * @param key The key for the {@link DataItem}.
	 * @return A {@link DataItem} if it exists, otherwise {@code null}.
	 */
	public DataItem get(String key);

	/**
	 * Get a {@link DataItem} from the list for the supplied index.
	 *
	 * @param   index
	 *          the index for the {@link DataItem}.
	 * @return  A {@link DataItem} if it exists,
	 *          otherwise {@code null}.
	 */
	public DataItem get(int index);

	/**
	 * Get a {@link ValueArray} from the list for the supplied key.
	 * @param key The key for the {@link DataItem}.
	 * @return If the item exists and is a {@link ValueArray} then the
	 * item's value, otherwise {@code null}.
	 */
	public ValueArray getArray(String key);

	/**
	 * Get a {@link Boolean} from the list for the supplied key.
	 * @param key The key for the {@link DataItem}.
	 * @return If the item exists and is a {@link String} then the
	 * item's value, otherwise {@code null}.
	 */
	public Boolean getBoolean(String key);

	/**
	 * Get a {@link DataSet} from the list for the supplied key.
	 * @param key The key for the {@link DataItem}.
	 * @return If the item exists and is a {@link DataSet} then the
	 * item's value, otherwise {@code null}.
	 */
	public DataSet getDataSet(String key);

	/**
	 * Get a {@link Date} from the list for the supplied key.
	 * @param key The key for the {@link Date}.
	 * @return If the item exists and is a {@link Date} then the
	 * item's value, otherwise {@code null}.
	 */
	public Date getDate(String key);

	/**
	 * Get a {@link Double} from the list for the supplied key.
	 * @param   key
	 *          the key for the {@link Double}.
	 * @return  If the item exists and is a {@link Double}
	 *          then the item's value,
	 *          otherwise {@code null}.
	 */
	public Double getDouble(String key);

	/**
	 * Get an {@link Integer} from the list for the supplied key.
	 * @param key The key for the {@link DataItem}.
	 * @return If the item exists and is an {@link Integer} then the
	 * item's value, otherwise {@code null}.
	 */
	public Integer getInteger(String key);

	/**
	 * Get a {@link Long} from the list for the supplied key.
	 * @param key The key for the {@link DataItem}.
	 * @return If the item exists and is a {@link Long} then the
	 * item's value, otherwise {@code null}.
	 */
	public Long getLong(String key);

	/**
	 * Get a {@link String} from the list for the supplied key.
	 * @param key The key for the {@link DataItem}.
	 * @return If the item exists and is a {@link String} then the
	 * item's value, otherwise {@code null}.
	 */
	public String getString(String key);

	/**
	 * Get the type of an item from the list for the supplied key.
	 * @param key The key for the {@link Object}.
	 * @return If the item exists then the
	 * item's type, otherwise {@link ValueType#NULL}.
	 */
	public ValueType getType(String key);

    /**
	 * Get an {@link Object} from the list for the supplied key.
	 * @param key The key for the {@link Object}.
	 * @return If the item exists then the
	 * item's value, otherwise {@code null}.
	 */
	public Object getObject(String key);

    /**
	 * Get an {@link Object} from the list for the supplied key.
	 * @param key The key for the {@link Object}.
	 * @return If the item exists then the
	 * item's value, otherwise {@code null}.
	 */
	public Value getValue(String key);

    /**
	 * Returns true if this contains no elements.
	 * @return true if this contains no elements
	 */
	public boolean isEmpty();


	/**
	 * Get a {@link Value} from anywhere in the hierarchical list.
     * <br>
     * This allows safe access to a child item without checking that the parent
     * {@link DataSet} exists.  The syntax {@code ds.item("parent.child")} will
     * check for the dataset {@code parent} and if it exists return the item
     * {@code child}.  If a parent does not exist it returns a {@code null} value.
     * To access an item in an array use :n on the fields name to retrieve the
     * nth item in the array.
     *
	 * @param key The dot separated hierarchical key for the {@link DataItem}.
	 * @return The {@link Value} if it exists, otherwise {@code null}.
	 */
	public Value item(String key);

    /**
	 * Get the list of keys.
	 *
	 * @return An array containing all the keys.
	 */
	public String[] keys();

    /**
     * Output the dataset to a stream in a readable form.
     * @param out the stream to output data to.
     */
    public void printFormatted(PrintStream out);

	/**
	 * Put the supplied item into the {@link DataSet}.
	 * <p>
	 * If the item already exists it is overwritten.
	 *
	 * @param item A {@link DataItem} to add.
	 * @return  the {@link DataSet} the item was added to.
	 */
	public DataSet put(DataItem item);

	/**
	 * Put the supplied object into the {@link DataSet}
	 * using the supplied key.
	 * <p>
	 * If the item already exists it is overwritten.
	 *
	 * @param key The key name for the item
	 * @param value The object value to add.
	 * @return  the {@link DataSet} the item was added to.
	 */
	public DataSet put(String key, Object value);

	/**
	 * Put the contents of another {@link DataSet} into this one.
	 * <p>
	 * Any items in the new data set that have a key that matches another item
	 * will overwrite the existing item.
	 *
	 * @param data The data to be added.
	 * @return  the {@link DataSet} the item was added to.
	 */
	public DataSet put(DataSet data);

	/**
	 * Removes the specified element from this {@link DataSet}.
	 * <p>
	 * Shifts any subsequent elements to the left (subtracts one from their indices).
	 *
	 * @param key   the key to the item to remove
	 *
	 * @return the element that was removed
	 */
	public DataItem remove(String key);

	/**
	 * Get the size of the {@link DataSet}.
	 *
	 * @return the number of {@link DataItem} objects in the {@link DataSet}
	 */
	public int size();
}
