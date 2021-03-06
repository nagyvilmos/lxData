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
    /** Handle to the  instance of the {@link HashFactory} */
    public static HashFactory factory = new HashFactory();

    private HashFactory() {} // keep in single, keep it simple

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
