/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ConfigValue.java
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

import lexa.core.data.BaseValue;
import lexa.core.data.Value;
import lexa.core.data.ValueType;
import lexa.core.data.exception.DataException;

/**
 *
 * @author william
 */
public class ConfigValue
        extends BaseValue
        implements ConfigObject
{
    private final String path;
    private final Object object;
    private boolean read;

    ConfigValue(String path, String key, Value value)
    {
        this(path == null ? key : path + '.' + key, value);
    }
    ConfigValue(String path, int index, Value value)
    {
        this(path + '_' + index, value);        
    }
    private ConfigValue(String path, Value value)
    {
        this.path = path;
            ValueType type = (value != null) ?
                    value.getType() :
                    ValueType.NULL;
            this.object =
                    type.equals(ValueType.NULL) ? 
                        null :
                    type.equals(ValueType.ARRAY) ? 
                        new ConfigValueArray(path , value.getArray()) :
                    type.equals(ValueType.DATA_SET) ?
                        new ConfigDataSet(this ,value.getDataSet()) :
                    value.getObject();
    }

    
    @Override
    public Object getObject()
    {
        this.read=true;
        return this.object;
    }

    @Override
    public void close() throws DataException
    {
        if (ConfigObject.class.isAssignableFrom(this.object.getClass()))
        {
            ((ConfigObject)this.object).close();
        }
        else
        {
            if (!this.read)
            {
                throw new DataException("cannot close",null);
            }
        }
    }

    @Override
    public void reset()
    {
        if (this.object != null &&
                ConfigObject.class.isAssignableFrom(this.object.getClass()))
        {
            ((ConfigObject)this.object).reset();
        }
        this.read = false;
    }

    @Override
    public ConfigDataSet getDataSet()
    {
        return (ConfigDataSet)super.getDataSet(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConfigValueArray getArray()
    {
        return (ConfigValueArray)super.getArray(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isRead()
    {
        if (this.read &&
                this.object != null &&
                ConfigObject.class.isAssignableFrom(this.object.getClass()))
        {
            return ((ConfigObject)this.object).isRead();
        }
        return this.read;
    }
    
    
    @Override
    public String getPath()
    {
        return this.path;
    }
    
}
