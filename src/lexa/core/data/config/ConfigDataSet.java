/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ConfigDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-09-01   WNW 16-01       Create seperate package and classes for config
 * 2016-09-14   WNW 16-09       Add path to all config objects
 *================================================================================
 */
package lexa.core.data.config;

import java.util.HashSet;
import java.util.Set;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataItem;
import lexa.core.data.SimpleDataSet;
import lexa.core.data.exception.DataException;

/**
 * A {@link DataSet} for storing configuration data.
 * <br>
 * This is used for loading and parsing configuration items. Once read, the
 * object should be closed to ensure that all items have been read.
 * <br>
 * A read only extension of a {@link SimpleDataSet}. This will behave in all
 * respects like a data set with the added trace of path within the whole set
 * and checking that all the data has been read when closing.
 * <br>
 * This class superceded the {@link Deprecated} class {@link lexa.core.data.ConfigData}.
 *
 * @author  William
 * @since   2016-09
 */
public class ConfigDataSet
		extends SimpleDataSet
        implements ConfigObject
{
    private final static String ROOT_NAME = "[root]";
    private final String path;
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
		this(null,data);
	}

    ConfigDataSet(ConfigObject parent, DataSet data)
	{
        this.path = parent == null ? null : parent.getPath();

        this.invalidGets = new HashSet();
        if (data == null)
        {
            this.read = true;
            return;
        }

        for (DataItem item : data)
        {
            super.put(new ConfigDataItem(path, item));
        }
        this.read = false;
	}

	/**
	 * Put the supplied item into the {@link ConfigDataSet}.
	 *
	 * <p>Throws an {@link UnsupportedOperationException} if called.
	 *
	 * @param   item
	 *          a {@link SimpleDataItem} to add.
	 * @return  the {@link SimpleDataSet} the item was added to.
	 */
	@Override
	public synchronized DataSet put(DataItem item)
	{
		throw new UnsupportedOperationException(
				"Cannot change the content of a config data set");
	}

	/**
	 * Removes the specified element from this {@link SimpleDataSet}.
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
		throw new UnsupportedOperationException(
				"Cannot change the content of a config data set");

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
			throw new DataException(cannotClose.toString(), this.path);
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
        if (this.path == null)
        {
            return ConfigDataSet.ROOT_NAME;
        }
        return this.path;
    }
    @Override
    public synchronized ConfigDataItem get(int index)
    {
        if (index < 0 || index >= this.size())
        {
            this.invalidGets.add("#" + index);
        }
        return (ConfigDataItem)super.get(index); //To change body of generated methods, choose Tools | Templates.
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
     * @param key The key for the SimpleDataItem.
     * @param defaultValue the default value
     * @return The corresponding {@link ConfigDataItem} if it exists,
     * otherwise a new item containing with the given default.
     */
    public synchronized ConfigDataItem get(String key, Object defaultValue)
    {
        // avoid the error later:
        if (this.contains(key))
            return this.get(key);
        return new ConfigDataItem(this.path, new SimpleDataItem(key, defaultValue));
    }

    @Override
    public synchronized ConfigValue getValue(String key)
    {
        return (ConfigValue)super.getValue(key); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized ConfigDataSet getDataSet(String key)
    {
        return (ConfigDataSet)super.getDataSet(key); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized ConfigValueArray getArray(String key)
    {
        return (ConfigValueArray)super.getArray(key); //To change body of generated methods, choose Tools | Templates.
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
                }
            }
            this.read = checkRead;
        }
        return this.read;
    }
}
