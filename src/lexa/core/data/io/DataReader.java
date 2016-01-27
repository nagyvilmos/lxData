/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataReader.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: February 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2013-08-10   WNW             Add support for quoted strings, these are never
 *                              written but can be read to make hand build files
 *                              easier to produce.
 * 2015-03-05	WNW 2015-03		Add the concept of an array.
 *								Add ARRAY and LONG types.
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 *================================================================================
 */
package lexa.core.data.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import lexa.core.data.SimpleDataItem;
import lexa.core.data.SimpleDataSet;
import lexa.core.data.SimpleValueArray;
import lexa.core.data.ValueArray;
import lexa.core.data.ValueType;
import lexa.core.data.formatting.CombinedFormat;

/**
 * Read {@link DataItem} and {@link DataSet} objects from an input stream.
 * <p>A {@code DataSet} is read as a series of {@code DataItem} objects.
 * <p>A {@code DataItem} is read as the name followed by the formatted value;
 * with the toString depends on the data type.
 * <p>See {@link DataWriter} for the toString used.
 *
 * @author William
 * @since 2013-02
 */
public class DataReader
{

	/** The reader used to input the content */
	private final BufferedReader bufferedReader;
	private final boolean fromFile;
	private final CombinedFormat formatter;

	/**
	 * Create a new reader to input directly from a file.
	 *
	 * @param   file
	 *      The file for receiving the input.
	 * @throws  FileNotFoundException
	 *      When the file to be opened cannot be accessed.
	 */
	public DataReader(File file)
			throws FileNotFoundException
	{
		this(new BufferedReader(
				new InputStreamReader(
				new DataInputStream(
				new FileInputStream(file)))),
				true);
	}

	/**
	 * Create a new reader to input from a buffer.
	 *
	 * @param   bufferedReader
	 *      The buffer for reading data from.
	 */
	public DataReader(BufferedReader bufferedReader)
	{
		this(bufferedReader, false);
	}

	/**
	 * Create a new reader to input from a buffer.
	 *
	 * @param   bufferedReader
	 *      The buffer for reading data from.
	 * @param   fromFile
	 *      Indicates the reader is from a file.
	 */
	private DataReader(BufferedReader bufferedReader, boolean fromFile)
	{
		this.bufferedReader = bufferedReader;
		this.fromFile = fromFile;
		this.formatter = new CombinedFormat();
	}

	/**
	 * Close the input.
	 * <p>
	 * Once the input has been closed, no more data may be read.
	 *
	 * @throws  IOException
	 *       When an IO error occurs closing the input.
	 */
	public void close()
			throws IOException
	{
		this.bufferedReader.close();
	}

	/**
	 * Read the input into a {@link SimpleDataSet}.
	 *
	 * @return
	 *       A {@link SimpleDataSet} read from the input, null if empty.
	 * @throws  IOException
	 *       When an IO error occurs closing the input.
	 */
	public SimpleDataSet read()
			throws IOException
	{
		return this.read(false);
	}

	/**
	 * Read a {@link SimpleDataSet} from the input.
	 *
	 * @param   isNested
	 *       Indicate that the read is inside a {@link SimpleDataSet}
	 *       and so should not reach EOF.
	 * @return
	 *       A {@link SimpleDataSet} read from the input, null if empty
	 *       and {@code isNested == true}.
	 * @throws  IOException
	 *       When an IO error occurs reading the input.
	 */
	private SimpleDataSet read(boolean isNested)
			throws IOException
	{
		SimpleDataSet data = new SimpleDataSet();

		while (true)
		{
			SimpleDataItem item = this.readItem(isNested);
			if (item == null)
			{
				break;
			}
			data.put(item);
		}
		if (!isNested && data.isEmpty())
		{
			return null; // do not return an empty data set or else we can't read it all.
		}
		return data;
	}

