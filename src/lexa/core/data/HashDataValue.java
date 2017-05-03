/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * HashDataValue.java
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
public class HashDataValue
        extends BaseDataValue
{
	/**
	Create a value to represent an object
	@param object the contained value
	*/
	public HashDataValue(Object object)
	{
        super(HashFactory.factory,object);
	}

	/**
	Create a value as a clone of another value
	@param clone a DataValue to clone
	*/
	public HashDataValue(DataValue clone)
	{
        super(HashFactory.factory,clone);
	}

    @Override
    public DataFactory factory()
    {
        return HashFactory.factory;
    }
}
