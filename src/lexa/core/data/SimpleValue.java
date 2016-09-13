/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * SimpleValue.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 *                              Add in a cloning constructor.
 * 2016-01-28   WNW 16-01       Update javadoc.
 * 2016-02-08   WNW 16-01       Constructors are public
 * 2016-09-09   WNW 16-09       Change base abstract classes from *Base to Base*
 *================================================================================
 */
package lexa.core.data;

/**
 Implementation of {@link Value} for use in a {@link SimpleDataSet}
 <p>
 This class manages the type conversion and keels it type safe.
 @author william
 @since 2015-03
 */
public class SimpleValue
        extends BaseValue
{
    /** the value being represented */
	private final Object value;
	
	/**
	Create a value to represent an object
	@param value the contained value
	*/
	public SimpleValue(Object value)
	{
		this.value = value;
	}
	
	/**
	Create a value as a clone of another value
	@param clone a Value to clone
	*/
	public SimpleValue(Value clone)
	{
            ValueType type = (clone != null) ?
                    clone.getType() :
                    ValueType.NULL;
            this.value =
                    type.equals(ValueType.NULL) ? 
                        null :
                    type.equals(ValueType.ARRAY) ? 
                        new SimpleValueArray(clone.getArray()) :
                    type.equals(ValueType.DATA_SET) ?
                        new SimpleDataSet(clone.getDataSet()) :
                    clone.getObject();
	}

    /**
	 * Gets the internal value.
	 * @return The value of the item.
	 */
	@Override
	public Object getObject()
	{
		return this.value;
	}
}
