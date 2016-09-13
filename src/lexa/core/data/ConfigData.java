/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ConfigData.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2012
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 28-FEB-13    WNW 2013-03     add getItem(String) so that config can
 *                              include native non-string values.
 * 2013-08-10   WNW             Added getAll() to extract all config from a block.
 *                              This allows data to be loaded via config.
 * 2013-08-20   WNW             If all config isn't read return a list of unread 
 *                              items.
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 * 2016-08-13   WNW 16-08       Provide a method to read the current path. 
 * 2016-08-13   WNW 2016-08     Fix use of DatatException path and key.
 * 2016-08-14   WNW 2016-08     Add getOptionalItem(String,Object).
 * 2016-09-01   WNW 2016-09     Class deprecated in favour of 
 *                              lexa.core.data.config package.
 *================================================================================
 */
package lexa.core.data;

import java.util.HashSet;
import java.util.Set;
import lexa.core.data.exception.DataException;

/**
 * A container for a {@link SimpleDataSet} as configuration.
 * <br>
 * This is used for loading and parsing configuration items. Once read, the 
 * object should be closed to ensure that all items have been read.
 * <br>
 * This class is <b>deprecated</b> and developers should use
 * {@link lexa.core.data.config.ConfigDataSet} instead.
 * @author William
 * @since 2012-07
 */
@Deprecated
public class ConfigData
{
    private final static String ROOT_NAME = "[root]";
	/** Data containing all the configuration items. */
	private final DataSet data;
	/** The set of processed keys */
	private final Set<String> read;
	/** The parent containing this branch. */
	private ConfigData parent;
	/** The current path. */
	private final String path;
	/** The name of the block. */
	private final String block;
	/** Indicates if the block is open or not */
	private boolean isOpen;

	/**
	 * Create a new configuration.
	 * <p>The ConfigData will contain all the elements in the data set. The current path of the configuration is set to
	 * {@code root}.
	 *
	 * @param	data 
	 *			a {@code DateSet} containing the configuration data.
	 *
	 * @throws	DataException The {@code data} is null or empty.
	 */
	public ConfigData(DataSet data)
			throws DataException
	{
		this(data, ConfigData.ROOT_NAME);
	}

	/**
	 * Create a new configuration.
	 * <p>The ConfigData will contain all the elements in the data set.
	 *
	 * @param data a {@code DateSet} containing the configuration data.
	 * @param path the path for the configuration.
	 *
	 * @throws DataException The {@code path} is null or empty, or the {@code data} is null or empty.
	 */
	public ConfigData(DataSet data, String path)
			throws DataException
	{
		this(data, null, path);
	}

	/**
	 * Create a new configuration.
	 * <p>
	 * The ConfigData will contain all the elements in the data set.
	 *
	 * @param data A {@code DateSet} containing the configuration data.
	 * @param parent The parent that contained the configuration.
	 * @param block The block within the parent where the configuration comes from
	 *
	 * @throws DataException The {@code block} is null or empty, or the {@code data} is null or empty.
	 */
	private ConfigData(DataSet data, ConfigData parent, String block)
			throws DataException
	{
		// are the arguments valid?
		if (block == null || "".equals(block))
		{
			if (parent == null)
			{
				throw new DataException("No path supplied");
			}
			else
			{
				throw new DataException("No path supplied", parent.path);
			}
		}

		if (data == null || data.isEmpty())
		{
			if (parent == null)
			{
				throw new DataException("No data in configuration", block);
			}
			else
			{
				throw new DataException("No data in configuration", parent.path, block);
			}
		}

		// populate the config
		this.parent = parent;
		this.block = block;
		if (this.parent == null ||
                ConfigData.ROOT_NAME.equals(this.parent.path))
		{
			this.path = block;
		}
		else
		{
			this.path = this.parent.path + "." + this.block;
		}
		// clone the data to prevent
		this.data = new SimpleDataSet(data);
		this.read = new HashSet();
		this.isOpen = true;
	}

	/**
	 * Close the configuration after reading.
	 *
	 * @throws DataException All the configuration items have not been read.
	 */
	public void close()
			throws DataException
	{
		if (!this.isConfigRead())
		{
			StringBuilder unread = new StringBuilder("All config data not read");
			for (DataItem item
					: this.data)
			{
				if (!this.read.contains(item.getKey()))
				{
					unread.append(", ")
							.append(item.getKey());
				}
			}
			throw new DataException(unread.toString(), this.path);
		}
		if (this.parent != null)
		{
			this.parent.close(this.block);
			this.parent = null;
		}
	}

	/**
	 * Close a child block of the configuration.
	 *
	 * @param key The key to the block.
	 *
	 * @throws DataException The block has already been closed.
	 */
	private void close(String key)
			throws DataException
	{
		if (this.data.getDataSet(key) == null)
		{
			throw new DataException("Cannot close block", this.path, key);
		}
		this.read.add(key);
	}

	/**
	 * Checks if the {@link ConfigData} contains a named key.
	 * <p>
	 * Checks each {@link SimpleDataItem} and sees if its key is the same as the named key.
	 * @param key A key for a {@link SimpleDataItem}.
	 * @return {@code true} if a {@link SimpleDataItem} exists with the named key, otherwise {@code false}.
	 */
	public boolean contains(String key)
	{
		return this.data.contains(key);
	}

