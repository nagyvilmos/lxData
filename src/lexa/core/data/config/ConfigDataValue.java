/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ConfigDataValue.java
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

import lexa.core.data.BaseDataValue;
import lexa.core.data.exception.DataException;
import lexa.core.data.DataValue;

/**
 *
 * @author william
 */
public class ConfigDataValue
        extends BaseDataValue
        implements ConfigObject
{
    private boolean read;

    ConfigDataValue(ConfigFactory factory, Object  object)
    {
        super(factory, object);
    }
    ConfigDataValue(ConfigFactory factory, DataValue value)
    {
        super(factory,value);
//        this.path = path;
//            DataType type = (value != null) ?
//                    value.getType() :
//                    DataType.NULL;
//            this.object =
//                    type.equals(DataType.NULL) ?
//                        null :
//                    type.equals(DataType.ARRAY) ?
//                        new ConfigDataArray(path , value.getArray()) :
//                    type.equals(DataType.DATA_SET) ?
//                        new ConfigDataSet(this ,value.getDataSet()) :
//                    value.getObject();
    }

    @Override
    public ConfigFactory configFactory()
    {
        return (ConfigFactory)this.factory();
    }

    @Override
    public void close() throws DataException
    {
        Object object = this.getObject();
        if (ConfigObject.class.isAssignableFrom(object.getClass()))
        {
            ((ConfigObject)object).close();
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
        Object object = this.getObject();
        if (object != null &&
                ConfigObject.class.isAssignableFrom(object.getClass()))
        {
            ((ConfigObject)object).reset();
        }
        this.read = false;
    }

    @Override
    public ConfigDataSet getDataSet()
    {
        return (ConfigDataSet)super.getDataSet(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConfigDataArray getArray()
    {
        return (ConfigDataArray)super.getArray(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
	 * Gets the internal value.
	 * @return The value of the item.
	 */
	@Override
	public Object getObject()
	{
        this.read = true;
		return super.getObject();
	}

    @Override
    public boolean isRead()
    {
        Object object = super.getObject();
        if (this.read &&
                object != null &&
                ConfigObject.class.isAssignableFrom(object.getClass()))
        {
            return ((ConfigObject)object).isRead();
        }
        return this.read;
    }


    @Override
    public String getPath()
    {
        return this.configFactory().getPath();
    }

}
