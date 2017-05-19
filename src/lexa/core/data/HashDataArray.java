/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * HashDataArray.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *==============================================================================
 */
package lexa.core.data;

/**
 * An array of values for a {@link HashDataSet}
 *
 * @author william
 * @since 2016-09
 */
public class HashDataArray extends BaseDataArray
{
	/**
	Create a new {@link HashDataArray}
	The initial array has no values
	*/
	public HashDataArray()
	{
		super(HashFactory.factory);
	}

	/**
	Create a new {@link DataArray} cloning the previous.
	The initial array has no values
        @param clone an array to clone.
	*/
	public HashDataArray(DataArray clone)
	{
        super(HashFactory.factory, clone);
	}

        /**
    Create a new {@link DataArray} from an array of objects
	The initial array has an entry for each object passed.
	@param objects an array of objects to populate the array.
	*/
	public HashDataArray(Object ... objects)
	{
		super(HashFactory.factory, objects);
	}
}