	/**
	 * Gets all the data in this config block and all sub-blocks.
	 * <p>This effectively removes all the config and marks it as read.
	 * <p><b>NB</b> Calling this method circumvents all checks on if the data is being used or not. This should only be
	 * used where ALL data is acceptable.
	 *
	 * @return all the data in this config block and all sub-blocks.
	 * @throws DataException Unable to iterate the configuration.
	 */
	public DataSet getAll()
			throws DataException
	{
		// can't just send it back as we need to make sure everything is marked as read.
		DataSet out = new SimpleDataSet();
		for (DataItem item
				: this.data)
		{
			if (item.getType()
					.equals(ValueType.DATA_SET))
			{
				ConfigData subBlock = this.getConfigData(item.getKey());
				out.put(item.getKey(), subBlock.getAll());
				subBlock.close();
			}
			else
			{
				out.put(item);
				this.read.add(item.getKey());
			}
		}
		return new SealedDataSet(out);
	}

	/**
	Get a configuration block from the configuration.
	<p>
	Gets the sub-block for the named key.
	
	@param key The key that identifies the block.
	@return The block represented by {@code key}
	@throws DataException The block does not exist within the configuration.
	 */
	public ConfigData getConfigData(String key)
			throws DataException
	{
		DataSet item = this.data.getDataSet(key);
		if (item == null)
		{
			throw new DataException("Missing config block", this.path, key);
		}
		return new ConfigData(item, this, key);
	}

	/**
	 * Get a configuration item from the configuration.
	 * <p>
	 * Gets the {@link SimpleDataItem} for the named key.
	 *
	 * @param key The key that identifies the item.
	 *
	 * @return The block represented by {@code key}
	 *
	 * @throws DataException The block does not exist within the configuration.
	 */
	public DataItem getItem(String key)
			throws DataException
	{
		DataItem item = this.data.get(key);
		if (item == null)
		{
			throw new DataException("Missing item", this.path, key);
		}
		this.read.add(key);
		return item;
	}

	/**
	 * Get an optional item from the configuration.
	 * <p>
	 * Gets the item for the named key, if the item does not exist then 
     * a new {@see DataItem} with {@code null} is returned.
	 *
	 * @param key The key that identifies the item.
	 *
	 * @return The item represented by {@code key} if it exists,
     * otherwise a new {@see DataItem} {@code null}.
	 */
	public DataItem getOptionalItem(String key)
    {
        return getOptionalItem(key, null);
    }
	/**
	 * Get an optional item from the configuration.
	 * <p>
	 * Gets the item for the named key, if the item does not exist then 
     * a new {@see DataItem} with the {@code defaultValue} is returned.
	 *
	 * @param key The key that identifies the item.
	 * @param defaultValue The default value if the item does not exist.
	 *
	 * @return The item represented by {@code key} if it exists,
     * otherwise a new {@see DataItem} with the {@code defaultValue}.
	 */
	public DataItem getOptionalItem(String key, Object defaultValue)
    {
        try
		{
			return this.getItem(key);
		}
		catch (DataException ex)
		{
			// no need as this is an optional item.
			// as it's missing, we just return the default.
			return new SimpleDataItem(key, defaultValue);
		}
    }
	/**
	 * Get an optional string value from the configuration.
	 * <p>
	 * Gets the value for the named key, if the value does not exist then a null is returned.
	 *
	 * @param key The key that identifies the item.
	 *
	 * @return The value of the item represented by {@code key} or {@code null} if it does not exist.
	 */
	public String getOptionalSetting(String key)
	{
		try
		{
			return this.getSetting(key);
		}
		catch (DataException ex)
		{
			// no need as this is an optional item.
			// as it's missing, we just return null.
			return null;
		}
	}

	/**
	 * Get an optional string value from the configuration.
	 * <p>
	 * Gets the value for the named key, if the value does not exist then the {@code defaultValue} is returned.
	 *
	 * @param key The key that identifies the item.
	 * @param defaultValue The default value if the item does not exist.
	 *
	 * @return The value of the item represented by {@code key}, or {@code defaultValue} if it does not exist.
	 */
	public String getOptionalSetting(String key, String defaultValue)
	{
		String item = this.getOptionalSetting(key);
		if (item == null)
		{
			return defaultValue;
		}

		return item;
	}

	/**
	 * Get the path of the configuration block
	 * <p>
	 * Gets the value for path based on the root from the parent.
	 *
	 * @return the path of the configuration block
	 */
	public String getPath()
	{
		return this.path;
	}

    /**
	 * Get a string value from the configuration.
	 * <p>
	 * Gets the value for the named key.
	 *
	 * @param key The key that identifies the item.
	 *
	 * @return The value of the item represented by {@code key}.
	 *
	 * @throws DataException The value does not exist within the configuration.
	 */
	public String getSetting(String key)
			throws DataException
	{
		String item = this.data.getString(key);
		if (item == null)
		{
			throw new DataException("Missing setting", this.path, key);
		}
		this.read.add(key);

		return item;
	}

	/**
	 * indicates if all the elements have been read.
	 *
	 * @return true if all of the elements have been read.
	 */
	public boolean isConfigRead()
	{
		return (this.data.size() == this.read.size());
	}

	/**
	 * Get the list of keys for the configuration block.
	 *
	 * @return An array containing all the keys.
	 *
	 * @throws DataException The configuration block is empty.
	 */
	public String[] keys()
			throws DataException
	{
		return this.data.keys();
	}
}
