/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * DataReader.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: February 2013
 *==============================================================================
 */
package lexa.core.data.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import lexa.core.data.ArrayFactory;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.DataType;
import lexa.core.data.formatting.CombinedFormat;
import lexa.core.data.DataArray;
import lexa.core.data.DataFactory;

/**
 * Read {@link lexa.core.data.DataItem} and {@link lexa.core.data.DataSet} objects
 * from an input stream.
 * <p>A {@code lexa.core.data.DataSet} is read as a series of
 * {@code lexa.core.data.DataItem} objects.
 * <p>A {@code lexa.core.data.DataItem} is read as the name followed by the
 * formatted value; with the toString depends on the data type.
 * <p>See {@link DataWriter} for the toString used.
 *
 * @author William
 * @since 2013-02
 */
public class DataReader
        implements ReadDataSet
{
    private final DataFactory factory;
	/** The reader used to input the content */
	private final BufferedReader bufferedReader;
	private final boolean fromFile;
	private final CombinedFormat formatter;
    private final String source;
    private long lineNumber;
    /**
	 * Create a new reader to input directly from a file.
	 * loads into {@link ArrayFactory} instances.
	 * @param   file
	 *      The file for receiving the input.
	 * @throws  FileNotFoundException
	 *      When the file to be opened cannot be accessed.
	 */
	public DataReader(File file)
			throws FileNotFoundException
	{
        this(file, ArrayFactory.factory);
    }

    /**
	 * Create a new reader to input directly from a file.
	 * read into {@link ArrayFactory} instances.
	 * @param   file
	 *      The file for receiving the input.
     * @param   factory
     *      The type of factory to load the data into.
	 * @throws  FileNotFoundException
	 *      When the file to be opened cannot be accessed.
	 */
    public DataReader(File file, DataFactory factory)
			throws FileNotFoundException
    {
		this(new BufferedReader(
				new InputStreamReader(
				new DataInputStream(
				new FileInputStream(file)))),
                factory,
				true,
                '[' + file.getPath() + ']');
	}

	/**
	 * Create a new reader to input from a buffer.
	 * loads into {@link ArrayFactory} instances.
	 * @param   bufferedReader
	 *      The buffer for reading data from.
	 */
	public DataReader(BufferedReader bufferedReader)
    {
        this(bufferedReader, ArrayFactory.factory);
    }

	/**
	 * Create a new reader to input from a buffer.
	 * @param   bufferedReader
	 *      The buffer for reading data from.
     * @param   factory
     *      The type of factory to load the data into.
     */
    public DataReader(BufferedReader bufferedReader, DataFactory factory)
	{
		this(bufferedReader, factory, false, "[buffer]");
	}

	/**
	 * Create a new reader to input from a buffer.
	 *
	 * @param   bufferedReader
	 *      The buffer for reading data from.
     * @param   factory
     *      The type of factory to load the data into.
     * @param   fromFile
	 *      Indicates the reader is from a file.
     * @param   source
     *      The source name of the data.
	 */
	private DataReader(BufferedReader bufferedReader, DataFactory factory, boolean fromFile, String source)
	{
        this.factory = factory;
		this.bufferedReader = bufferedReader;
		this.fromFile = fromFile;
		this.formatter = new CombinedFormat();
        this.source = source;
        this.lineNumber=0;
	}

	/**
	 * Close the input.
	 * <p>
	 * Once the input has been closed, no more data may be read.
	 *
	 * @throws  IOException
	 *       When an IO error occurs closing the input.
	 */
    @Override
	public void close()
			throws IOException
	{
		this.bufferedReader.close();
	}

	/**
	 * Read the input into a {@link DataSet}.
	 *
	 * @return
	 *       A {@link DataSet} read from the input, null if empty.
	 * @throws  IOException
	 *       When an IO error occurs closing the input.
	 */
    @Override
	public DataSet read()
			throws IOException
	{
		return this.read(false);
	}

	/**
	 * Read a {@link DataSet} from the input.
	 *
	 * @param   isNested
	 *       Indicate that the read is inside a {@link DataSet}
	 *       and so should not reach EOF.
	 * @return
	 *       A {@link DataSet} read from the input, null if empty
	 *       and {@code isNested == true}.
	 * @throws  IOException
	 *       When an IO error occurs reading the input.
	 */
	private DataSet read(boolean isNested)
			throws IOException
	{
		DataSet data = this.factory.getDataSet();

		while (true)
		{
			DataItem item = this.readItem(isNested);
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
	 * Read a {@link DataItem} from the input.
	 *
	 * @param   isNested
	 *       Indicate that the read is inside a {@link DataSet}
	 *       and so should not reach EOF.
	 * @return
	 *       A {see ArrayDataItem} read from the input or {@code null} if
	 *       The current {@link DataSet} has all been read.
	 * @throws  IOException
	 *       When an IO error occurs reading the input.
	 */
	private DataItem readItem(boolean isNested)
			throws IOException
	{
		String line;
		while (true)
		{
			line = this.readLine();
			if (line == null)
			{
				if (isNested)
				{
					throw new IOException("Expected '}' before EOF " + this.source);
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
						throw new IOException("Expected EOF " + this.source);
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
		return this.factory.getDataItem(key, this.decodeValue(value));
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
			DataSet data = include.read();
			include.close();
			return data;
		}

		char type = value.charAt(0);
		if (value.length() == 1)
		{
            switch (String.valueOf(type))
            {
                case "{"    : return this.read(true);
                case "\\"   : return this.readCodedString();
			    case "\""   : return this.readQuotedString();
			    case "["    : return this.readArray();
			    case "-"    : return "";    // handle an empty string elegently
			}
            throw new IOException("Invalid value encountered at " + value + " on line " + this.lineNumber);
		}
		if (' ' == value.charAt(1))
		{
			switch (DataType.toType(type))
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

	private DataArray readArray() throws IOException
	{
		DataArray array = this.factory.getDataArray();
		while (true)
		{
			String value = this.readLine();
			if (value == null)
			{
				throw new IOException("Missing end of array " + this.source + " at line " + this.lineNumber);
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
			String line = this.readLine();
			if (line == null)
			{
				throw new IOException("Incomplete coded string " + this.source + " at line " + this.lineNumber);
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
                    switch (ch)
                    {

                    }
					ch = line.charAt(c);
				}
				builder.append(ch);
			}
		}
		return builder.toString();
	}

    private String readLine()
            throws IOException
    {
        this.lineNumber++;
        return this.bufferedReader.readLine();
    }

	private String readQuotedString()
			throws IOException
	{
		StringBuilder builder = new StringBuilder();
		boolean more = true;
		while (more)
		{
			String line = this.readLine();
			if (line == null)
			{
				throw new IOException("Expected close quote. " + this.source  + " at line " + this.lineNumber);
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

    /**
     * Read an entire data file into a {@link DataSet}.
     * <br>
     * This provides a single line method call to open a data file, read all its
     * content and close it cleanly.
     *
     * @param   file
     *          the file to be read
     * @return  the content of the file
     * @throws  FileNotFoundException
     *          when the file cannot be found
     * @throws  IOException
     *          when a problem is encountered reading from the file.
     */
    public static DataSet readDataFile(File file)
            throws FileNotFoundException,
            IOException
    {
        try (DataReader reader = new DataReader(file))
        {
            return reader.read();
        }
    }

    /**
     * Create a {@link DataSet} from a string array
     * <br>
     * The array is concatenated so that each entry is separated by a new line,
     * this makes it easier for defining default data sets in code.
     *
     * @param   args
     *          an array lines to be parsed into a data set
     * @return  the input parsed as a data set.
     * @throws  IOException
     *          when an I/O exception occurs.
     */
    public static DataSet parseString(String ... args)
            throws IOException
    {
        String string = String.join("\n", args);
        try (DataReader reader =
                new DataReader(
                    new BufferedReader(
                        new StringReader(string))))
        {
            return reader.read();
        }
    }
}
