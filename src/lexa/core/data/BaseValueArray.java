/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * BaseValueArray.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: February 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-09-09   WNW 16-09       Change base abstract classes from *Base to Base*
 *================================================================================
 */
package lexa.core.data;

import java.util.Iterator;

/**
 * Base implementation of a {@link DataArray}
 * @author william
 * @since 2016-02
 */
public abstract class BaseValueArray
        implements DataArray {

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
        array.forEach((DataValue value) -> this.add(value));
        return this;
    }

    @Override
    public DataArray addAll(Object[] array) {
        for (Object object : array) {
            this.add(object);
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

}
