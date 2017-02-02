/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ConfigDataItem.java
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

import lexa.core.data.BaseDataItem;
import lexa.core.data.DataItem;
import lexa.core.data.exception.DataException;

/**
 * A Data Item in a config data set.
 * 
 * @author william
 * @since 2016-09
 */
public class ConfigDataItem
        extends BaseDataItem
        implements ConfigObject
{
    private final String path;
    private final ConfigValue value;

    /**
     * Create a configuration data item.
     * @param path
     * @param item
     */
    ConfigDataItem(String path, DataItem item)
    {
        super(item.getKey());
        this.path = path;
        this.value = new ConfigValue(this.path, super.getKey(), item.getValue());
    }

    @Override
    public void close() throws DataException
    {
        this.value.close();
    }

    @Override
    public boolean isRead()
    {
        return this.value.isRead();
    }

    @Override
    public void reset()
    {
        this.value.reset();
    }

    @Override
    public ConfigValue getValue()
    {
        return this.value;
    }
    
    @Override
    public String getPath()
    {
        if (this.path == null)
        {
            return this.getKey();
        }
        return this.path;
    }
    
}
