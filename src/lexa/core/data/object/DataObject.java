/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataObject.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2017
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 */
package lexa.core.data.object;

import lexa.core.data.DataSet;

/**
 * An object that can be represented as a DataObject
 * @author william
 */
public interface DataObject
{

    /**
     * Serialise a {@link DataObject} as a {@link DataSet}
     * @return a {@link DataSet} representing the {@link DataObject}
     */
    DataSet toData();

    /**
     * Deserialise a {@link DataObject} from a {@link DataSet}
     * @param data
     *          a {@link DataSet} representing the {@link DataObject}
     */
    void fromData(DataSet data);
}
