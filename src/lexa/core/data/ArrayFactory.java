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
class ArrayFactory
        implements DataFactory
{
    public static ArrayFactory factory = new ArrayFactory();
    private ArrayFactory() {} // keep in single, keep it simple
    @Override
    public DataArray clone(DataArray clone)
    {
        return new ArrayDataArray(clone);
    }

    @Override
    public DataSet clone(DataSet clone)
    {
        return new ArrayDataSet(clone);
    }

    @Override
    public DataItem clone(DataItem clone)
    {
        return new ArrayDataItem(clone);
    }

    @Override
    public DataArray convert(DataArray convert)
    {
        if (convert != null &&
                convert.getClass().equals(ArrayDataArray.class))
        {
            return new ArrayDataArray(convert);
        }
        return convert;
    }

    @Override
    public DataSet convert(DataSet convert)
    {
        if (convert != null &&
                convert.getClass().equals(ArrayDataSet.class))
        {
            return new ArrayDataSet(convert);
        }
        return convert;
    }

    @Override
    public DataItem convert(DataItem convert)
    {
        if (convert != null &&
                convert.getClass().equals(ArrayDataItem.class))
        {
            return new ArrayDataItem(convert);
        }
        return convert;
    }

    @Override
    public DataValue convert(DataValue convert)
    {
        if (convert != null &&
                convert.getClass().equals(ArrayDataValue.class))
        {
            return new ArrayDataValue(convert);
        }
        return convert;
    }

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
}
