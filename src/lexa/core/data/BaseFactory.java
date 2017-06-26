/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * BaseFactory.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: May 2017
 *==============================================================================
 */
package lexa.core.data;

/**
 *
 * @author william
 * @since 2017-05
 */
public abstract class BaseFactory
        implements DataFactory
{
    @Override
    public boolean checkFactory(DataFactory factory)
    {
        return this == factory;
    }

    @Override
    public DataArray clone(DataArray array)

    {
        DataArray clone = this.getDataArray();
        for (DataValue value : array)
        {
            clone.add(this.clone(value));
        }
        return  clone;
    }

    @Override
    public DataSet clone(DataSet data)
    {
        DataSet clone = this.getDataSet();
        for (DataItem item : data)
        {
            clone.put(this.clone(item));
        }
        return clone;
    }

    @Override
    public DataItem clone(DataItem item)
    {
        return this.getDataItem(item.getKey(),
                this.clone(item.getObject()));
    }

    @Override
    public DataValue clone(DataValue value)
    {
        return this.getDataValue(
                this.clone(value.getObject()));
    }

    @Override
    public Object clone(Object object)
    {
        if (object == null)
        {
            return null;
        }
        Class cl = object.getClass();

        if (DataSet.class.isAssignableFrom(cl))
        {
            return this.clone((DataSet)object);
        }
        else if (DataArray.class.isAssignableFrom(cl))
        {
            return this.clone((DataArray)object);
        }
        else if (DataItem.class.isAssignableFrom(cl))
        {
            return this.clone(((DataItem)object).getObject());
        }
        else if (DataValue.class.isAssignableFrom(cl))
        {
            return this.clone(((DataValue)object).getObject());
        }
        // nothing:
        return object;
    }

    @Override
    public DataArray convert(DataArray array)
    {
        if (array != null &&
                !this.checkFactory(array.factory()))
        {
            return this.clone(array);
        }
        return array;
    }

    @Override
    public DataSet convert(DataSet data)
    {
        if (data != null &&
                !this.checkFactory(data.factory()))
        {
            return this.clone(data);
        }
        return data;
    }

    @Override
    public DataItem convert(DataItem item)
    {
        if (item != null &&
                !this.checkFactory(item.factory()))
        {
            return this.clone(item);
        }
        return item;
    }

    @Override
    public DataValue convert(DataValue value)
    {
        if (value != null &&
                !this.checkFactory(value.factory()))
        {
            return this.clone(value);
        }
        return value;
    }

    @Override
    public Object convert(Object object)
    {
        if (object == null)
        {
            return null;
        }
        Class cl = object.getClass();

        if (DataSet.class.isAssignableFrom(cl))
        {
            return this.convert((DataSet)object);
        }
        else if (DataArray.class.isAssignableFrom(cl))
        {
            return this.convert((DataArray)object);
        }
        else if (DataItem.class.isAssignableFrom(cl))
        {
            return this.convert(((DataItem)object).getObject());
        }
        else if (DataValue.class.isAssignableFrom(cl))
        {
            return this.convert(((DataValue)object).getObject());
        }
        // nothing:
        return object;
    }

}
