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
    DataArray clone(DataArray clone);
    DataSet clone(DataSet clone);
    DataItem clone(DataItem clone);
    DataArray convert(DataArray convert);
    DataSet convert(DataSet convert);
    DataItem convert(DataItem convert);
    DataValue convert(DataValue convert);
    DataArray getDataArray();
    DataSet getDataSet();
    DataItem getDataItem(String key, Object value);
}
