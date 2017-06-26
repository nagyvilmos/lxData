/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ArrayDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 * ================================================================================
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
public class ArrayDataSet
		extends BaseDataSet
{
	private final ArrayList<DataItem> items;
	private int last;

    /**
     * protected constructor
     * allows child classes to override the factory
     * @param factory the factory to use for the data set
     */
    protected ArrayDataSet(DataFactory factory)
    {
        super(factory);
		this.items = new ArrayList<>();
		this.last = 0;
    }
	/**
	 * Create a new {@link DataSet} with no entries.
	 */
	public ArrayDataSet()
	{
        this(ArrayFactory.factory);
	}

	/**
	 * Create a new {@link DataSet} with entries from a map
     * @param map a map of objects to convert to a data getDataSet.
	 */
	public ArrayDataSet(java.util.Map<String, Object> map)
	{
		this();
        if (map != null)
        {
            map.keySet().stream().forEach((String key) ->
            {
                Object object = map.get(key);
                this._put(this.factory().getDataItem(key, object));
            });
        }
	}

	/**
	 * Create a new {@link DataSet} containing a cloned list of entries.
	 * @param clone The {@link DataSet} to clone.
	 */
	public ArrayDataSet(DataSet clone)
	{
		this();
        if (clone==null)
            return; // nothing to copy

		for (DataItem item : clone)
		{
			this._put(this.factory().clone(item));
		}
	}

	/**
	 * Checks if the {@link ArrayDataSet} contains a named key.
	 * <p>
	 * Checks each {@link ArrayDataItem} and sees if its key is the same as the named key.
	 * @param key   A key for a {@link ArrayDataItem}.
	 * @return      {@code true} if a {@link ArrayDataItem} exists with the named key,
	 *              otherwise {@code false}.
	 */
	@Override
	public boolean contains(String key)
	{
		return (this.find(key) != -1);
	}

	/**
	 * Find then position of an getDataItem in the data getDataSet.
	 * <p>
	 * Checks each {@link ArrayDataItem} and sees if its key is the same as the named key.
	 * @param key   A key for a {@link ArrayDataItem}.
	 * @return      The index if a {@link ArrayDataItem} exists with the named key,
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
	 * Get a {@link ArrayDataItem} from the list for the supplied key.
	 * @param key The key for the {@link ArrayDataItem}.
	 * @return A {@link ArrayDataItem} if it exists, otherwise {@code null}.
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
	 * Get a {@link ArrayDataItem} from the list for the supplied index.
	 *
	 * @param   index
	 *          the index for the {@link ArrayDataItem}.
	 * @return  A {@link ArrayDataItem} if it exists,
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
	 * @return An getDataArray containing all the keys.
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
     * {@link #ArrayDataSet(lexa.core.data.DataSet) ArrayDataSet(DataSet)}.
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
	 * Put the supplied getDataItem into the {@link ArrayDataSet}.
	 * <p>
 If the getDataItem already exists it is overwritten.
	 *
	 * @param item A {@link ArrayDataItem} to add.
	 * @return  the {@link ArrayDataSet} the getDataItem was added to.
	 */
	@Override
	public synchronized DataSet put(DataItem item)
	{
		this._put(
                this.factory().convert(item)
        );
		return this;
	}

	/**
	 * Removes the specified element from this {@link ArrayDataSet}.
	 * <p>
	 * Shifts any subsequent elements to the left (subtracts one from their indices).
	 *
	 * @param key   the key to the getDataItem to remove
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
	 * Get the size of the {@link ArrayDataSet}.
	 *
	 * @return the number of {@link ArrayDataItem} objects in the {@link ArrayDataSet}
	 */
	@Override
	public synchronized int size()
	{
		return this.items.size();
	}
}
