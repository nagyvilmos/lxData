/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * DoubleFormat.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *==============================================================================
 */
package lexa.core.data.formatting;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Helper for serialising {@link Double} values.
 * @author William
 * @since 2015-03
 */
public class DoubleFormat
		implements Format<Double>
{
	/** number formatter to use */
	private NumberFormat numberFormat;

	/**
	Create a double formatter using the default ENGLISH local
	@see Locale#ENGLISH
	*/
	public DoubleFormat()
	{
		this(Locale.ENGLISH);
	}

	/**
	Create a double formatter for a locale.
	@param locale the local to provide formatting for.
	*/
	public DoubleFormat(Locale locale)
	{
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	@Override
	public String toString(Double value)
	{
        if (Double.isFinite(value))
        {
            return this.numberFormat.format(value);
        }
        if (Double.isNaN(value))
        {
            return "NaN";
        }
        if (Double.POSITIVE_INFINITY == value)
        {
            return "+Inf";
        }
        if (Double.NEGATIVE_INFINITY == value)
        {
            return "-Inf";
        }
        // should never reach here
        return "";
	}

	@Override
	public Double fromString(String string)
	{
        switch (string)
        {
            case "NaN" : return Double.NaN;
            case "+Inf" : return Double.POSITIVE_INFINITY;
            case "-Inf" : return Double.NEGATIVE_INFINITY;
        }
		try
		{
			return this.numberFormat.parse(string)
					.doubleValue();
		}
		catch (Exception ex)
		{
			// if we can't fromString it, return null
			return null;
		}
	}
}