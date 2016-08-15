/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * SimpleDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ----------   --- ----------  --------------------------------------------------
 * 2015-03-25	WNW 15-03       The great refactoring.
 * 2015-04-22	WNW             More refactoring
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 * 2016-01-28   WNW 16-01       Update javadoc.
 * 2016-01-28   WNW 16-01       Move toString() from SimpleDataSet to DataSetGetter
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
		extends DataSetBase
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
			this._put(new SimpleDataItem(item));
		}
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
	 * from this {@link #put(lexa.core.data.DataItem) put(DataItem)} and the
     * clone constructor
     * {@link #SimpleDataSet(lexa.core.data.DataSet) SimpleDataSet(DataSet)}.
	 * @param item A {@link DataItem} to add.
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
}
