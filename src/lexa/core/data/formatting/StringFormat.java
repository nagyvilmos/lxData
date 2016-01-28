/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * StringFormat.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 30-03-2015	WNW	2015-03		Refactor FormatString -> StringFormat
 * 2016-01-28   WNW 16-01       Update javadoc.
 *================================================================================
 */
package lexa.core.data.formatting;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper for serialising {@link String} values.
 * @author William
 * @since 2013-08
 */
public class StringFormat
		implements Format<String>
{
	@Override
	public String toString(String value)
	{
		if (value == null || "".equals(value)) {
			return value;
		}
		// slash in front of \ # - " { } ? $ @ % \n
		StringBuffer out = new StringBuffer(value.length() + (value.length() / 10));
		for (char c : value.toCharArray())
		{
			if ("\\#-\"{}?@%\n".indexOf(c) != -1)
			{
				out.append('\\');
			}
			out.append(c);
		}
		return out.toString();
//		Matcher matcher = Pattern.compile("\\\\|#|-|\\\"|\\{|\\}|\\?|@|%|\\n").matcher(value);
//		StringBuffer out = new StringBuffer(value.length() + (value.length() / 10));
//		while (matcher.find()) {
//			String rep = "\\" + matcher.group();
//			matcher.appendReplacement(out, rep);
//			//String ms = matcher.group();
//			//matcher.appendReplacement(out, "\\" + ms);
//			//matcher.appendReplacement(out, "\\");
//			//out.append(ms);
//		}
//		matcher.appendTail(out);
//		return out.toString();
	}

	@Override
	public String fromString(String string)
	{
		return string;
	}
	
	/**
	Perform a multiple search and replace on a string.
	<p>The search and replace is done on paired values in an array, the search string
	then the string to replace with.
	The terms are located in order, so a substring will never be found if after the 
	main string and vica-versa.
	<pre>
	String [][] terms = {
			{"sat", "stood"},
			{"at", "art"},
			{"on", "in"}
			{"the", "thy"}
	};
	String replaced = StringFormat.multiReplace("The cat sat on the mat", terms);
	// replaced = "The cart stood in thy mart"
	</pre>
    * 
	@param input input string to be parsed
	@param replacements two dimensional array of replacement terms.
	@return the parsed string
	*/
	public static String multiReplace(
		final String input, String[] ... replacements) {

		if (input == null || "".equals(input) || replacements == null) {
			return input;
		}
		StringBuilder regexBuilder = new StringBuilder();
		Map<String,String> pairings = new HashMap();
		for (String[] pair : replacements)
		{
			regexBuilder.append('|').append(Pattern.quote(pair[0]));
			pairings.put(pair[0],pair[1]);
		}
		Matcher matcher = Pattern.compile(regexBuilder.toString()).matcher(input);
		StringBuffer out = new StringBuffer(input.length() + (input.length() / 10));
		while (matcher.find()) {
			String match = matcher.group();
			matcher.appendReplacement(out, pairings.get(match));
		}
		matcher.appendTail(out);
		return out.toString();
	}
}
