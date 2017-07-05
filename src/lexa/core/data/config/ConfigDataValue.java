/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * ConfigDataValue.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *==============================================================================
 */
package lexa.core.data.config;

import lexa.core.data.BaseDataValue;
import lexa.core.data.DataType;
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
                throw new DataException("cannot close", this.getPath());
            }
        }
    }

    @Override
    public ConfigFactory configFactory()
    {
        return (ConfigFactory)this.factory();
    }

    @Override
    public ConfigDataArray getArray()
    {
        return (ConfigDataArray)super.getArray();
    }

    @Override
    public Boolean getBoolean()
    {
        return super.getBoolean();
    }

    @Override
    public ConfigDataSet getDataSet()
    {
        return (ConfigDataSet)super.getDataSet();
    }

	@Override
	public Object getObject()
	{
        this.read = true;
		return super.getObject();
	}

    @Override
    public String getPath()
    {
        return this.configFactory().getPath();
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

    /**
     * Validate the data type for the item
     * @param   type
     *          the type of object expected
     * @throws  DataException
     *          when the type does not match
     */
    public void validateType(DataType type)
            throws DataException
    {
        if (!this.getType().equals(type))
        {
            throw new DataException(
                    "Config item not expected type : " + type.toString(),
                    this.getPath()
            );
        }
    }
}
