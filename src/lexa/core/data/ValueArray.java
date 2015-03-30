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
 *
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
public class ValueArray
		implements Iterable<Value>
{
	/** the first item in the list */
	private Index start;
	/** the last item in the list */
	private Index end;
	/** the most recent item in the list */
	private Index recent;
	/** the position of the most recent item */
	private long pos;
	/* the size of the array */
	private long size;
	
	/**
	Create a new {@link ValueArray}
	The initial array has no values
	*/
	public ValueArray()
	{
		this.size = 0;
	}
	
	/**
	Create a new {@link ValueArray} from an array of objects
	The initial array has an entry for each object passed.
	@param objects an array of objects to populate the array.
	*/
	public ValueArray(Object ... objects)
	{
		this();
		for (Object o : objects)
		{
			this.add(o);
		}
	}

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
	public ValueArray add(Object object)
	{
		return this.add(new SimpleValue(object));
	}
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
	public ValueArray add(Value value)
	{
		return this.add(size, value);
	}

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
	public ValueArray add(long index, Object object)
	{
		return this.add(index, new SimpleValue(object));
	}

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
	public ValueArray add(long index, Value value)
	{
		if (value.getType().equals(ValueType.NULL))
		{
			throw new IllegalArgumentException("[null] cannot be added to an array");
		}
		if (index == size)
		{
			this.recent = new Index();
			this.recent.value = value;
			this.recent.previous = this.end;
			if (this.size > 0)
				this.end.next = this.recent;
			this.end = this.recent;
		}
		else
		{
			this.get(index); // set the pointer
			Index old = this.recent;
			this.recent = new Index();
			this.recent.value = value;
			this.recent.previous = old.previous;
			old.previous = this.recent;
			this.recent.next = old;
		}

		this.pos = index;
		this.size++;
		if (this.pos == 0)
		{
			this.start = this.recent;
		}
		return this;
	}

	public ValueArray clone()
	{
		ValueArray clone = new ValueArray();
		for (Value v : this)
		{
			clone.add(v.clone());
		}
		return clone;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		for (Value v : this)
		{
			hash = hash * 13 + v.hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final ValueArray other = (ValueArray) obj;
		if (this.size() != other.size())
			return false;
		for (int i = 0; i<this.size(); i++)
		{
			if (!this.get(i).equals(other.get(i)))
				return false;
		}
		return true;
	}

	
	/**
	Get the value at a position.
	@param index position of required value
	@return the value corresponding to the position
	*/
	public Value get(long index)
	{
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		// who is closest?
		long dp = pos-index;
		if (dp<0)
			dp=-dp;
		long de = size - index - 1;
		if (index < dp && index < de)
		{
			recent = start;
			pos = 0;
		}
		else if (de < dp)
		{
			recent = end;
			pos = size-1;
		}
		// third case of dp least is covered
		while (pos < index)
		{
			// move forward
			pos++;
			recent=recent.next;
		}
		while (pos > index)
		{
			// move back
			pos--;
			recent=recent.previous;
		}
		return this.recent.value;
	}

	@Override
	public Iterator<Value> iterator()
	{
		return new ValueArrayIterator(this);
	}

	/**
	Remove the value at a position.
	@param index position of value to be removed
	@return the value that has been removed
	*/
	public Value remove(long index)
	{
		Value remove = this.get(index);
		
		// remove item from list
		if (recent.previous!=null)
		{
			recent.previous.next = recent.next;
		}
		else
		{
			start = recent.next;
		}
		if (recent.next!=null)
		{
			recent.next.previous = recent.previous;
		}
		else
		{
			end = recent.previous;
		}
		// set up new last/pos
		size--;
		if (pos < size)
		{
			recent=recent.next;
		}
		else
		{
			pos--;
			recent=recent.previous;
		}
		return remove;
	}

	/**
	Get the size of the array.
	@return the number of elements in the array
	*/
	public long size()
	{
		return this.size;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Index i = this.start;
		while (i != null)
		{
			sb.append(i.value);
			i = i.next;
			if (i != null)
			{
				sb.append(", ");
			}
		}
		return sb.append(']').toString();
	}

	/**
	An index item
	*/
	private class Index 
	{
		/** the previous item */
		Index previous;
		/** the next item */
		Index next;
		/** the value */
		Value value;
	}
}
