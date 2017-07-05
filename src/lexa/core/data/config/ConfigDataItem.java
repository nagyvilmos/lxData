/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * ConfigDataItem.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *==============================================================================
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
     * @param parent
     *          the parent to this item
     * @param item
     *          the content for this item
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
    public ConfigDataArray getArray()
    {
        return (ConfigDataArray)super.getArray();
    }

    @Override
    public ConfigDataSet getDataSet()
    {
        return (ConfigDataSet)super.getDataSet();
    }

    @Override
    public String getPath()
    {
        return this.configFactory().getPath();
    }

    @Override
    public ConfigDataValue getValue()
    {
        return (ConfigDataValue)super.getValue();
    }
}
