/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * ValeType - Known types for a DataItem
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2012
 *==============================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 * An enumeration that defines the set of supported value types for a {@link DataItem}
 * The acceptable type are:
 * <ul>
 * <li>NULL</li>
 * <li>ARRAY</li>
 * <li>BOOLEAN</li>
 * <li>DATA_SET</li>
 * <li>DATE</li>
 * <li>DOUBLE</li>
 * <li>INTEGER</li>
 * <li>LONG</li>
 * <li>STRING</li>
 * <li>OBJECT</li>
 * </ul>
 * @author William
 */
public enum DataType
{

	/** The value is null *//** The value is null */
	NULL(null,'~'),
	/** The value is a boolean */
	ARRAY(DataArray.class,':'),
	/** The value is a boolean */
	BOOLEAN(Boolean.class,'?'),
	/** The value is a data set */
	DATA_SET(DataSet.class, '#'),
	/** The value is a date */
	DATE(Date.class,'@'),
	/** The value is a double */
	DOUBLE(Double.class,'$'),
	/** The value is an integer */
	INTEGER(Integer.class,'%'),
	/** The value is a long */
	LONG(Long.class,'='),
	/** The value is a string */
	STRING(String.class,'-'),
	/** The value is any undefined object. DataType.OBJECT cannot be serialised. */
	OBJECT(Object.class,' ');

	/**
	 * Get the corresponding {@link DataType} of an {@link Object}.
	 *
	 * @param value An object to determine the type that it.
	 * @return The type of the object or {@code null} if it cannot be determined.
	 */
	public static DataType getType(Object value)
	{
		for (DataType type
				: DataType.values())
		{
			if (type.isType(value))
			{
				return type;
			}
		}
		// this should never occur, added to satisfy build warning.
		return null;
	}
	/** *  The class that defines the {@link DataType}. */
	private Class<?> typeClass;
	/** the character used for annotating the type */
	private char typeChar;

	/**
	 * Private constructor for {@link DataType}.
	 * @param typeClass The class that corresponds to the {@link DataType}.
	 * @param typeChar single char used to indicate type in config.
	 */
	private DataType(Class<?> typeClass, char typeChar)
	{
		this.typeClass = typeClass;
		this.typeChar = typeChar;
	}

	/**
	 * Compare an {@link Object} to the {@link DataType}.
	 * @param value An {@link Object} to be checked.
	 * @return {@code true} if the {@link Object} is assignable from the {@link DataType},
	 *      otherwise {@code false}.
	 */
	public boolean isType(Object value)
	{
		if (this.typeClass == null || value == null)
		{
			return this.typeClass == value;
		}
		return this.typeClass.isAssignableFrom(value.getClass());
	}

	/**
	Get the character used to annotate the type.
	@return the character used to annotate the type
	*/
	public char getTypeChar()
	{
		return this.typeChar;
	}

	/**
	Get the type corresponding to a character
	@param typeChar a character denoting a type.
	@return the type corresponding to the character
	*/
	public static DataType toType(char typeChar)
	{
		for (DataType vt : DataType.values())
		{
			if (vt.getTypeChar() == typeChar)
			{
				return vt;
			}
		}
		return OBJECT;
	}

	/**
	 * Filter an object to a specific type.
	 * @param value An {@link Object} to be checked.
	 * @return The object if it is of the type, otherwise null.
	 * @see DataType#isType(Object)
	 */
	Object getValueIfType(Object value)
	{
		if (this.isType(value))
		{
			return value;
		}
		return null;
	}
}