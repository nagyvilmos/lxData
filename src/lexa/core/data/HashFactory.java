/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * HashFactory.java
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

package lexa.core.data;

/**
 *
 * @author william
 */
public class HashFactory
        extends BaseFactory
{
    public static HashFactory factory = new HashFactory();

    private HashFactory() {} // keep in single, keep it simple

    @Override
    public DataArray clone(DataArray clone)
    {
        return new HashDataArray(clone);
    }

    @Override
    public DataSet clone(DataSet clone)
    {
        return new HashDataSet(clone);
    }

    @Override
    public DataItem clone(DataItem clone)
    {
        return new HashDataItem(clone);
    }

    @Override
    public DataArray getDataArray()
    {
        return new HashDataArray();
    }

    @Override
    public DataItem getDataItem(String key, Object value)
    {
        return new HashDataItem(key, value);
    }

    @Override
    public DataSet getDataSet()
    {
        return new HashDataSet();
    }

    @Override
    public DataValue getDataValue(Object object)
    {
        return new HashDataValue(object);
    }
}
