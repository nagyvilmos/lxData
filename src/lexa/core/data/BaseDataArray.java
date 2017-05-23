/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * BaseDataArray.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: February 2016
 *==============================================================================
 */
package lexa.core.data;

import java.util.Iterator;

/**
 * Base implementation of a {@link DataArray}
 * @author william
 * @since 2016-02
 */
public abstract class BaseDataArray
        implements DataArray {

    private final DataFactory factory;
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
     * Create a basic data array
     * @param factory the factory to build components
     */
    protected BaseDataArray(DataFactory factory)
    {
        this.factory = factory;
        this.size = 0;
    }

    /**
     * Create a new {@link DataArray} cloning the previous. The initial array
     * has no values
     *
     * @param factory the factory to use
     * @param clone an array to clone.
     */
    protected BaseDataArray(DataFactory factory, DataArray clone)
    {
        this(factory);
        clone.forEach(value -> this.add(value.getType().equals(DataType.ARRAY)
                ? this.factory.convert(value.getArray())
                : value.getType().equals(DataType.DATA_SET)
                ? this.factory.convert(value.getDataSet())
                : value.getObject()
        ));
    }

    /**
     * Create a new {@link DataArray} from an array of objects The initial
     * array has an entry for each object passed.
     *
     * @param factory the factory to use
     * @param objects an array of objects to populate the array.
     */
    protected BaseDataArray(DataFactory factory,  Object... objects)
    {
        this(factory);
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
    @return this {@link DataArray}
     */
    @Override
    public DataArray add(Object object) {
        return this.add(this.size(), object);
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
    @return this {@link DataArray}
     */
    @Override
    public DataArray add(DataValue value) {
        return this.add(this.size(), value);
    }

    @Override
    public DataArray addAll(DataArray array) {
        if (array != null)
        {
            array.forEach((DataValue value) -> this.add(value));
        }
        return this;
    }

    @Override
    public DataArray addAll(Object[] array) {
        if (array != null)
        {
            for (Object object : array)
            {
                this.add(object);
            }
        }
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (DataValue v : this) {
            hash = hash * 13 + v.hashCode();
        }
        return hash;
    }

    @Override
    public int compareTo(DataArray to)
    {
        int compLen = this.size() - to.size();
        int compSize =
                compLen > 0 ?
                to.size() :
                this.size();
        for (int i = 0; i < compSize; i++)
        {
            int comp = this.get(i).compareTo(to.get(i));
            if (comp!=0)
            {
                return comp;
            }
        }
        return compLen;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !DataArray.class.isAssignableFrom(obj.getClass()))
        {
            return false;
        }
        final DataArray other = (DataArray) obj;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<DataValue> iterator() {
        return new DataArrayIterator(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        Iterator<DataValue> it = this.iterator();
        while (it.hasNext())
        {
            DataValue v = it.next();
            sb.append(v);
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append(']').toString();
    }

    /**
     * Add an object to the array at a given position.
     * <p>
     * The value is added added to the array at the given position. The
     * following are equivalent:
     * <pre>{@code
     * va.add(obj, pos);
 va.add(new ArrayDataValue(obj), pos);
 }</pre>
     *
     * @param index the position for the object
     * @param object the value to add
     * @return this {@link DataArray}
     */
    public DataArray add(int index, Object object)
    {
        if ((object != null) && DataValue.class.isAssignableFrom(object.getClass()))
            return this.add(index, this.factory.convert((DataValue)object));

        return this.add(index, this.factory.getDataValue(object));
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
     * @return this {@link DataArray}
     */
    public DataArray add(int index, DataValue value)
    {
        if (value.getType().equals(DataType.NULL))
        {
            throw new IllegalArgumentException("[null] cannot be added to an array");
        }
        if (index == size)
        {
            this.currentValue = new Index();
            this.currentValue.value = this.factory.convert(value);
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

    @Override
    public DataFactory factory()
    {
        return this.factory;
    }
    /**
     * Get the value at a position.
     *
     * @param index position of required value
     * @return the value corresponding to the position
     */
    public DataValue get(int index)
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
    public DataValue remove(int index)
    {
        DataValue remove = this.get(index);

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
    protected class Index
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
        DataValue value;
    }
}
