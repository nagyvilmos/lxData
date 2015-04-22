/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * SimpleDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2009
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ----------   --- ----------  --------------------------------------------------
 * 2013-04-14   WNW 2013-04     Add a contains method to check if a key exists.
 *                              Changed find to only be private; should be okay.
 * 2013-08-10   WNW             Added support fot iterating the DataSet.
 * 2013-08-11   WNW             Added support fot getting double directly.
 * 2013-08-14   WNW             put(DataSet) handles null value.
 * 2014-06-04	WNW				Remove extra braces from toString()
 * 2014-10-10	WNW -			Redo the equals and hash code for DataSet
 * 2015-03-05	WNW 15-03		Add the concept of an array.
 *								The .put methods return the DataSet to allow chaining
 *								Add ARRAY and LONG types.
 * 2015-03-25	WNW 15-03		The great refactoring.
 * 2015-04-22	WNW				More refactoring
 *================================================================================
 */
package lexa.core.data;

import java.util.ArrayList;

/**
 * Provide a polymorphic map containing disparate data types as values.
 * A {@link DataSet} contains multiple {@link DataItem} objects, keyed by
 * their names.
 * @author William
 * @since 2009-07
 * @see DataItem
 */
public class SimpleDataSet
		extends DataSetGetter
{

	private final ArrayList<DataItem> items;
	private int last;

	/**
	 * Create a new {@link DataSet} with no entries.
	 */
	public SimpleDataSet()
	{
		this.items = new ArrayList();
		this.last = 0;
	}

	/**
	 * Create a new {@link DataSet} containing a cloned list of entries.
	 * @param clone The {@link DataSet} to clone.
	 */
	public SimpleDataSet(DataSet clone)
	{
		this();
		for (DataItem item : clone)
		{
			this._put(item.clone());
		}
	}

	/**
	Create a clone of the {@link SimpleDataSet@return an item by item copy of this.
	*/
	@Override
	@SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDeclaresCloneNotSupported"})
	public DataSet clone()
	{
		return new SimpleDataSet(this);
	}

	/**
	 * Checks if the {@link SimpleDataSet} contains a named key.
	 * <p>
	 * Checks each {@link SimpleDataItem} and sees if its key is the same as the named key.
	 * @param key   A key for a {@link SimpleDataItem}.
	 * @return      {@code true} if a {@link SimpleDataItem} exists with the named key,
	 *              otherwise {@code false}.
	 */
	@Override
	public boolean contains(String key)
	{
		return (this.find(key) != -1);
	}

	/**
	 * Compares an object
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final SimpleDataSet other = (SimpleDataSet) obj;

		// trivial case of different sizes
		if (this.size() != other.size())
		{
			return false;
		}

		// the items could be loaded in a different order but if the content
		// is the same, the two sets are equal.
		for (DataItem item
				: this)
		{
			if (!item.equals(other.get(item.getKey())))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		for (DataItem item : this)
		{
			hash += (17 * item.hashCode());
		}
		return hash;
	}

	/**
	 * Find then position of an item in the data set.
	 * <p>
	 * Checks each {@link SimpleDataItem} and sees if its key is the same as the named key.
	 * @param key   A key for a {@link SimpleDataItem}.
	 * @return      The index if a {@link SimpleDataItem} exists with the named key,
	 *              otherwise {@code -1}.
	 */
	private synchronized int find(String key)
	{
		if (this.items.size() > 0)
		{
			int end;
			if (this.last < this.items.size())
			{
				end = this.last;
			}
			else
			{
				end = 0;
			}
			int index = end;
			do
			{
				if (this.items.get(index)
						.getKey()
						.equals(key))
				{
					this.last = index;
					return index;
				}
				index = (index + 1) % this.items.size();
			} while (index != end);
		}
		return -1;
	}

	/**
	 * Get a {@link SimpleDataItem} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return A {@link SimpleDataItem} if it exists, otherwise {@code null}.
	 */
	@Override
	public synchronized DataItem get(String key)
	{
		int index = this.find(key);
		if (index == -1)
		{
			return null;
		}
		return this.items.get(index);
	}

	/**
	 * Get a {@link SimpleDataItem} from the list for the supplied index.
	 *
	 * @param   index
	 *          the index for the {@link SimpleDataItem}.
	 * @return  A {@link SimpleDataItem} if it exists,
	 *          otherwise {@code null}.
	 */
	@Override
	public synchronized DataItem get(int index)
	{
		if (index < 0 || index >= this.size())
		{
			return null;
		}
		return this.items.get(index);
	}

	/**
	 * Returns true if this contains no elements.
	 * @return true if this contains no elements
	 */
	@Override
	public synchronized boolean isEmpty()
	{
		return (this.items.isEmpty());
	}

	/**
	 * Get the list of keys.
	 *
	 * @return An array containing all the keys.
	 */
	@Override
	public synchronized String[] keys()
	{
		String[] keys = new String[this.items.size()];
		for (int item = 0;
				item < this.items.size();
				item++)
		{
			keys[item] = this.items.get(item)
					.getKey();
		}
		return keys;
	}

	/**
	 * Implementation for {@link #put(DataItem)}.
	 * <p>
	 * This is {@code private} to stop any overrides and is to be called only
	 * from this and the constructor {@link #DataSet(DataSet)}.
	 * @param item A {@link SimpleDataItem} to add.
	 */
	private synchronized void _put(DataItem item)
	{
		if (item == null)
		{
			return;
		}
		int index = this.find(item.getKey());
		if (index == -1)
		{
			this.items.add(item);
		}
		else
		{
			this.items.set(index, item);
		}
	}

	/**
	 * Put the supplied item into the {@link SimpleDataSet}.
	 * <p>
	 * If the item already exists it is overwritten.
	 *
	 * @param item A {@link SimpleDataItem} to add.
	 * @return  the {@link SimpleDataSet} the item was added to.
	 */
	@Override
	public synchronized DataSet put(DataItem item)
	{
		this._put(item);
		return this;
	}

	/**
	 * Put the supplied object into the {@link SimpleDataSet}
	 * using the supplied key.
	 * <p>
	 * If the item already exists it is overwritten.
	 *
	 * @param key The key name for the item
	 * @param value The object value to add.
	 * @return  the {@link SimpleDataSet} the item was added to.
	 */
	@Override
	public synchronized DataSet put(String key, Object value)
	{
		return this.put(new SimpleDataItem(key, value));
	}

	/**
	 * Put the contents of another {@link SimpleDataSet} into this one.
	 * <p>
	 * Any items in the new data set that have a key that matches another item
	 * will overwrite the existing item.
	 *
	 * @param data The data to be added.
	 * @return  the {@link SimpleDataSet} the item was added to.
	 */
	@Override
	public synchronized DataSet put(DataSet data)
	{
		if (data != null)
		{
			for (DataItem item
					: data)
			{
				this.put(item);
			}
		}
		return this;
	}

	/**
	 * Removes the specified element from this {@link SimpleDataSet}.
	 * <p>
	 * Shifts any subsequent elements to the left (subtracts one from their indices).
	 *
	 * @param key   the key to the item to remove
	 *
	 * @return the element that was removed
	 */
	@Override
	public synchronized DataItem remove(String key)
	{
		int index = this.find(key);
		if (index == -1)
		{
			return null;
		}
		return this.items.remove(index);
	}

	/**
	 * Get the size of the {@link SimpleDataSet}.
	 *
	 * @return the number of {@link SimpleDataItem} objects in the {@link SimpleDataSet}
	 */
	@Override
	public synchronized int size()
	{
		return this.items.size();
	}

	/**
	 * Return a string representation of a {@link SimpleDataSet}.
	 * Formatted as a list of all the {@link SimpleDataItem}'s:
	 * <blockquote>
	 * <pre>
	 * {{key}{value} {key}{{key}{value} {key}{value}}}
	 * </pre></blockquote>
	 *
	 * @return A string representation of the object.
	 */
	@Override
	public synchronized String toString()
	{
		StringBuilder sb = new StringBuilder("{");
		for (DataItem item
				: this.items)
		{
			sb.append(item.toString()).append(" ");			
		}
		return sb.deleteCharAt(sb.length()-1).append("}")
				.toString();
	}
}
