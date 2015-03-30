/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.data;

import java.util.Date;

/**
 *
 * @author william
 */
public interface DataSet
		extends Iterable<DataItem>
{

	/**
	Create a clone of the {@link DataSet@return an item by item copy of this.
	 * @return a clone of this data set
	 */
	DataSet clone();

	/**
	 * Checks if the {@link DataSet} contains a named key.
	 * <p>
	 * Checks each {@link SimpleDataItem} and sees if its key is the same as the named key.
	 * @param key   A key for a {@link SimpleDataItem}.
	 * @return      {@code true} if a {@link SimpleDataItem} exists with the named key,
	 *              otherwise {@code false}.
	 */
	boolean contains(String key);

	/**
	 * Get a {@link SimpleDataItem} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return A {@link SimpleDataItem} if it exists, otherwise {@code null}.
	 */
	DataItem get(String key);

	/**
	 * Get a {@link SimpleDataItem} from the list for the supplied index.
	 *
	 * @param   index
	 *          the index for the {@link SimpleDataItem}.
	 * @return  A {@link SimpleDataItem} if it exists,
	 *          otherwise {@code null}.
	 */
	DataItem get(int index);

	/**
	 * Get a {@link ValueArray} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link ValueArray} then the
	 * item's value, otherwise {@code null}.
	 */
	ValueArray getArray(String key);

	/**
	 * Get a {@link Boolean} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link String} then the
	 * item's value, otherwise {@code null}.
	 */
	Boolean getBoolean(String key);

	/**
	 * Get a {@link DataSet} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link DataSet} then the
	 * item's value, otherwise {@code null}.
	 */
	DataSet getDataSet(String key);

	/**
	 * Get a {@link Date} from the list for the supplied key.
	 * @param key The key for the {@link Date}.
	 * @return If the item exists and is a {@link Date} then the
	 * item's value, otherwise {@code null}.
	 */
	Date getDate(String key);

	/**
	 * Get a {@link Double} from the list for the supplied key.
	 * @param   key
	 *          the key for the {@link Double}.
	 * @return  If the item exists and is a {@link Double}
	 *          then the item's value,
	 *          otherwise {@code null}.
	 */
	Double getDouble(String key);

	/**
	 * Get an {@link Integer} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is an {@link Integer} then the
	 * item's value, otherwise {@code null}.
	 */
	Integer getInteger(String key);

	/**
	 * Get a {@link Long} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link Long} then the
	 * item's value, otherwise {@code null}.
	 */
	Long getLong(String key);

	/**
	 * Get a {@link String} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link String} then the
	 * item's value, otherwise {@code null}.
	 */
	String getString(String key);

	/**
	 * Get an {@link Object} from the list for the supplied key.
	 * @param key The key for the {@link Object}.
	 * @return If the item exists then the
	 * item's value, otherwise {@code null}.
	 */
	Object getValue(String key);

	/**
	 * Returns true if this contains no elements.
	 * @return true if this contains no elements
	 */
	boolean isEmpty();

	/**
	 * Get the list of keys.
	 *
	 * @return An array containing all the keys.
	 */
	String[] keys();

	/**
	 * Put the supplied item into the {@link DataSet}.
	 * <p>
	 * If the item already exists it is overwritten.
	 *
	 * @param item A {@link SimpleDataItem} to add.
	 * @return  the {@link DataSet} the item was added to.
	 */
	DataSet put(DataItem item);

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
	DataSet put(String key, Object value);

	/**
	 * Put the contents of another {@link DataSet} into this one.
	 * <p>
	 * Any items in the new data set that have a key that matches another item
	 * will overwrite the existing item.
	 *
	 * @param data The data to be added.
	 * @return  the {@link DataSet} the item was added to.
	 */
	DataSet put(DataSet data);

	/**
	 * Removes the specified element from this {@link DataSet}.
	 * <p>
	 * Shifts any subsequent elements to the left (subtracts one from their indices).
	 *
	 * @param key   the key to the item to remove
	 *
	 * @return the element that was removed
	 */
	DataItem remove(String key);

	/**
	 * Get the size of the {@link DataSet}.
	 *
	 * @return the number of {@link SimpleDataItem} objects in the {@link DataSet}
	 */
	int size();
	
}
