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
import lexa.core.data.DataValue;
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

    /**
     * Create a configuration data item.
     * @param path
     * @param item
     */
    ConfigDataItem(ConfigObject parent, DataItem item)
    {
        //super(parent.configFactory().getChild(item.getKey()),item.getKey(), null);
        this(
                parent.configFactory(),
                item.getKey(),
                item.getValue()
        );
    }
    ConfigDataItem(ConfigFactory factory, String key, DataValue value)
    {
        super(factory.getChild(key),key,value);
    }
    @Override
    public void close() throws DataException
    {
        ((ConfigDataValue)super.getValue()).close();
    }

    @Override
    public ConfigFactory configFactory()
    {
        return (ConfigFactory)this.factory();
    }

    @Override
    public boolean isRead()
    {
        return ((ConfigDataValue)super.getValue()).isRead();
    }

    @Override
    public void reset()
    {
        ((ConfigDataValue)super.getValue()).reset();
    }

    @Override
    public String getPath()
    {
        return this.configFactory().getPath();
    }
}
