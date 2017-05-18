/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * ArrayFactory.java
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
public class ArrayFactory
        extends BaseFactory
{
    public static ArrayFactory factory = new ArrayFactory();

    private ArrayFactory() {} // keep in single, keep it simple

    @Override
    public DataArray getDataArray()
    {
        return new ArrayDataArray();
    }

    @Override
    public DataItem getDataItem(String key, Object value)
    {
        return new ArrayDataItem(key, value);
    }

    @Override
    public DataSet getDataSet()
    {
        return new ArrayDataSet();
    }

    @Override
    public DataValue getDataValue(Object object)
    {
        return new ArrayDataValue(object);
    }
}
