package lexa.core.data;
/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * DataFactory.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: April 2017
 *------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Description:
 * ----------   --- ------------------------------------------------------------
 * -            -   -
 *==============================================================================
 */

/**
 * Factory to create consistent instances for DataSets and their components.
 *
 * @author william
 * @since 2017-04
 */
public interface DataFactory
{
    boolean checkFactory(DataFactory factory);
    DataArray clone(DataArray clone);
    DataSet clone(DataSet clone);
    DataItem clone(DataItem clone);
    DataValue clone(DataValue clone);
    DataArray convert(DataArray convert);
    DataItem convert(DataItem convert);
    DataSet convert(DataSet convert);
    DataValue convert(DataValue convert);
    Object convert(Object convert);
    DataArray getDataArray();
    DataItem getDataItem(String key, Object value);
    DataSet getDataSet();
    DataValue getDataValue(Object object);

}
