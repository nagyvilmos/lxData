/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * BaseFactory.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: Month YEAR
 *------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Description:
 * ----------   --- ------------------------------------------------------------
 * -            -   -
 *==============================================================================
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lexa.core.data;

/**
 *
 * @author william
 */
public abstract class BaseFactory
        implements DataFactory
{
    @Override
    public DataArray convert(DataArray convert)
    {
        if (convert != null &&
                convert.factory() != this)
        {
            return this.getDataArray().add(convert);
        }
        return convert;
    }

    @Override
    public DataSet convert(DataSet convert)
    {
        if (convert != null &&
                convert.factory() != this)
        {
            return this.getDataSet().put(convert);
        }
        return convert;
    }

    @Override
    public DataItem convert(DataItem convert)
    {
        if (convert != null &&
                convert.factory() != this)
        {
            return this.getDataItem(convert.getKey(), convert.getString());
        }
        return convert;
    }

    @Override
    public DataValue convert(DataValue convert)
    {
        if (convert != null &&
                convert.factory() != this)
        {
            return this.getDataValue(convert.getObject());
        }
        return convert;
    }

    @Override
    public Object convert(Object convert)
    {
        if (convert == null)
        {}
        else if (DataSet.class.isAssignableFrom(convert.getClass()))
        {
            return this.convert((DataSet)convert);
        }
        else if (DataArray.class.isAssignableFrom(convert.getClass()))
        {
            return this.convert((DataArray)convert);
        }
        else if (DataValue.class.isAssignableFrom(convert.getClass()))
        {
            return this.convert(((DataValue)convert).getObject());
        }
        // nothing:
        return convert;
    }

}
