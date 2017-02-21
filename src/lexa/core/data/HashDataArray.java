/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * HashDataArray.java
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

import java.util.Iterator;

/**
 * An array of values in a {@link HashValue}
 *
 * @author william
 * @since 2016-09
 */
public class HashDataArray extends SimpleValueArray
{
	/**
	Create a new {@link DataArray}
	The initial array has no values
	*/
	public HashDataArray()
	{
		super();
	}
	
	/**
	Create a new {@link DataArray} cloning the previous.
	The initial array has no values
        @param clone an array to clone.
	*/
	public HashDataArray(DataArray clone)
	{
        super(clone);
	}

        /**
    Create a new {@link DataArray} from an array of objects
	The initial array has an entry for each object passed.
	@param objects an array of objects to populate the array.
	*/
	public HashDataArray(Object ... objects)
	{
		super(objects);
	}
}