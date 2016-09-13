/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * HashValueArray.java
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
public class HashValueArray extends SimpleValueArray
{
	/**
	Create a new {@link ValueArray}
	The initial array has no values
	*/
	public HashValueArray()
	{
		super();
	}
	
	/**
	Create a new {@link ValueArray} cloning the previous.
	The initial array has no values
        @param clone an array to clone.
	*/
	public HashValueArray(ValueArray clone)
	{
        super(clone);
	}

        /**
    Create a new {@link ValueArray} from an array of objects
	The initial array has an entry for each object passed.
	@param objects an array of objects to populate the array.
	*/
	public HashValueArray(Object ... objects)
	{
		super(objects);
	}
}