	/**
	 * Read a {@link SimpleDataItem} from the input.
	 *
	 * @param   isNested
	 *       Indicate that the read is inside a {@link SimpleDataSet}
	 *       and so should not reach EOF.
	 * @return
	 *       A {see SimpleDataItem} read from the input or {@code null} if
	 *       The current {@link SimpleDataSet} has all been read.
	 * @throws  IOException
	 *       When an IO error occurs reading the input.
	 */
	private SimpleDataItem readItem(boolean isNested)
			throws IOException
	{
		String line;
		while (true)
		{
			line = this.bufferedReader.readLine();
			if (line == null)
			{
				if (isNested)
				{
					throw new IOException("Expected '}' before EOF.");
				}
				return null;
			}

			line = line.trim();
			if (line.isEmpty() || line.charAt(0) == '#')
			{
				continue;
			}
			else
			{
				if ("}".equals(line))
				{
					if (!isNested)
					{
						throw new IOException("Expected EOF.");
					}
					return null;
				}
			}
			break;
		}

		// split the line at the first space
		int split = line.indexOf(' ');
		
		String key;
		String value;
		if (split > -1)
		{
			key = line.substring(0, split);
			value =line.substring(split).trim();
		}
		else
		{
			key = line;
			value = "";
		}
		return new SimpleDataItem(key, this.decodeValue(value));
	}
	private Object decodeValue(String value)
			throws IOException
	{
		if (value.isEmpty())
		{
			return null;
		}
		// ignore includes if we're not reading from a file.
		if (this.fromFile &&
				value.length() > 9 &&
				"@include ".equals(value.substring(0, 9)))
		{
			value = value.substring(9)
					.trim();
			File includeFile = new File(value);
			if (!includeFile.exists())
			{
				// push the path in front:
				includeFile = new File(
						System.getProperty("user.dir") + "\\data\\" + value);
			}
			DataReader include = new DataReader(includeFile);
			SimpleDataSet data = include.read();
			include.close();
			return data;
		}

		char type = value.charAt(0);
		if (value.length() == 1)
		{
			if ('{' == type)
			{
				return this.read(true);
			}
			if ('\\' == type)
			{
				return this.readCodedString();
			}
			if ('"' == type)
			{
				return this.readQuotedString();
			}
			if ('[' == type)
			{
				return this.readArray();
			}
			throw new IOException("Invalid value encountered at " + value);
		}
		if (' ' == value.charAt(1))
		{
			switch (ValueType.toType(type))
			{
				case BOOLEAN :
				{
					return this.formatter.booleanFormat.fromString(value.substring(2));
				}
				case DATE :
				{
					return this.formatter.dateFormat.fromString(value.substring(2));
				}
				case DOUBLE :
				{
					return this.formatter.doubleFormat.fromString(value.substring(2));
				}
				case INTEGER :
				{
					return this.formatter.integerFormat.fromString(value.substring(2));
				}
				case LONG :
				{
					return this.formatter.longFormat.fromString(value.substring(2));
				}
				case STRING :
				{
					return this.formatter.stringFormat.fromString(value.substring(2));
				}
			}
		}
		// just treat unqualified as a string.
		return value;
	}

	private ValueArray readArray() throws IOException
	{
		ValueArray array = new SimpleValueArray();
		while (true)
		{
			String value = this.bufferedReader.readLine();
			if (value == null)
			{
				throw new IOException("Missing end of array");
			}
			value = value.trim();
			if ("]".equals(value))
			{
				break;
			}
			if (value.isEmpty() || value.charAt(0) == '#')
			{
				continue;
			}
			array.add(this.decodeValue(value));				
		}
		
		return array;
	}
	/**
	 * Read a coded string from the input.
	 * <p>Example:
	 * <pre>
	 * \# this will all be read as\
	 * it \@ contains all the special chracters such as \\ are escaped\
	 * \{ so the parser can read them \}.\
	 * all for \$15.00 \@ \-50\% discount
	 * </pre>
	 *
	 * @return A decoded string.
	 * @throws  IOException
	 *       When an IO error occurs reading the input.
	 */
	private String readCodedString()
			throws IOException
	{
		StringBuilder builder = new StringBuilder();
		boolean more = true;
		while (more)
		{
			String line = this.bufferedReader.readLine();
			if (line == null)
			{
				throw new IOException("Incomplete coded string.");
			}
			more = false;
			for (int c = 0;
					c < line.length();
					c++)
			{
				char ch = line.charAt(c);
				if ('\\' == ch)
				{
					c++;
					if (c == line.length())
					{
						more = true;
						builder.append('\n');
						break;
					}
					ch = line.charAt(c);
				}
				builder.append(ch);
			}
		}
		return builder.toString();
	}

	private String readQuotedString()
			throws IOException
	{
		StringBuilder builder = new StringBuilder();
		boolean more = true;
		while (more)
		{
			String line = this.bufferedReader.readLine();
			if (line == null)
			{
				throw new IOException("Expected close quote.");
			}
			int quote = line.indexOf('"');
			if (quote > -1)
			{
				if ("\"".equals(line.trim()))
				{
					more = false;
					continue;
				}
				line = line.replaceAll("\"\"", "\"");
			}
			if (builder.length() > 0)
			{
				builder.append('\n');
			}
			builder.append(line);
		}
		return builder.toString();
	}
}
