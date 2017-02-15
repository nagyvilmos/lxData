/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * SimpleValueArray.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: January 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-01-27	WNW	16-01       Extracted the interface
 *                              Remove the superfluose clone() method.
 * 2016-02-08   WNW 16-01       Add new addAll methods.
 *                              Change size to an int
 * 2016-09-09   WNW 16-09       Change base abstract classes from *Base to Base*
 *================================================================================
 */
package lexa.core.data;

/**
 * An array of values The array is self sizing, as it is implemented as a linked
 * list. The values are polymorphic as they are always held as {@link Value}
 * objects.
 *
 * @author william
 * @since 2016-01
 */
public class SimpleValueArray extends BaseValueArray
{

    /**
     * the first item in the list
     */
    private Index start;
    /**
     * the last item in the list
     */
    private Index end;
    /**
     * the most recent item in the list
     */
    private Index currentValue;
    /**
     * the position of the most recent item
     */
    private long currentIndex;
    /* the size of the array */
    private int size;

    /**
     * Create a new {@link ValueArray} The initial array has no values
     */
    public SimpleValueArray()
    {
        this.size = 0;
    }

    /**
     * Create a new {@link ValueArray} cloning the previous. The initial array
     * has no values
     *
     * @param clone an array to clone.
     */
    public SimpleValueArray(ValueArray clone)
    {
        this();
        clone.forEach(value -> this.add(
                value.getType().equals(ValueType.ARRAY)
                ? new SimpleValueArray(value.getArray())
                : value.getType().equals(ValueType.DATA_SET)
                ? new SimpleDataSet(value.getDataSet())
                : value
        ));
    }

    /**
     * Create a new {@link ValueArray} from an array of objects The initial
     * array has an entry for each object passed.
     *
     * @param objects an array of objects to populate the array.
     */
    public SimpleValueArray(Object... objects)
    {
        this();
        for (Object o : objects)
        {
            this.add(o);
        }
    }

    /**
     * Add an object to the array at a given position.
     * <p>
     * The value is added added to the array at the given position. The
     * following are equivalent:
     * <pre>{@code
     * va.add(obj, pos);
     * va.add(new SimpleValue(obj), pos);
     * }</pre>
     *
     * @param index the position for the object
     * @param object the value to add
     * @return this {@link ValueArray}
     */
    public ValueArray add(int index, Object object)
    {
        return this.add(index, new SimpleValue(object));
    }

    /**
     * Add a value to the array at a given position.
     * <p>
     * The value is added added to the array at the given position. The position
     * must be in the range of 0 to the size of the array. If the position is
     * equal to the size, then the new item will be added to the end.
     *
     * @param index the position for the object
     * @param value the value to add
     * @return this {@link ValueArray}
     */
    public ValueArray add(int index, Value value)
    {
        if (value.getType().equals(ValueType.NULL))
        {
            throw new IllegalArgumentException("[null] cannot be added to an array");
        }
        if (index == size)
        {
            this.currentValue = new Index();
            this.currentValue.value = value;
            this.currentValue.previous = this.end;
            if (this.size > 0)
            {
                this.end.next = this.currentValue;
            }
            this.end = this.currentValue;
        } else
        {
            this.get(index); // set the pointer
            Index old = this.currentValue;
            this.currentValue = new Index();
            this.currentValue.value = value;
            this.currentValue.previous = old.previous;
            old.previous = this.currentValue;
            this.currentValue.next = old;
        }

        this.currentIndex = index;
        this.size++;
        if (this.currentIndex == 0)
        {
            this.start = this.currentValue;
        }
        return this;
    }

    /**
     * Get the value at a position.
     *
     * @param index position of required value
     * @return the value corresponding to the position
     */
    public Value get(int index)
    {

        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        if (index == this.currentIndex)
        {
            return this.currentValue.value;
        }

        long min = index > this.currentIndex
                ? this.currentIndex : 0;
        long max = index < this.currentIndex
                ? this.currentIndex : this.size() - 1;

        if ((max - index) < (index - min))
        {
            // go back from max
            if (this.currentIndex < max)
            {
                // set back to the end
                this.currentIndex = max;
                this.currentValue = this.end;
            }
            while (this.currentIndex > index)
            {
                // move back
                this.currentIndex--;
                this.currentValue = this.currentValue.previous;
            }
        }
        else
        {
            // go forward from min
            if (this.currentIndex > min)
            {
                // set back to start
                this.currentIndex = 0;
                this.currentValue = this.start;
            }
            while (this.currentIndex < index)
            {
                // move forward
                this.currentIndex++;
                this.currentValue = this.currentValue.next;
            }
        }
            
        return this.currentValue.value;
    }

    /**
     * Remove the value at a position.
     *
     * @param index position of value to be removed
     * @return the value that has been removed
     */
    public Value remove(int index)
    {
        Value remove = this.get(index);

        // remove item from list
        if (currentValue.previous != null)
        {
            currentValue.previous.next = currentValue.next;
        } else
        {
            start = currentValue.next;
        }
        if (currentValue.next != null)
        {
            currentValue.next.previous = currentValue.previous;
        } else
        {
            end = currentValue.previous;
        }
        // set up new last/pos
        size--;
        if (currentIndex < size)
        {
            currentValue = currentValue.next;
        } else
        {
            currentIndex--;
            currentValue = currentValue.previous;
        }
        return remove;
    }

    /**
     * Get the size of the array.
     *
     * @return the number of elements in the array
     */
    public int size()
    {
        return this.size;
    }

    /**
     * An index item
     */
    private class Index
    {

        /**
         * the previous item
         */
        Index previous;
        /**
         * the next item
         */
        Index next;
        /**
         * the value
         */
        Value value;
    }
}
