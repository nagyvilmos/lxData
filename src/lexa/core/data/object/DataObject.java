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
    
    DataSet toData();
    void fromData(DataSet data);
    
}
