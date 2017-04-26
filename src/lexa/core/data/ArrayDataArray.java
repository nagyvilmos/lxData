/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ArrayDataArray.java
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
 * list. The values are polymorphic as they are always held as {@link DataValue}
 * objects.
 *
 * @author william
 * @since 2016-01
 */
public class ArrayDataArray extends BaseDataArray
{
    /**
     * Create a new {@link DataArray} The initial array has no values
     */
    public ArrayDataArray()
    {
        super(ArrayFactory.factory);
    }

    /**
     * Create a new {@link DataArray} cloning the previous. The initial array
     * has no values
     *
     * @param clone an array to clone.
     */
    public ArrayDataArray(DataArray clone)
    {
        super(ArrayFactory.factory, clone);
    }

    /**
     * Create a new {@link DataArray} from an array of objects The initial
     * array has an entry for each object passed.
     *
     * @param objects an array of objects to populate the array.
     */
    public ArrayDataArray(Object... objects)
    {
        super(ArrayFactory.factory, objects);
    }

    @Override
    public DataFactory factory()
    {
        return ArrayFactory.factory;
    }
}
