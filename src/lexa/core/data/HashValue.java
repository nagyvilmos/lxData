/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * HashValue.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------

 *================================================================================
 */
package lexa.core.data;

/**
 Implementation of {@link DataValue} for use in a {@link HashDataSet}
 <p>
 This class manages the type conversion and keeps it type safe.
 @author william
 @since 2016-09
 */
public class HashValue
        extends BaseDataValue
{
    /** the object being represented */
	private final Object object;
	
	/**
	Create a value to represent an object
	@param object the contained value
	*/
	public HashValue(Object object)
	{
		this.object = object;
	}
	
	/**
	Create a value as a clone of another value
	@param clone a DataValue to clone
	*/
	public HashValue(DataValue clone)
	{
            DataType type = (clone != null) ?
                    clone.getType() :
                    DataType.NULL;
            this.object =
                    type.equals(DataType.NULL) ? 
                        null :
                    type.equals(DataType.ARRAY) ? 
                        new HashValueArray(clone.getArray()) :
                    type.equals(DataType.DATA_SET) ?
                        new HashDataSet(clone.getDataSet()) :
                    clone.getObject();
	}

	@Override
	public Object getObject()
	{
		return this.object;
	}
}
