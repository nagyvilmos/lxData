/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * CombinedFormat.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 30-03-2015	WNW	2015-03		Refactor FormatCombined -> CombinedFormat
 *================================================================================
 */
package lexa.core.data.formatting;

/**
 * Helper for serialising data values.
 * <p>An instance provides references to all the formats.  This is to circumvent
 * having multiple references in the code base where multiple types are required.
 *
 * @author William
 * @since 2013-08
 */
public class CombinedFormat
{

	/** formatting for a {@link Boolean} object */
	public final BooleanFormat booleanFormat = new BooleanFormat();
	/** formatting for a {@link java.util.Date} object */
	public final CombinedDateTimeFormat dateFormat = new CombinedDateTimeFormat();
	/** formatting for a {@link Double} object */
	public final DoubleFormat doubleFormat = new DoubleFormat();
	/** formatting for an {@link Integer} object */
	public final IntegerFormat integerFormat = new IntegerFormat();
	/** formatting for a {@link Long} object */
	public final LongFormat longFormat = new LongFormat();
	/** formatting for a {@link String} object */
	public final StringFormat stringFormat = new StringFormat();
}
