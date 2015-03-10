/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.data.formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper for serialising {@link String} values.
 * @author William
 * @since 2013-08
 */
public class FormatString
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
	
	public static String multiReplace(
		final String input, String[] ... replacements) {

		if (input == null || "".equals(input) || replacements == null) {
			return input;
		}
		StringBuilder regexBuilder = new StringBuilder();
		for (String[] pair : replacements)
		{
			regexBuilder.append('|').append(Pattern.quote(pair[0]));
		}
		Matcher matcher = Pattern.compile(regexBuilder.toString()).matcher(input);
		StringBuffer out = new StringBuffer(input.length() + (input.length() / 10));
		while (matcher.find()) {
			String match = matcher.group();
			for (String[] pair : replacements)
			{
				if (match.equals(pair[0]))
				{
					matcher.appendReplacement(out, pair[1]);
					break;
				}
			}
		}
		matcher.appendTail(out);
		return out.toString();
	}
}
