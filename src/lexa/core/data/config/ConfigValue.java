/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private Object object;
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
            ValueType type = (value != null) ?
                    value.getType() :
                    ValueType.NULL;
            this.object =
                    type.equals(ValueType.NULL) ? 
                        null :
                    type.equals(ValueType.ARRAY) ? 
                        new ConfigValueArray(path , value.getArray()) :
                    type.equals(ValueType.DATA_SET) ?
                        new ConfigDataSet(path ,value.getDataSet()) :
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
    
    
}
