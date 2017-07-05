/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ConfigDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *================================================================================
 */
package lexa.core.data.config;

import java.util.HashSet;
import java.util.Set;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.ArrayDataItem;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.DataType;
import lexa.core.data.exception.DataException;

/**
 * A {@link DataSet} for storing configuration data.
 * <br>
 * This is used for loading and parsing configuration items. Once read, the
 * object should be closed to ensure that all items have been read.
 * <br>
 * An extension of a {@link ArrayDataSet}. This will behave in all
 * respects like a data set with the added trace of path within the whole set
 * and checking that all the data has been read when closing.
 *
 * @author  William
 * @since   2016-09
 */
public class ConfigDataSet
		extends ArrayDataSet
        implements ConfigObject
{
    private final Set<String> invalidGets;
    private boolean read;
	/**
	 * Constructor that wraps the configuration data.
	 * <br>
	 * No modification can be made to this data set, though it can be cloned.
	 * The cloned data set can be modified.
	 *
	 * @param   data
     *          a {@link DataSet} containing the configuration.
	 */
	public ConfigDataSet(DataSet data)
	{
		this(ConfigFactory.factory,data);
	}

    ConfigDataSet(ConfigObject parent, DataSet data)
	{
        this(
            parent == null ?
                    ConfigFactory.factory :
                    parent.configFactory(),
            data
        );
    }

    ConfigDataSet(ConfigFactory factory, DataSet data)
    {
        super(factory);

        this.invalidGets = new HashSet<>();
        if (data == null)
        {
            this.read = true;
            return;
        }

        for (DataItem item : data)
        {
            super.put(this.factory().convert(item));
        }
        this.read = false;
	}

    @Override
    public ConfigFactory configFactory()
    {
        return (ConfigFactory)this.factory();
    }

	/**
	 * Put the supplied item into the {@link ConfigDataSet}.
	 *
	 * <p>Causes a reset of all reads.
	 *
	 * @param   item
	 *          a {@link ArrayDataItem} to add.
	 * @return  the {@link ArrayDataSet} the item was added to.
	 */
	@Override
	public synchronized ConfigDataSet put(DataItem item)
	{
		super.put(item);
        ((ConfigDataItem)super.get(item.getKey())).reset();
        return this;
	}

	/**
	 * Removes the specified element from this {@link ArrayDataSet}.
	 *
	 * <p>Throws an {@link UnsupportedOperationException} if called.
	 *
	 * @param   key
	 *          the key to the item to remove
	 *
	 * @return  no return is made, this method always throws an exception.
	 */
	@Override
	public synchronized DataItem remove(String key)
	{
        this.invalidGets.remove(key);
        return super.remove(key);
	}

    @Override
    public void close() throws DataException
    {
        if (!this.isRead())
        {
			StringBuilder cannotClose = new StringBuilder("Cannot close config");
			for (DataItem notRead : this)
			{
				if (!((ConfigObject)notRead).isRead())
				{
					cannotClose.append(", ")
							.append(notRead.getKey())
                            .append(" unread");
				}
			}
            for (String badGet : this.invalidGets)
            {
                cannotClose.append(", ")
                        .append(badGet)
                        .append(" not set");
            }
			throw new DataException(cannotClose.toString(), this.getPath());
        }
    }

    @Override
    public void reset()
    {
        for (DataItem item : this)
        {
            ((ConfigObject)item).reset();
        }
        this.read = false;
    }

    @Override
    public String getPath()
    {
        return this.configFactory().getPath();
    }

    @Override
    public synchronized ConfigDataItem get(int index)
    {
        if (index < 0 || index >= this.size())
        {
            this.invalidGets.add("#" + index);
        }
        return (ConfigDataItem)super.get(index);
    }

    @Override
    public synchronized ConfigDataItem get(String key)
    {
        ConfigDataItem item = (ConfigDataItem)super.get(key);
        if (item == null)
        {
            this.invalidGets.add(key);
        }
        return item;
    }

    /**
     * Get a {@link ConfigDataItem} from the list for the supplied key.
     * Either get the configured setting or the default value if missing.
     *
     * @param key The key for the ArrayDataItem.
     * @param defaultValue the default value
     * @return The corresponding {@link ConfigDataItem} if it exists,
     * otherwise a new item containing with the given default.
     */
    public synchronized ConfigDataItem get(String key, Object defaultValue)
    {
        // avoid the error later:
        if (this.contains(key))
            return this.get(key);
        return new ConfigDataItem(this, new ArrayDataItem(key, defaultValue));
    }

    @Override
    public synchronized ConfigDataValue getValue(String key)
    {
        return (ConfigDataValue)super.getValue(key);
    }

    @Override
    public synchronized ConfigDataSet getDataSet(String key)
    {
        return (ConfigDataSet)super.getDataSet(key);
    }

    @Override
    public synchronized ConfigDataArray getArray(String key)
    {
        return (ConfigDataArray)super.getArray(key);
    }

    @Override
    public boolean isRead()
    {
        if (!this.read)
        {
            boolean checkRead = true;
            if (!this.invalidGets.isEmpty())
            {
                checkRead = false;
            }

            for (DataItem item : this)
            {
                if (!((ConfigObject)item).isRead())
                {
                    checkRead = false;
                    break;
                }
            }
            this.read = checkRead;
        }
        return this.read;
    }

    /**
     * Validate the data types for listed items in the data set.
     * @param   args
     *          a list of items in pairs, String then DataTtype
     * @throws  DataException
     *          when the type does not match
     */
    public void validateType(Object ... args)
            throws DataException
    {
        if (args.length % 2 > 0)
        {
            throw new DataException(
                    "Arguments not a list of pairs",
                    this.getPath()
            );
        }
        for (int a = 0;
             a < args.length;
             a = a + 2)
        {
            if (!String.class.isAssignableFrom(args[a].getClass()) ||
                    !DataType.class.isAssignableFrom(args[a+1].getClass()))
            {
                throw new DataException(
                        "Parameters are not String,DataType",
                        this.getPath()
                );
            }
            String name = (String)args[a];
            DataType type = (DataType)args[a+1];
            ConfigDataValue value =  this.getValue(name);
            if (value == null)
            {
                throw new DataException(
                        "Item doesn not exist in the data set",
                        this.getPath(), name);
            }
            value.validateType(type);
        }
    }
}
