/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * BaseDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: April 2015
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ----------   --- ----------  --------------------------------------------------
 * 2015-04-22	WNW				More refactoring
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 * 2016-01-28   WNW 16-01       Add method getType(String)
 * 2016-01-28   WNW 16-01       Move toString() from SimpleDataSet to DataSetGetter
 *                              Fix a bug in toString() for 0 item set
 * 2016-02-09   WNW             Change base abstract classes from *Getter to *Base
 * 2016-08-15   WNW 16-08       Change getObject to getObject
 *                              and getValueObject to getObject
 * 2016-08-20   WNW 16-08       add printFormatted method to DataSet
 * 2016-09-09   WNW 16-09       Change base abstract classes from *Base to Base*
 *================================================================================
 */
package lexa.core.data;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;
import lexa.core.data.io.DataWriter;

/**
 * Provide the base methods for a data set.
 * <p>
 * This provides the getters for the specific types supported.
 * It does not implement the basic {@link DataSet#get(java.lang.String) get} method
 * @author william
 */
public abstract class BaseDataSet
		implements DataSet
{
    private final DataFactory factory;

    protected BaseDataSet(DataFactory factory)
    {
        this.factory = factory;
    }
    /**
	 * Get a {@link DataArray} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link DataArray} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized DataArray getArray(String key)
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
	 * Get the type of an item from the list for the supplied key.
	 * @param key The key for the {@link Object}.
	 * @return If the item exists then the
	 * item's type, otherwise {@link DataType#NULL}.
	 */
	@Override
	public synchronized DataType getType(String key)
	{
		DataItem item = this.get(key);
		if (item == null)
		{
			return DataType.NULL;
		}
		return item.getType();
	}

    /**
	 * Get an {@link Object} from the list for the supplied key.
	 * @param key The key for the {@link Object}.
	 * @return If the item exists then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Object getObject(String key)
	{
		DataItem item = this.get(key);
		if (item == null)
		{
			return null;
		}
		return item.getObject();
	}

    /**
	 * Get an {@link DataValue} from the list for the supplied key.
	 * @param key The key for the {@link DataValue}.
	 * @return If the item exists then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized DataValue getValue(String key)
	{
		DataItem item = this.get(key);
		if (item == null)
		{
			return null;
		}
		return item.getValue();
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
				: this)
		{
			sb.append(item.toString()).append(" ");
		}
        int length = sb.length();
        if (length > 1)
        {
            sb.deleteCharAt(length - 1);
        }
		return sb.append("}")
				.toString();
	}

	/**
	 * Compares an object
	 * @param obj object to compare to
	 * @return {@code true} if the objects are equal, otherwise {@code false}
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !DataSet.class.isAssignableFrom(obj.getClass()))
		{
			return false;
		}
		final DataSet other = (DataSet) obj;

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

    @Override
    public DataValue item(String key)
    {
        int split = key.indexOf('.');
        if (split < 0)
        {
            return this.itemValue(key);
        }
        String parent = key.substring(0,split);
        DataValue value = this.itemValue(parent);

        if (value == null || !value.getType().equals(DataType.DATA_SET))
        {
            return null;
        }
        return value.getDataSet().item(key.substring(split+1));
    }

    private DataValue itemValue(String key)
    {
        int array = key.indexOf(':');
        if (array < 0)
        {
            return this.getValue(key);
        }
        String name = key.substring(0,array);
        return BaseDataSet.itemArray(
                this.getArray(name),
                key.substring(array+1));
    }

    private static DataValue itemArray(DataArray valueArray, String key)
    {
        if (valueArray == null)
        {
            return null;
        }
        int sub = key.indexOf(':');
        if (sub > 0)
        {
            DataValue subValue = BaseDataSet.itemArray(
                    valueArray, key.substring(0,sub));
            if (!subValue.getType().equals(DataType.ARRAY))
            {
                return null;
            }
            return BaseDataSet.itemArray(
                    subValue.getArray(), key.substring(sub+1));
        }
        int index = Integer.parseInt(key);
        if (index < 0 || index >= valueArray.size())
        {
            return null;
        }
        return valueArray.get(index);
    }

    @Override
	public Iterator<DataItem> iterator()
	{
		return new DataSetIterator(this);
	}

    @Override
    public void printFormatted(PrintStream out)
    {
        try
        {
            new DataWriter(out).write(this);
        } catch (IOException ex)
        {
            ex.printStackTrace(out);
        }
    }

	/**
	 * Put the supplied object into the {@link ArrayDataSet}
	 * using the supplied key.
	 * <p>
 If the getDataItem already exists it is overwritten.
	 *
	 * @param key The key name for the getDataItem
	 * @param value The object getDataValue to add.
	 * @return  the {@link ArrayDataSet} the getDataItem was added to.
	 */
	@Override
	public synchronized DataSet put(String key, Object value)
	{
		return this.put(this.factory.getDataItem(key, value));
	}

    /**
	 * Put the contents of another {@link ArrayDataSet} into this one.
	 * <p>
 Any items in the new data getDataSet that have a key that matches another getDataItem
 will overwrite the existing getDataItem.
	 *
	 * @param data The data to be added.
	 * @return  the {@link ArrayDataSet} the getDataItem was added to.
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
}
