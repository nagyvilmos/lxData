/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ValueArray.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-01-27	WNW	16-01       Extracted the interface
 *                              Remove the superfluose clone() method.
 * 2016-02-08   WNW 16-01       Add new addAll methods.
 *                              Change size to an int
 *================================================================================
 */
package lexa.core.data;

import java.util.Iterator;

/**
 * An array of values
 * The array is self sizing, as it is implemented as a 
 * linked list.  The values are polymorphic as they are always
 * held as {@link Value} objects.
 *
 * @author william
 * @since 2015-03
 */
public interface ValueArray
		extends Iterable<Value>
{
	/**
	Add an object to the array.
	<p>
	The value is added added to the end of the array.  The following are equivalent:
	<pre>{@code
	va.add(obj);
	va.add(new SimpleValue(obj));
	va.add(obj, va.size);
	va.add(new SimpleValue(obj), va.size);
	}</pre>
	@param object the object to add
	@return this {@link ValueArray}
	*/
	public ValueArray add(Object object);
        
	/**
	Add a value to the array.
	<p>
	The value is added added to the end of the array.  The following are equivalent:
	<pre>{@code
	va.add(val);
	va.add(val, va.size);
	}</pre>
	@param value the value to add
	@return this {@link ValueArray}
	*/
	public ValueArray add(Value value);

	/**
	Add an object to the array at a given position.
	<p>
	The value is added added to the array at the given position.  The following are equivalent:
	<pre>{@code
	va.add(obj, pos);
	va.add(new SimpleValue(obj), pos);
	}</pre>
	@param index the position for the object
	@param object the value to add
	@return this {@link ValueArray}
	*/
	public ValueArray add(int index, Object object);

	/**
	Add a value to the array at a given position.
	<p>
	The value is added added to the array at the given position.
	The position must be in the range of 0 to the size of the array.
	If the position is equal to the size, then the new item will be added to the end.
	@param index the position for the object
	@param value the value to add
	@return this {@link ValueArray}
	*/
	public ValueArray add(int index, Value value);

	/**
	Add all the values to the array.
	<p>
	The values are added to the array at the end.
	@param array a {@link ValueArray} to add
	@return this {@link ValueArray}
	*/
	public ValueArray addAll(ValueArray array);

	/**
	Add all the values to the array.
	<p>
	The values are added to the array at the end.
	@param array an array to add
	@return this {@link ValueArray}
	*/
	public ValueArray addAll(Object[] array);

    /**
	Get the value at a position.
	@param index position of required value
	@return the value corresponding to the position
	*/
	public Value get(int index);

	@Override
	public Iterator<Value> iterator();

	/**
	Remove the value at a position.
	@param index position of value to be removed
	@return the value that has been removed
	*/
	public Value remove(int index);

	/**
	Get the size of the array.
	@return the number of elements in the array
	*/
	public int size();
}
