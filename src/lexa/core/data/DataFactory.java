package lexa.core.data;
/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * DataFactory.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: April 2017
 *==============================================================================
 */

/**
 * Factory to create consistent instances for DataSets and their components.
 *
 * @author william
 * @since 2017-04
 */
public interface DataFactory
{
    /**
     * Compare two factories to check they produce the same type of data objects
     * @param factory
     *      the factory to compare against this.
     * @return {@code true} if they are the same type of factory, otherwise {@code false}
     */
    boolean checkFactory(DataFactory factory);

    /**
     * Clone a {@link DataArray}.
     * Create a clone of the provided {@link DataArray} that is consistent with
     * this {@link DataFactory}. This will always result in a deep copy.
     *
     * @param array
     *          the {@link DataArray} to be cloned.
     * @return  a new {@link DataArray} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    DataArray clone(DataArray array);

    /**
     * Clone a {@link DataSet}.
     * Create a clone of the provided {@link DataSet} that is consistent with
     * this {@link DataFactory}. This will always result in a deep copy.
     *
     * @param data
     *          the {@link DataSet} to be cloned.
     * @return  a new {@link DataSet} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    DataSet clone(DataSet data);

    /**
     * Clone a {@link DataItem}.
     * Create a clone of the provided {@link DataItem} that is consistent with
     * this {@link DataFactory}. This will always result in a deep copy.
     *
     * @param item
     *          the {@link DataItem} to be cloned.
     * @return  a new {@link DataItem} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    DataItem clone(DataItem item);

    /**
     * Clone a {@link DataValue}.
     * Create a clone of the provided {@link DataValue} that is consistent with
     * this {@link DataFactory}. This will always result in a deep copy.
     *
     * @param value
     *          the {@link DataValue} to be cloned.
     * @return  a new {@link DataValue} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    DataValue clone(DataValue value);

    /**
     * Clone an {@link Object}.
     * Create a clone of the provided {@link Object} that is consistent with
     * this {@link DataFactory}. This will always result in a deep copy of any
     * components
     * 
     * @param object
     *          the {@link Object} to be converted.
     * @return  a {@link Object} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    Object clone(Object object);

    /**
     * Convert a {@link DataArray}.
     * Make sure that the {@link DataArray} is of the type supported by this
     * {@link DataFactory} and if it is a different type then create a clone.
     *
     * @param array
     *          the {@link DataArray} to be converted.
     * @return  a {@link DataArray} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    DataArray convert(DataArray array);

    /**
     * Convert a {@link DataItem}.
     * Make sure that the {@link DataItem} is of the type supported by this
     * {@link DataFactory} and if it is a different type then create a clone.
     *
     * @param item
     *          the {@link DataItem} to be converted.
     * @return  a {@link DataItem} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    DataItem convert(DataItem item);

    /**
     * Convert a {@link DataSet}.
     * Make sure that the {@link DataSet} is of the type supported by this
     * {@link DataFactory} and if it is a different type then create a clone.
     *
     * @param data
     *          the {@link DataSet} to be converted.
     * @return  a {@link DataSet} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    DataSet convert(DataSet data);

    /**
     * Convert a {@link DataValue}.
     * Make sure that the {@link DataValue} is of the type supported by this
     * {@link DataFactory} and if it is a different type then create a clone.
     *
     * @param value
     *          the {@link DataValue} to be converted.
     * @return  a {@link DataValue} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    DataValue convert(DataValue value);

    /**
     * Convert an {@link Object}.
     * Make sure that the {@link Object} is of the type supported by this
     * {@link DataFactory} and if it is a different type then create a clone.
     *
     * @param object
     *          the {@link Object} to be converted.
     * @return  a {@link Object} of the type supported by this
     *          {@link DataFactory} with the same content.
     */
    Object convert(Object object);

    /**
     * Create a new {@link DataArray}
     * @return  an empty {@link DataArray} of the type supported by this
     *          {@link DataFactory}.
     */
    DataArray getDataArray();

    /**
     * Create a new {@link DataItem}
     * @param key
     *          the key for the item
     * @param value
     *          the value for the item
     * @return  a {@link DataItem} of the type supported by this
     *          {@link DataFactory} with the {@code key} and {@code value}
     */
    DataItem getDataItem(String key, Object value);

    /**
     * Create a new {@link DataSet}
     * @return  an empty {@link DataSet} of the type supported by this
     *          {@link DataFactory}.
     */
    DataSet getDataSet();

    /**
     * Create a new {@link DataValue}
     * @param object
     *          the object for the item
     * @return  a {@link DataValue} of the type supported by this
     *          {@link DataFactory} with the {@code object}
     */
    DataValue getDataValue(Object object);
}
