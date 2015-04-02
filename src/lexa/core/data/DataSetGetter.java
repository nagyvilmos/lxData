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
public abstract class DataSetGetter
		implements DataSet
{
		// I really do not know why we need this, but.
		@Override
		public abstract DataSet clone();

	/**
	 * Get a {@link ValueArray} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link ValueArray} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized ValueArray getArray(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getArray();
	}
	/**
	 * Get a {@link Boolean} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link String} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Boolean getBoolean(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getBoolean();
	}

	/**
	 * Get a {@link SimpleDataSet} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link SimpleDataSet} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized DataSet getDataSet(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getDataSet();
	}

	/**
	 * Get a {@link Date} from the list for the supplied key.
	 * @param key The key for the {@link Date}.
	 * @return If the item exists and is a {@link Date} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Date getDate(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getDate();
	}

	/**
	 * Get a {@link Double} from the list for the supplied key.
	 * @param   key
	 *          the key for the {@link Double}.
	 * @return  If the item exists and is a {@link Double}
	 *          then the item's value,
	 *          otherwise {@code null}.
	 */
	@Override
	public synchronized Double getDouble(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getDouble();
	}

	/**
	 * Get an {@link Integer} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is an {@link Integer} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Integer getInteger(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getInteger();
	}

	/**
	 * Get a {@link Long} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link Long} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Long getLong(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getLong();
	}

	/**
	 * Get a {@link String} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link String} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized String getString(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getString();
	}

	/**
	 * Get an {@link Object} from the list for the supplied key.
	 * @param key The key for the {@link Object}.
	 * @return If the item exists then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Object getValue(String key)
	{
		DataItem item = this.get(key);
		if (item == null)
		{
			return null;
		}
		return item.getValue();
	}

}
