/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * HashDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ----------   --- ----------  --------------------------------------------------
 *================================================================================
 */
package lexa.core.data;

import java.util.*;

/**
 * A data set using hashed keys to access the content
 * A {@link DataSet} that utilises a {@link HashMap} for storing the items.
 * This class should be used in favour of the {@link SimpleDataSet} when there
 * are a large number of entries or the entries will be accessed randomly and by
 * key.
 * <em><b>NB</b>This performance of this class is poor when using iterators and
 * iterator based functions</em>
 * @author William
 * @since 2016-09
 */
public class HashDataSet
		extends BaseDataSet
{

	private final Map<String, DataItem> items;

	/**
	 * Create a new {@link HashDataSet} with no entries.
	 */
	public HashDataSet()
	{
        super(HashFactory.factory);
		this.items = new HashMap();
	}

	/**
	 * Create a new {@link HashDataSet} containing a cloned list of entries.
	 * @param clone The {@link DataSet} to clone.
	 */
	public HashDataSet(DataSet clone)
	{
		this();
        if (clone==null)
            return; // nothing to copy

        // assume we're receiving another Hash set so clone by key:
        String[] keys = clone.keys();
        for (String key : keys)
        {
            this._put(new HashDataItem(clone.get(key)));
        }
	}

	@Override
	public boolean contains(String key)
	{
		return this.items.containsKey(key);
	}

	@Override
	public synchronized DataItem get(String key)
	{
		return this.items.get(key);
	}

	@Override
	public synchronized DataItem get(int index)
	{
        if (index < 0 ||
                index >= this.size())
        {
            return null;
        }
        // good!!
        return this.items.get(this.keys()[index]);
    }

	@Override
	public synchronized boolean isEmpty()
	{
		return (this.items.isEmpty());
	}

	@Override
	public synchronized String[] keys()
	{
        return this.items.keySet().toArray(new String[this.size()]);
	}

	/**
	 * Implementation for {@link #put(DataItem)}.
	 * <p>
	 * This is {@code private} to stop any overrides and is to be called only
	 * from this {@link #put(lexa.core.data.DataItem) put(DataItem)} and the
     * clone constructor
     * {@link #HashDataSet(lexa.core.data.DataSet) HashDataSet(DataSet)}.
	 * @param item A {@link DataItem} to add.
	 */
	private synchronized void _put(DataItem item)
	{
		this.items.put(item.getKey(), item);
	}

	@Override
	public synchronized DataSet put(DataItem item)
	{
		this._put(item);
		return this;
	}

	@Override
	public synchronized DataSet put(String key, Object value)
	{
		return this.put(new HashDataItem(key, value));
	}

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

	@Override
	public synchronized DataItem remove(String key)
	{
		return this.items.remove(key);
	}

	@Override
	public synchronized int size()
	{
		return this.items.size();
	}
}
